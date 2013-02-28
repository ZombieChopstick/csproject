package com.ruperthodgkins.csproject;

import java.util.ArrayList;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class Discard {
	private ArrayList<Card> discardPile;
	private Texture discardPic;
	private AssetManager manager = AssetsManager.getInstance();
	private float x;
	private float y;
	
	public Discard(float x, float y) {
		discardPile = new ArrayList<Card>();
		discardPic = manager.get(AssetsManager.CARDNONE);
	}
	
	public Texture getDiscardPic() {
		return discardPic;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public void addCard(Card c) {
		discardPile.add(0,c);
		discardPic = c.getCardPic();
	}
	
	public void resize(int width, int height) {
		x = width-(discardPic.getWidth()*(width/1920f))-347*(width/1920f);
		y = 0;
	}
}
