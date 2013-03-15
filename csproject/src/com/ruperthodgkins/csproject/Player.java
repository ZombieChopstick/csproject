package com.ruperthodgkins.csproject;

import java.util.ArrayList;

public class Player {
	private Hand hand;
	private Deck deck;
	private Discard discard;
	private ArrayList<Character> myCharacters;
	private static int nextID = 0;
	private int id;
	private String name;
	private boolean missNextTurn = false;
	private boolean firstTurn = true;
	private boolean endTurn = false;
	private boolean turnStarted = false;
	
	public Player() {
		hand = new Hand();
		deck = new Deck(30);
		discard = new Discard(0,0);
		myCharacters = new ArrayList<Character>();
		id = nextID;
		nextID++;
	}
	
	public int getID() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public ArrayList<Character> getCharacters() {
		return myCharacters;
	}
	
	public void setCharacters(ArrayList<Character> characters) {
		myCharacters = characters;
	}
	
	public Hand getHand() {
		return hand;
	}
	
	public Deck getDeck() {
		return deck;
	}
	
	public Discard getDiscardPile() {
		return discard;
	}
	
	public boolean getFirstTurn() {
		return firstTurn;
	}
	
	public boolean getMissTurn() {
		return missNextTurn;
	}
	
	public boolean getTurnStarted() {
		return turnStarted;
	}
	
	public void setMissTurn(boolean miss) {
		missNextTurn = true;
	}
	
	public void setFirstTurn(boolean first) {
		firstTurn = first;
	}
	
	public void setTurnStarted(boolean started) {
		turnStarted = started;
	}
	
	public void endTurn() {
		endTurn = true;
		turnStarted = false;
	}
	
	public void startTurn() {
		endTurn = false;
		turnStarted = true;
	}
	
	public boolean turnEnded() {
		return endTurn;
	}
}
