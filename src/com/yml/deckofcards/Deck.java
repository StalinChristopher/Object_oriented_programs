package com.yml.deckofcards;
import java.util.*;
import com.yml.queue.Queue;
import com.yml.linkedlist.Node;

/**
 * @author Stalin Christopher
 * @class This class initialises the cards and has methods to shuffle the deck and assign the cards to players and also
 * 		  print the cards
 * 
 *
 */
public class Deck {
	Random random = new Random();
	Set<Integer> cardNumber = new HashSet<Integer>();
	String suit[] = {"Clubs", "Diamonds", "Hearts", "Spades"};
	String rank[] = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
	Card card[] = new Card[52];
	Deck() {
	}
	
	/**
	 * @method to initialise the card object
	 */
	public void initialize() {
		for(int i = 0; i< 52; i++) {
			card[i] = new Card();
		}
		int count =0;
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j<13; j++) {
				card[count].setSuit(suit[i]);
				card[count].setRank(rank[j]);
				count ++;
			}
		}
	}
	
	/**
	 * @method to return a unique random number which is used as index for cards array to assign cards to the players
	 */
	public int generateRandomNumber() {
		
		int randomNumber = random.nextInt(52);
		while(cardNumber.contains(randomNumber)) {
			randomNumber = random.nextInt(52);
		}
		cardNumber.add(randomNumber);
		return randomNumber;
	}
	
	/**
	 * @method generates 9 unique cards for the players present in the queue using generateRandomNumber() method
	 * 		   
	 */
	public void shuffle(Queue<Player> players) {
		for (Node<Player> player : players) {
			for(int i =0; i < 9; i++) {
				int randomNumber = generateRandomNumber();
				player.getData().addCard(card[randomNumber]);
			}
		}
	}
	
	/**
	 * @method to print the given 2d array as the parameter
	 */
	public void printCards(Queue<Player> players) {
		int count = 1;
		for(Node<Player> player : players) {
			System.out.print("Player "+count+" : \n");
			for (Node<Card> card : player.getData().getDeckOfCards()) {
				System.out.println(card.getData() + " ");
			}
			count++;
			System.out.println();
		}
	}
}
