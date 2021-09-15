package com.yml.stockaccount;

import com.yml.linkedlist.Node;
import com.yml.linkedlist.LinkedList;
import com.yml.stack.*;
import com.yml.stack.Stack;
import com.yml.queue.Queue;
import java.util.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.*;

/**
 * @author Stalin Christopher This class maintains the stock account of the
 *         user. Here buy, sell, save and print methods are written for the
 *         stocks
 *
 */
public class StockAccount implements StockAccountInterface {
	private final String STOCKS_FILE = "data/stocks.json";
	private String fileName;
	private JSONArray stocksData;
	private LinkedList<CompanyShares> companyShares = new LinkedList<CompanyShares>();
	Stack<JSONObject> transactionStack = new Stack<JSONObject>();
	Queue<String> dateTimeQueue = new Queue<String>();
	StockAccount(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * Getter and setter methods for CompanyShares list
	 */
	public LinkedList<CompanyShares> getCompanyShares() {
		return companyShares;
	}

	public void setCompanyShares(LinkedList<CompanyShares> companyShares) {
		this.companyShares = companyShares;
	}

	/**
	 * Method to restore all the data from a file that has been written to storage
	 * by save() method
	 */
	public void readFileContents() {
		try {
			LinkedList<CompanyShares> companySharesList = new LinkedList<CompanyShares>();
			FileReader reader = new FileReader(fileName);
			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject) parser.parse(reader);
			JSONArray companyShares = (JSONArray) obj.get("companyShares");
			JSONArray transStack = (JSONArray) obj.get("transactionStack");
			Iterator<JSONObject> iterator = companyShares.iterator();
			if (companyShares == null) {
				return;
			}
			JSONArray dateTimeQ = (JSONArray) obj.get("dateTimeQueue");
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
			iterator = transStack.iterator();
			while (iterator.hasNext()) {
				transactionStack.push(iterator.next());
			}
			
			Iterator<String> itr2 = dateTimeQ.iterator();
            while (itr2.hasNext()) {
                dateTimeQueue.enqueue(itr2.next());
            }
            
			System.out.println("Data restored from file");
		} catch (Exception e) {
			System.out.println("Data restored from file");
			e.printStackTrace();
		}
	}

