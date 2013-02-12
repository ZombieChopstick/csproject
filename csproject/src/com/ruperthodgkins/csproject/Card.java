package com.ruperthodgkins.csproject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Card {
	private String name;
	private Texture cardPic;
	private Texture cardBackPic;
	private boolean flipped = false;
	private Vector2 position = new Vector2(0,Gdx.graphics.getHeight()-480);
	
	public Card(String name, Texture pic) {
		this.name = name;
		cardPic = pic;
		AssetManager manager = AssetsManager.getInstance();
		cardBackPic = manager.get(AssetsManager.CARDBACK,Texture.class);
	}
	
	public String getName() {
		return name;
	}
	
	public Texture getCardPic() {
		if(flipped)
			return cardPic;
		else
			return cardBackPic;
	}
	
	public boolean isFlippedOver() {
		return flipped;
	}
	
	public void flip() {
		flipped = !flipped;
	}
	
	public Vector2 getPosition() {
		return position;
	}
	
	public void setPosition(int x, int y) {
		position = new Vector2(x,y);
	}
}
