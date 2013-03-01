package com.ruperthodgkins.csproject;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;

public class TurnController {
	private ArrayList<Player> players = new ArrayList<Player>();
	private Player currentPlayer;
	private boolean drawnCard = false;
	private boolean movedCharacterInBattle = false;
	private int fieldCharacterSpaces = 0;
	private int cardLimit = 1;
	private int numberOfCardsPlayed = 0;
	private boolean attackedOpponent = true;
	private Card cardToRemove = null;
	private Timer gameTimer;
	
	public TurnController(Player p1, Player p2) {
		players.add(p1);
		players.add(p2);
		currentPlayer = p1;
		gameTimer = new Timer();
		gameTimer.start();
	}
	
	public Player getCurrentPlayer() {
		return currentPlayer;
	}
	
	public Player getPlayer(String name) {
		for(Player p : players) {
			if(p.getName().equals(name)) {
				return p;
			}
		}
		return null;
	}
	
	public ArrayList<Player> getPlayers() {
		return players;
	}
	
	public void setupNewGame() {
		//CREATE BOARD
		//SETUP BOARD
		//LOAD PLAYERS DATA
	}
	
	public String getGameTime() {
		return gameTimer.getGameTime();
	}
	
	public void removePlayerFromGame(Player p) {
		players.remove(p);
	}
	
	public void switchPlayer(Player current) {
		try {
			System.out.println("switching players");
			Thread.sleep(250);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(players.indexOf(current) == 0) {currentPlayer = players.get(1); }
		else if(players.indexOf(current) == 1) {currentPlayer = players.get(0);}
		drawnCard = false;
		numberOfCardsPlayed = 0;
		movedCharacterInBattle = false;
		Board.setCharacterMoved(false);
	}
	
	public void update() {
		Player p = currentPlayer;
		if(p.getMissTurn()) {
			p.setMissTurn(false);
			//break;
		}
		//wait for player to draw card
		if(p.getFirstTurn() && drawnCard == false) {
			p.startTurn();
			p.getDeck().shuffle();
			if(p.getDeck().hit(Gdx.input.getX(), Gdx.input.getY()) && Gdx.input.justTouched()) {
				for(int i = 0; i<5; i++) {
					Card c = p.getDeck().drawCard();
					p.getHand().addCard(c);
				}
				p.setFirstTurn(false);
				drawnCard = true;
			}
			
		}
		else if(drawnCard == false) {
			p.startTurn();
			if(p.getDeck().hit(Gdx.input.getX(), Gdx.input.getY()) && Gdx.input.justTouched()) {
				if(p.getDeck().getCardsRemaining() > 0) {
					Card c = p.getDeck().drawCard();
					p.getHand().addCard(c);
					drawnCard = true;
				}
				else {
					p.getDeck().hide();
				}
			}
		}
		
		//wait for player to arrange hand
		p.getHand().update();
		
		//wait for player to move characters to field
		if(fieldCharacterSpaces > 0) {
			//move characters to field
		}
		
		//wait for player to apply card to character
		if(numberOfCardsPlayed < cardLimit && drawnCard) {
			for(Character c : p.getCharacters()) {
				if(p.getHand().getHoldingCard()!=null) {
					if (c.hit(Gdx.input.getX(),Gdx.input.getY()) && c.getOwner() == p) {
						System.out.println("Added 20HP to " + c.getName());
						c.increaseHP(20);
						p.getDiscardPile().addCard(p.getHand().getHoldingCard());
						cardToRemove = p.getHand().getHoldingCard();
						numberOfCardsPlayed++;
						p.getHand().removeCard(cardToRemove);
					}
				}
			}
		}
		
		//wait for player to move character
		if(!movedCharacterInBattle) {
			//move character in battle
			if(Board.getCharacterMoved()) {
				movedCharacterInBattle = true;
			}
		}
		
		if(drawnCard && fieldCharacterSpaces == 0 && numberOfCardsPlayed == cardLimit && movedCharacterInBattle && attackedOpponent) {
			p.endTurn();
		}
		
		//wait for player to attack opposition
		if(p.turnEnded()) {
			switchPlayer(p);
		}
	}
}