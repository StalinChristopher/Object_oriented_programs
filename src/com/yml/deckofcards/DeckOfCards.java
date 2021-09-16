package com.yml.deckofcards;

import com.yml.linkedlist.Node;
import com.yml.queue.Queue;

public class DeckOfCards {
	public static void deckOfCards() {
		Deck deck = new Deck();
        deck.initialize();
		Queue<Player> players = new Queue<Player>();
		
		int noOfPlayers = 4;
        for(int i = 0 ; i<noOfPlayers; i++) {
            players.enqueue(new Player());
        }

        deck.shuffle(players);
        for (Node<Player> player : players) {
            player.getData().sortBasedOnRank();
        }

        deck.printCards(players);
	}
}
