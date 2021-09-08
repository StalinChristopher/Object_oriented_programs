package com.yml.stockmanagement;

public class Stock {
	private String stockName;
	private String stockSymbol;
	private long numberOfShares;
	private long sharePrice;
	public String getStockName() {
		return stockName;
	}
	public void setStockName(String stockName) {
		this.stockName = stockName;
	}
	public String getStockSymbol() {
		return stockSymbol;
	}
	public void setStockSymbol(String stockSymbol) {
		this.stockSymbol = stockSymbol;
	}
	public long getNumberOfShares() {
		return numberOfShares;
	}
	public void setNumberOfShares(long numberOfShares) {
		this.numberOfShares = numberOfShares;
	}
	@Override
	public String toString() {
		return "Stock [stockName=" + stockName + ", stockSymbol=" + stockSymbol + ", numberOfShares=" + numberOfShares
				+ ", sharePrice=" + sharePrice + "]";
	}
	public long getSharePrice() {
		return sharePrice;
	}
	public void setSharePrice(long sharePrice) {
		this.sharePrice = sharePrice;
	}
	
	
}
