package com.yml.deckofcards;
import com.yml.linkedlist.*;
import com.yml.queue.Queue;

/**
 * @author Stalin Christopher
 * This class creates a player object which maintains a queue of cards and also converts String to integer value
 * and also sorts the cards distributed based on rank
 */
public class Player {
	
	private Queue<Card> deckOfCards;
	
	Player(){
		deckOfCards = new Queue<Card>();
	}

	public Queue<Card> getDeckOfCards() {
		return deckOfCards;
	}

	public void setDeckOfCards(Queue<Card> deckOfCards) {
		this.deckOfCards = deckOfCards;
	}
	
	public void addCard(Card card) {
		deckOfCards.enqueue(card);
	}
	
	public int getIntegerValue(String value) {
		int integerValue = 0;
		
		try {
			integerValue = Integer.parseInt(value);
		}catch(NumberFormatException e) {
			switch (value) {
			case "Jack":
				integerValue = 11;
				break;
			case "Queen":
				integerValue = 12;
				break;
			case "King":
				integerValue = 13;
				break;
			case "Ace":
				integerValue = 14;
				break;
			}
		}
		return integerValue;
	}
	
	public void sortBasedOnRank() {
		int deckSize = deckOfCards.size();
		for(int i = 0; i<deckSize; i++) {
			Node<Card> current = deckOfCards.getFront();
			Node<Card> temp = current.getNext();
			while( temp != null) {
				int firstValue = getIntegerValue(current.getData().getRank());
				int secondValue = getIntegerValue(temp.getData().getRank());
				if(firstValue > secondValue) {
					Card temporary = current.getData();
					current.setData(temp.getData());
					temp.setData(temporary);
				}
				current = temp;
				temp = temp.getNext();
			}
		}
	}
	
}
