package com.yml.stockaccount;

import java.util.*;

/**
 * @author Stalin Christopher
 * this class contains information regarding stocks and transaction of the company
 */
public class CompanyShares{
    private String stockSymbol;
    private long numberOfShares;

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

    /**
     * @param transaction
     * Add a new transaction of the company
     */
    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    /**
     * @return all the transactions of the company
     * 
     */
    public List<Transaction> getTransactions() {
        return transactions;
    }

    /**
     * sets the transactions of the company
     */
    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    /**
     * @return stock symbol for a particular share
     */
    public String getStockSymbol() {
        return stockSymbol;
    }

    /**
     * @param stockSymbol
     * sets the stockSymbol for a particular stock
     */
    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    /**
     * @return the number of shares present for the particular stock in the company
     */
    public long getNumberOfShares() {
        return numberOfShares;
    }

    /**
     * @param numberOfShares
     * Set the numberOfShares for a particular stock of a company
     */
    public void setNumberOfShares(long numberOfShares) {
        this.numberOfShares = numberOfShares;
    }
    
}
