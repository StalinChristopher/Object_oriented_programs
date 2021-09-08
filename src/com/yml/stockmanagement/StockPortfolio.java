package com.yml.stockmanagement;
import java.util.*;

import org.json.simple.JSONObject;

public class StockPortfolio {
	private List<Stock> stockList = new ArrayList<Stock>();
	private Map<String,Long> map = new HashMap<>();
	
	public Map<String, Long> getMap() {
		return map;
	}

	public void setMap(Map<String, Long> map) {
		this.map = map;
	}

	public List<Stock> getStockList() {
		return stockList;
	}

	public void setStockList(List<Stock> stockList) {
		this.stockList = stockList;
	}
	
	public void calculateStockValue() {
		for(Stock stock: stockList) {
			long stockValue = stock.getNumberOfShares() * stock.getSharePrice();
			System.out.println("Stock Name : "+stock.getStockName()+",  Stock value : "+stockValue);
			map.put(stock.getStockSymbol(), stockValue);
		}

	}
	
	public void calculateTotalStockValue() {
		long totalStock=0;
		Collection<Long> stockValueList = map.values();
		for(Long value :stockValueList) {
			totalStock+=value;
		}
		System.out.println("Total value of all the stocks : "+totalStock);
	}


}
