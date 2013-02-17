package com.ruperthodgkins.csproject;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
	private ArrayList<Card> deck;
	
	public Deck(int size) {
		deck = new ArrayList<Card>(size);
	}
	
	public void shuffle() {
		Collections.shuffle(deck);
	}
	
	public Card drawCard() {
		Card c = deck.get(0);
		if(c!=null) {
			deck.remove(0);
			return c;
		}
		return null;
	}
}
