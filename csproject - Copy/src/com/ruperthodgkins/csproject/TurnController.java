package com.ruperthodgkins.csproject;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;

public class TurnController {
	private ArrayList<Player> players = new ArrayList<Player>();
	private Player currentPlayer;
	private boolean drawnCard = false;
	
	public TurnController(Player p1, Player p2) {
		players.add(p1);
		players.add(p2);
		currentPlayer = p1;
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
	
	public void removePlayerFromGame(Player p) {
		players.remove(p);
	}
	
	public void switchPlayer(Player current) {
		if(players.indexOf(current) == 0) {currentPlayer = players.get(1); }
		else if(players.indexOf(current) == 1) {currentPlayer = players.get(0);}
		drawnCard = false;
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
		
		//wait for player to apply card to character
		
		//wait for player to move character
		
		//wait for player to attack opposition
		if(p.turnEnded()) {
			switchPlayer(p);
		}
	}
}
