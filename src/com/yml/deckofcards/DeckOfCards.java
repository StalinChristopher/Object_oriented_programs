package com.yml.deckofcards;

public class DeckOfCards {
	public static void deckOfCards() {
		
		//Creating a new object of class Deck
		Deck deck = new Deck();
		
		//Calling the shuffle method from Deck class and storing the return value in 2d array of type String
		String playersArray[][] = deck.shuffle();
		
		//Printing the obtained 2d array
		deck.print2dArray(playersArray);
	}
}
