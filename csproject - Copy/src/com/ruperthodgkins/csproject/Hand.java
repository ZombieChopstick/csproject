package com.ruperthodgkins.csproject;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;

public class Hand {
	private ArrayList<Card> hand;
	private Card holdingCard = null;
	private int lastX = 20;
	private int lastZ = 1;
	private int lastCardX;
	private int lastCardY;
	private boolean rememberLastCardPosition = false;
	
	public Hand() {
		hand = new ArrayList<Card>();
	}
	
	public ArrayList<Card> getCards() {
		/*ArrayList<Card> adjustedHand = new ArrayList<Card>();
		for(Card card : hand) {
			int z = card.getZIndex();
			adjustedHand.add(z-1,card);
		}
		int index = 0;
		for(Card c : adjustedHand) {
			if(c == null) {
				adjustedHand.remove(index);
			}
			index++;
		}
		return adjustedHand;*/
		return hand;
	}
	
	public void addCard(Card c) {
		hand.add(c);
		c.setPosition(lastX, 0);
		c.setZIndex(lastZ);
		//System.out.println("Z Before: " + lastZ);
		lastZ++;
		//System.out.println("Z After: " + lastZ);
		lastX+=50;
	}
	
	public boolean removeCard(Card c) {
		return hand.remove(c);
	}
	
	public void bringCardForward(Card c) {
		for(Card card : hand) {
			if(card == c) {
				c.setZIndex(lastZ);
				lastZ++;
			}
		}
	}
	
	public void update() {
		ArrayList<Card> possibleHits = new ArrayList<Card>();
		for(Card c : hand) {
			if(c.hit(Gdx.input.getX(),Gdx.input.getY())) {
				possibleHits.add(c);
			}
			c.flipFaceDown();
		}
		int lastZ = 0;
		Card cardHit = null;
		if(possibleHits.size() > 0) {
			for(Card c : possibleHits) {
				int z = c.getZIndex();
				if(z > lastZ) { 
					lastZ = z; 
					cardHit = c;
				}
			}
			
			if(cardHit!= null) {
				//System.out.println("Last Position: " + lastCardX + "," + lastCardY);
				if(Gdx.input.isTouched()) {
					if(holdingCard == null) {
						lastCardX = cardHit.getX();
						lastCardY = cardHit.getY();
						rememberLastCardPosition = true;
						cardHit.setPosition(Gdx.input.getX()-(cardHit.getCardPic().getWidth()/2), Game.getHeight()-Gdx.input.getY()-(cardHit.getCardPic().getHeight()/2));
						holdingCard = cardHit;
					}
					else {
						holdingCard.setPosition(Gdx.input.getX()-(holdingCard.getCardPic().getWidth()/2), Game.getHeight()-Gdx.input.getY()-(holdingCard.getCardPic().getHeight()/2));
						holdingCard.flipFaceUp();
					}
				}
				else {
					if(rememberLastCardPosition) {
						cardHit.setPosition(lastCardX, lastCardY);
						rememberLastCardPosition = false;
					}
					holdingCard = null;
					cardHit.flipFaceUp();
					int index = 1;
					for(Card c : hand) {
						if(cardHit == c) {
							c.setZIndex(hand.size());
							//System.out.println("Hit Card: " + c.getZIndex());
						}
						else {
							c.setZIndex(index);
							//System.out.println("Card #" + index + ": " + c.getZIndex());
							index++;
						}
					}
				}
			}
		}
		
		/*if(Gdx.input.isTouched()) {
			for(Card c : hand) {
				if(c.hit(Gdx.input.getX(),Gdx.input.getY()) && holdingCard == null) {
					c.setPosition(Gdx.input.getX()-(c.getCardPic().getWidth()/2), Game.getHeight()-Gdx.input.getY()-(c.getCardPic().getHeight()/2));
					holdingCard = c;
				}
				else if(holdingCard!=null) {
					holdingCard.setPosition(Gdx.input.getX()-(holdingCard.getCardPic().getWidth()/2), Game.getHeight()-Gdx.input.getY()-(holdingCard.getCardPic().getHeight()/2));
				}
			}
		}
		else {
			holdingCard = null;
		}*/
		
		/*for(Card c : hand) {
			if(c.hit(Gdx.input.getX(),Gdx.input.getY())) {
				c.flipFaceUp();
				System.out.println(c.getZIndex());
			}
			else {
				c.flipFaceDown();
			}
		}*/
	}
}