	/**
	 * Method to calculate total value of the all the stocks combined in the account
	 */
	@Override
	public double valueOf() {
		readJSONFile();
		double value = 0;
		for (Node<CompanyShares> companyShare : companyShares) {
			value += valueof(companyShare.getData());
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

	/**
	 * Method to buy stocks from stocks.json file
	 */
	@Override
	public void buy(int amount, String symbol) {
		readJSONFile();
		Iterator<JSONObject> iterator = stocksData.iterator();
		long numberOfShares = 0;
		while (iterator.hasNext()) {
			JSONObject stock = iterator.next();
			if (stock.get("stockSymbol").equals(symbol)) {
				numberOfShares = (long) stock.get("numberOfShares");
			}
		}

		if (amount > numberOfShares) {
			System.out.println("No sufficient shares available to buy");
		} else {
			CompanyShares newCompanyShare = null;
			for (Node<CompanyShares> companyShare : companyShares) {
				CompanyShares compShare = companyShare.getData();
				if (compShare.getStockSymbol().equals(symbol)) {
					newCompanyShare = compShare;
					companyShares.remove(compShare);
					break;
				}
			}
			if (newCompanyShare == null) {
				newCompanyShare = new CompanyShares(symbol);
			}
			updateValue(symbol, amount, newCompanyShare, Transaction.BUY);
		}

	}

	/**
	 * @param symbol
	 * @param numberOfShares
	 * @param companyShare
	 * @param state          
	 * Helper method to create/update companyShare object in
	 * companyShares List and also update stocks.json file
	 * during buy/sell situations
	 */
	private void updateValue(String symbol, long numberOfShares, CompanyShares companyShare, String state) {
		// Add transaction to CompanyShare Object
		readJSONFile();
		long currentShares = companyShare.getNumberOfShares();
		if (state == Transaction.BUY) {
			companyShare.setNumberOfShares(currentShares + numberOfShares);
		} else {
			companyShare.setNumberOfShares(currentShares - numberOfShares);
		}
		long millis = System.currentTimeMillis();
		Date dateTime = new Date(millis);
		Transaction transaction = new Transaction(dateTime.toString(), numberOfShares, state);
		JSONObject transact = new JSONObject();
		transact.put("symbol", symbol);
		transact.put("state", state);
		transactionStack.push(transact);
		dateTimeQueue.enqueue(dateTime.toString());
		companyShare.addTransaction(transaction);
		companyShares.add(companyShare);

		// Update stocks.json file
		Iterator<JSONObject> itr = stocksData.iterator();

		while (itr.hasNext()) {
			JSONObject stock = itr.next();
			if (stock.get("stockSymbol").equals(symbol)) {
				currentShares = (long) stock.get("numberOfShares");
				stock.remove("numberOfShares");
				if (state == Transaction.BUY) {
					long newShares = currentShares - numberOfShares;
					stock.put("numberOfShares", newShares);
				} else {
					long newShares = currentShares + numberOfShares;
					stock.put("numberOfShares", newShares);
				}
			}
		}

		try {
			FileWriter writer = new FileWriter(STOCKS_FILE);
			JSONObject result = new JSONObject();
			result.put("stocks", stocksData);
			writer.write(result.toJSONString());
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (state == Transaction.BUY) {
			System.out.println("Buy Successfull");
		} else {
			System.out.println("Sell Successfull");
		}

	}

	/**
	 * Method to Sell a stocks present in the account
	 */
	@Override
	public void sell(int amount, String symbol) {
		readJSONFile();
		long numberOfShares = 0;

		for (Node<CompanyShares> companyShare : companyShares) {
			if (companyShare.getData().getStockSymbol().equals(symbol)) {
				numberOfShares = companyShare.getData().getNumberOfShares();
			}
		}

		if (numberOfShares == 0 || amount > numberOfShares) {
			System.out.println("No sufficient shares are available to sell");
		} else {
			CompanyShares selectedShare = null;
			for (Node<CompanyShares> companyShare : companyShares) {
				if (companyShare.getData().getStockSymbol().equals(symbol)) {
					selectedShare = companyShare.getData();
					companyShares.remove(companyShare.getData());
					break;
				}
			}

			if (selectedShare != null) {
				updateValue(symbol, amount, selectedShare, Transaction.SELL);
			}
		}
	}

	/**
	 * Method to save the current status of account into a file in a given path
	 */
	@Override
	public void save(String filename) {
		JSONArray compShares = new JSONArray();
		for (Node<CompanyShares> companyShare : companyShares) {
			String stockSymbol = companyShare.getData().getStockSymbol();
			long numberOfShares = companyShare.getData().getNumberOfShares();
			JSONArray transactions = new JSONArray();
			for (Transaction transaction : companyShare.getData().getTransactions()) {
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

		JSONArray transStack = new JSONArray();
		for (Node<JSONObject> transact : transactionStack) {
			transStack.add(transact.getData());
		}
		
		JSONArray dateQueue = new JSONArray();
        for (Node<String> dateTime : dateTimeQueue) {
            dateQueue.add(dateTime.getData());
        }

		JSONObject finalJSON = new JSONObject();
		finalJSON.put("companyShares", compShares);
		finalJSON.put("transactionStack", transStack);
		finalJSON.put("dateTimeQueue", dateQueue);

		try {
			FileWriter writer = new FileWriter(filename);
			writer.write(finalJSON.toJSONString());
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * method to print the stockReport in the console
	 */
	@Override
	public void printReport() {
		System.out.println("Stock Report");
		System.out.println("Holding Shares\n");
		for (Node<CompanyShares> companyShare : companyShares) {
			double valueEach = 0;
			if (companyShare.getData().getNumberOfShares() != 0) {
				valueEach = valueof(companyShare.getData()) / companyShare.getData().getNumberOfShares();
			}
			System.out.println("Share Symbol : " + companyShare.getData().getStockSymbol());
			System.out.println("Number of Shares : " + companyShare.getData().getNumberOfShares());
			System.out.println("Value of each share : " + valueEach);
			System.out.println("Total Share Value : " + valueof(companyShare.getData()));
			System.out.println();
		}
		System.out.println("Total Value of all the shares in the account: " + valueOf());

	}

	/**
	 * Method to reads stocks.json file and sets the stocksData class variable used
	 * by other methods
	 */
	private void readJSONFile() {
		try {
			FileReader reader = new FileReader(STOCKS_FILE);
			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject) parser.parse(reader);
			stocksData = (JSONArray) obj.get("stocks");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
