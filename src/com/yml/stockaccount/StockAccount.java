package com.yml.stockaccount;

import java.util.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.*;

public class StockAccount implements StockAccountInterface {
	private String fileName;
    private JSONArray stocksData;
    List<CompanyShares> companyShares = new ArrayList<CompanyShares>();

    StockAccount(String fileName) {
        this.fileName = fileName;
    }

    public void readFileContents(){
        try {
            List<CompanyShares> companySharesList = new ArrayList<CompanyShares>();
            FileReader reader = new FileReader(fileName);
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(reader);
            
            JSONArray companyShares = (JSONArray) obj.get("companyShares");
            Iterator<JSONObject> iterator = companyShares.iterator();
            while (iterator.hasNext()) {
                CompanyShares companyShare = new CompanyShares();
                JSONObject compShare = iterator.next();
                companyShare.setStockSymbol(compShare.get("stockSymbol").toString());
                companyShare.setNumberOfShares((long) compShare.get("numberOfShares"));

                JSONArray transactions = (JSONArray) compShare.get("transactions");
                Iterator<JSONObject> iterator2 = transactions.iterator();

                List<Transaction> transactionList = new ArrayList<Transaction>();
                while (iterator2.hasNext()) {
                    Transaction transact = new Transaction();
                    JSONObject transaction = iterator2.next();
                    transact.setDateTime(transaction.get("DateTime").toString());
                    transact.setNumberOfShares((long) transaction.get("numberOfShares"));
                    transact.setState((String) transaction.get("State"));
                    transactionList.add(transact);
                }

                companyShare.setTransactions(transactionList);
                companySharesList.add(companyShare);
            }
            this.companyShares = companySharesList;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public double valueOf() {
    	readJSONFile();
    	double value = 0;
        for (CompanyShares companyShare : companyShares) {
            value += valueof(companyShare);
        }  
        return value;
    }
    
    public double valueof(CompanyShares companyShare) {
        readJSONFile();
        Iterator<JSONObject> itr = stocksData.iterator();
        double sharePrice = 0.0;
        while (itr.hasNext()) {
            JSONObject stock = itr.next();
            if (stock.get("stockSymbol").equals(companyShare.getStockSymbol())) {
                sharePrice = (double) stock.get("sharePrice");
            }
        }
        
        return sharePrice * companyShare.getNumberOfShares();
    }

    @Override
    public void buy(int amount, String symbol) {
        readJSONFile();
        Iterator<JSONObject> iterator = stocksData.iterator();
        PrintWriter out = new PrintWriter(System.out,true);

        long numberOfShares = 0;
        while (iterator.hasNext()) {
            JSONObject stock = iterator.next();
            if (stock.get("stockSymbol").equals(symbol)) {
                numberOfShares = (long) stock.get("numberOfShares");
            }
        }

        if (amount > numberOfShares) {
            out.println("Insufficient Shares Available");
        }
        else {
            CompanyShares newCompanyShare = null;
            for (CompanyShares companyShare : companyShares) {
                if (companyShare.getStockSymbol().equals(symbol)) {
                    newCompanyShare = companyShare;
                    companyShares.remove(companyShare);
                    break;
                }
            }
            if (newCompanyShare == null) {
                newCompanyShare = new CompanyShares(symbol);
            }

            
            updateValue(symbol, amount, newCompanyShare ,Transaction.BUY);
        }
        
    }

    private void updateValue(String symbol, long numberOfShares, CompanyShares companyShare, String state) {
        //Add transaction to CompanyShare Object
    	readJSONFile();
        long currentShares = companyShare.getNumberOfShares();
        if (state == Transaction.BUY) {
            companyShare.setNumberOfShares(currentShares + numberOfShares);
        }
        else {
            companyShare.setNumberOfShares(currentShares - numberOfShares);
        }
        long millis = System.currentTimeMillis();
        Date dateTime = new Date(millis);
        Transaction transaction = new Transaction(dateTime.toString(), numberOfShares, state);
        companyShare.addTransaction(transaction);
        companyShares.add(companyShare);

        //Update stocks.json file
        Iterator<JSONObject> itr = stocksData.iterator();
        
        while (itr.hasNext()) {
            JSONObject stock = itr.next();
            if (stock.get("stockSymbol").equals(symbol)) {
                currentShares = (long)stock.get("numberOfShares"); 
                stock.remove("numberOfShares");
                if (state == Transaction.BUY) {
                	long newShares = currentShares - numberOfShares;
                    stock.put("numberOfShares", newShares);
                }
                else {
                	long newShares = currentShares + numberOfShares;
                    stock.put("numberOfShares", newShares);
                }
            }
        }

        try {
            FileWriter writer = new FileWriter("data/stocks.json");
            JSONObject result = new JSONObject();
            result.put("stocks", stocksData);
            writer.write(result.toJSONString());
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    @Override
    public void sell(int amount, String symbol) {
    	readJSONFile();
        PrintWriter out = new PrintWriter(System.out);
        long numberOfShares = 0;

        for (CompanyShares companyShare : companyShares) {
            if (companyShare.getStockSymbol().equals(symbol)) {
                numberOfShares = companyShare.getNumberOfShares();
            }
        }

        if (numberOfShares == 0 || amount > numberOfShares) {
            out.println("Insufficient Shares available");
        }
        else {
            CompanyShares selectedShare = null;
            for (CompanyShares companyShare : companyShares) {
                if (companyShare.getStockSymbol().equals(symbol)) {
                    selectedShare = companyShare;
                    companyShares.remove(companyShare);
                    break;
                }
            }

            if (selectedShare != null) {
                updateValue(symbol, amount, selectedShare, Transaction.SELL);
            }
        }
    }

    @Override
    public void save(String filename) {
        JSONArray compShares = new JSONArray();
        for (CompanyShares companyShare : companyShares) {
            String stockSymbol = companyShare.getStockSymbol();
            long numberOfShares = companyShare.getNumberOfShares();
            JSONArray transactions = new JSONArray();
            for (Transaction transaction : companyShare.getTransactions()) {
                JSONObject transactionObject = new JSONObject();
                transactionObject.put("DateTime", transaction.getDateTime().toString());
                transactionObject.put("numberOfShares", transaction.getNumberOfShares());
                transactionObject.put("State", transaction.getState());
                transactions.add(transactionObject);
            }
            JSONObject obj = new JSONObject();
            obj.put("stockSymbol", stockSymbol);
            obj.put("numberOfShares", numberOfShares);
            obj.put("transactions", transactions);
            compShares.add(obj);
       }

        JSONObject finalJSON = new JSONObject();
        finalJSON.put("companyShares", compShares);

       try {
           FileWriter writer = new FileWriter(filename);
           writer.write(finalJSON.toJSONString());
           writer.flush();
           writer.close();
       } catch (Exception e) {
           e.printStackTrace();
       }       
    }

    @Override
    public void printReport() {
    	PrintWriter out = new PrintWriter(System.out, true);
        out.println("Stock Report");
        out.println("Holding Shares\n");
        for (CompanyShares companyShare : companyShares) {
            out.println("Share Symbol : " + companyShare.getStockSymbol());
            out.println("Number of Shares : " + companyShare.getNumberOfShares());
            out.println("Value of each share : " + valueof(companyShare)/companyShare.getNumberOfShares());
            out.println("Total Share Value : " + valueof(companyShare));
            out.println();
        }
        out.println("Total Value of all the shares in the account: " + valueOf());
        
    }
    
    private void readJSONFile(){
        try{
            FileReader reader = new FileReader("data/stocks.json");
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(reader);
            stocksData = (JSONArray) obj.get("stocks");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
