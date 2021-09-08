package com.yml.stockmanagement;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;

import org.json.simple.parser.*;
import org.json.simple.*;

public class StockManagement {
	@SuppressWarnings("unchecked")
	public static void stockManagement() {
		StockPortfolio stockPortfolio = new StockPortfolio();
		List<Stock> stockList = stockPortfolio.getStockList();
		try {
			JSONParser jsonParser = new JSONParser();
			Reader reader = new FileReader("data/stocks.json");
			JSONObject object = (JSONObject) jsonParser.parse(reader);
			JSONArray array = (JSONArray) object.get("stocks");
			array.forEach(item->{
				JSONObject object1 = (JSONObject) item;
				Stock stock = new Stock();
				String stockName = (String) object1.get("stockName");
				String stockSymbol = (String) object1.get("stockSymbol");
				long numberOfShares = (long) object1.get("numberOfShares");
				long sharePrice = (long)object1.get("sharePrice");
				stock.setStockName(stockName);
				stock.setNumberOfShares(numberOfShares);
				stock.setSharePrice(sharePrice);
				stock.setStockSymbol(stockSymbol);
				stockList.add(stock);
			});
			stockPortfolio.setStockList(stockList);
			System.out.println("Stock report");
			System.out.println("---------------");
			stockPortfolio.calculateStockValue();
			System.out.println();
			stockPortfolio.calculateTotalStockValue();
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
