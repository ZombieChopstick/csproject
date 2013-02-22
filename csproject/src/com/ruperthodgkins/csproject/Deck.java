package com.ruperthodgkins.csproject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Deck {
	private ArrayList<Card> deck;
	private Rectangle bbox;
	private Texture cardBackPic;
	private int x;
	private int y;
	private boolean visible = true;
	
	public Deck(int size) {
		deck = new ArrayList<Card>(size);
		
		AssetManager manager = AssetsManager.getInstance();
		cardBackPic = manager.get(AssetsManager.CARDBACK,Texture.class);
		x = Game.getWidth()-cardBackPic.getWidth()-20;
		y = 0;
		bbox = new Rectangle(x,y,cardBackPic.getWidth(),cardBackPic.getHeight());
		bbox.setX(x);
		bbox.setY(y);
	}
	
	public Texture getDeckTexture() {
		return cardBackPic;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getCardsRemaining() {
		return deck.size();
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
	
	public void addCard(Card c) {
		deck.add(c);
	}
	
	public void loadDeck(String file) {
		File deckFile = new File(file);
		try {
			FileInputStream reader = new FileInputStream(deckFile);
			@SuppressWarnings("unused")
			byte[] buffer;
			try {
				while(reader.read() != -1) {
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public boolean visible() {
		return visible;
	}
	
	public void hide() {
		visible = false;
	}
	
	public void show() {
		visible = true;
	}
	
	public boolean hit(int x, int y) {
		if(bbox.contains(x,Gdx.graphics.getHeight() - y)) {
			return true;
		}
		return false;
	}
	
	public void resize(int width, int height) {
		x = width-cardBackPic.getWidth()-20;
		y = 0;
		bbox.setX(x);
		bbox.setY(y);
	}
}
