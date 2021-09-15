package com.yml.stockaccount;
import java.io.FileReader;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.yml.linkedlist.Node;

/**
 * @author Stalin Christopher
 * Class to create a account object and maintain the user interface
 */
public class StockAccountManagement {
	final static String STOCKS_FILE = "data/stocks.json";
    final static String ACCOUNT_FILE = "data/stockAccounts/account1.json";
    private static Scanner in = new Scanner(System.in);

    private static StockAccount account = new StockAccount(ACCOUNT_FILE);
	public static void stockManagement() {
		account.readFileContents();
		System.out.println("Welcome to Stock Account program");
		System.out.println("Choose an option:");
		while(true) {
            System.out.println("1. Buy Stocks\n2. Sell Stocks\n3. Print stock report\n4. Exit");
            int choice = in.nextInt();
            in.nextLine();

            switch (choice) {
                case 1:
                    buyStocks();
                    break;
                case 2:
                    sellStocks();
                    break;
                case 3:
                    account.printReport();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid Option");
                    break;
            }
		}
	}
	
	private static void sellStocks() {
        Scanner in = new Scanner(System.in);

        System.out.println("Select the stock you want to Sell");
        int count = 1;
        for (Node<CompanyShares> companyShare : account.getCompanyShares()) {
            System.out.println(count + ":");
            System.out.println("Stock Symbol : " + companyShare.getData().getStockSymbol());
            System.out.println("Number Of Shares : " + companyShare.getData().getNumberOfShares());
            System.out.println();
            count++;
        }

        int choice = in.nextInt();
        while (choice >= count) {
            System.out.println("Invalid option");
            choice = in.nextInt();
        }

        System.out.println("Enter the amount to sell");
        int amount = in.nextInt();
        CompanyShares selectedStock = account.getCompanyShares().get(choice - 1).getData();
        while (amount > (long) selectedStock.getNumberOfShares() || amount<=0)
        {
            System.out.println("Enter a valid amount");
            amount = in.nextInt();
        }

        account.sell(amount, selectedStock.getStockSymbol());
        account.save(ACCOUNT_FILE);
    }

    private static void buyStocks() {
        Scanner in = new Scanner(System.in);
        System.out.println("Select the stock you want to buy");
        JSONArray stocks = readJSON();
        Iterator<JSONObject> itr = stocks.iterator();
        int count = 1;
        while (itr.hasNext()) {
            System.out.println(count + ":");
            JSONObject stock = itr.next();
            System.out.println("Stock Name: " + stock.get("stockName"));
            System.out.println("Stock Symbol: " + stock.get("stockSymbol"));
            System.out.println("Share price: " + stock.get("sharePrice"));
            System.out.println("Number Of Shares: " + stock.get("numberOfShares"));
            System.out.println();
            count++;
        }

        int stockChoice = in.nextInt();
        while (stockChoice >= count) {
            System.out.println("Invalid option");
            stockChoice = in.nextInt();
        }

        System.out.println("Enter the amount to buy");
        int amount = in.nextInt();
        JSONObject selectedStock = (JSONObject) stocks.get(stockChoice - 1);
        while (amount > (long) selectedStock.get("numberOfShares") || amount<=0)
        {
            System.out.println("Enter a valid amount");
            amount = in.nextInt();
        }

        account.buy(amount, (String) selectedStock.get("stockSymbol"));
        account.save(ACCOUNT_FILE);
    }

    private static JSONArray readJSON() {
        try {
            FileReader reader = new FileReader(STOCKS_FILE);
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(reader);
            JSONArray stocks = (JSONArray) obj.get("stocks");
            return stocks;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
	
}
