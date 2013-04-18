package com.ruperthodgkins.csproject;

import java.util.ArrayList;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class Field {
	private ArrayList<Card> reserveCharacters = new ArrayList<Card>(6);
	private Texture[] reserveSpace = new Texture[6];
	private AssetManager manager = AssetsManager.getInstance();
	private float x;
	private float y;
	private int emptySpaces = 6;
	
	public Field(float x, float y) {
		for(int i = 0; i<6; i++) {
			reserveSpace[i] = manager.get(AssetsManager.CARDNONE);
		}
		this.x = x;
		this.y = y;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public Texture[] getReserveSpaces() {
		return reserveSpace;
	}
	
	public ArrayList<Card> getReserveCharacters() {
		return reserveCharacters;
	}
	
	public int getEmptySpaces() {
		return emptySpaces;
	}
	
	public void addCharacter(Card c) {
		reserveCharacters.add(c);
		reserveSpace[6-emptySpaces] = c.getCardPic();
		emptySpaces--;
	}
	
	public void withdrawCharacter(Card c) {
		reserveCharacters.remove(c);
	}
}
