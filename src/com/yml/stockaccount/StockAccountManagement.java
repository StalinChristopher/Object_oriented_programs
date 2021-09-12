package com.yml.stockaccount;

public class StockAccountManagement {
	public static void stockManagement() {
		StockAccount account = new StockAccount("data/stockAccounts/account1.json");
		account.readFileContents();
		
	     account.buy(70, "$I");
		 account.buy(100, "$R");
	     account.sell(7, "$I");
	     account.sell(50, "$R");
	     account.save("data/stockAccounts/account1.json");
	     account.printReport();
	}
	
}
