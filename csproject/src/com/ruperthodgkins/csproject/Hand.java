package com.ruperthodgkins.csproject;

import java.util.ArrayList;

public class Hand {
	private ArrayList<Card> hand;
	
	public Hand() {
		hand = new ArrayList<Card>();
	}
	
	public void addCard(Card c) {
		hand.add(c);
	}
	
	public boolean removeCard(Card c) {
		return hand.remove(c);
	}
}