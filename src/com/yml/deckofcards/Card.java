package com.yml.deckofcards;

/**
 * @author Stalin Christopher
 * This class is used to create a card object which stores and manipulates the value of suit and rank of one card
 */
public class Card {
	private String suit;
    private String rank;

    Card(String suit, String rank) {
        this.suit = suit;
        this.rank = rank;
    }

    Card() {

    }

    @Override
    public String toString() {
        return rank+" of "+ suit;
    }

    public String getSuit() {
        return suit;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }
    public String getRank() {
        return rank;
    }
    public void setRank(String rank) {
        this.rank = rank;
    }
}
