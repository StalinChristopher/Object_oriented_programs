package com.yml.stockaccount;

import java.util.*;

public class CompanyShares{
    private String stockSymbol;
    private long numberOfShares;
    private String shareName;

	private List<Transaction> transactions = new ArrayList<Transaction>();

	CompanyShares() {

    }
    
    CompanyShares(String stockSymbol) {
        this.stockSymbol = stockSymbol;
        this.numberOfShares = 0;
    }

    @Override
    public String toString() {
        return "stockSymbol=" + stockSymbol + ", Shares=" + numberOfShares;
    }
    
    public String getShareName() {
		return shareName;
	}

	public void setShareName(String shareName) {
		this.shareName = shareName;
	}

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
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
    
}
