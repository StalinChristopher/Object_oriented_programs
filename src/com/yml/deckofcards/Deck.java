package com.yml.deckofcards;
import java.util.*;

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
	String cards[] = new String[53];
	Deck() {
		this.initialize();
	}
	
	/**
	 * @method to initialise the cards and it is called inside the constructor
	 */
	public void initialize() {
		int count =0;
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j<13; j++) {
				cards[count] = suit[i]+""+rank[j];
				count ++;
			}
		}
	}
	
	/**
	 * @method to return a unique random number which is used as index for cards array to assign cards to the players
	 */
	public int generateRandomNumber() {
		
		int randomNumber = random.nextInt(53);
		while(cardNumber.contains(randomNumber)) {
			randomNumber = random.nextInt(53);
		}
		cardNumber.add(randomNumber);
		return randomNumber;
	}
	
	/**
	 * @method it returns a 2d array where each row is the player and each column is the respective card of the players
	 * 		   Each player will have 9 unique cards and there are a total of 4 players
	 */
	public String[][] shuffle() {
		String[][] playersArray = new String[4][9];
		for(int i = 0; i < 4; i++) {
			for(int j = 0 ; j < 9; j++) {
				int randomNumber = generateRandomNumber();
				playersArray[i][j] = cards[randomNumber];
			}
		}
		return playersArray;
	}
	
	/**
	 * @method to print the given 2d array as the parameter
	 */
	public void print2dArray(String[][] playersArray) {
		for(int i=0; i<4; i++) {
			System.out.print("Player "+(i+1)+" : ");
			for(int j = 0 ; j < 9; j++) {
				System.out.print(playersArray[i][j]+"     ");
			}
			System.out.println();
		}
	}
}
