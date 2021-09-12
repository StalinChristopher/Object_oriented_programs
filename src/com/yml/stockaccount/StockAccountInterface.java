package com.yml.stockaccount;

public interface StockAccountInterface {
	double valueOf();
	void buy(int amount, String symbol);
	void sell(int amount, String symbol);
	void save(String fileName);
	void printReport();
}
