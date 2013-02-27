package com.ruperthodgkins.csproject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Card implements Comparable<Card> {
	private String name;
	private Texture cardPic;
	private Texture cardBackPic;
	private boolean faceUp = false;
	private int x;
	private int y;
	private Rectangle bbox;
	private int z = 0;
	private Character representsCharacter = null;
	
	public Card(String name, Texture pic) {
		this.name = name;
		cardPic = pic;
		AssetManager manager = AssetsManager.getInstance();
		cardBackPic = manager.get(AssetsManager.CARDBACK,Texture.class);
		x = 0;
		y = 0;
		bbox = new Rectangle(x,y,cardPic.getWidth(),cardPic.getHeight());
		System.out.println("Card Name: " + name);
		System.out.println("Card Position: " + x + "," + y);
		System.out.println("Card Size: " + cardPic.getWidth() + "x" + cardPic.getHeight());
		System.out.println("Bounds Position: " + bbox.getX() + "," + bbox.getY());
		System.out.println("Bounds Size: " + bbox.getWidth() + "x" + bbox.getHeight());
		z = 0;
	}
	
	public Card(String name, Texture pic, int x, int y) {
		this.name = name;
		cardPic = pic;
		AssetManager manager = AssetsManager.getInstance();
		cardBackPic = manager.get(AssetsManager.CARDBACK,Texture.class);
		this.x = x;
		this.y = y;
		bbox = new Rectangle(x,y,cardPic.getWidth(),cardPic.getHeight());
		System.out.println("Card Name: " + name);
		System.out.println("Card Position: " + x + "," + y);
		System.out.println("Card Size: " + cardPic.getWidth() + "x" + cardPic.getHeight());
		System.out.println("Bounds Position: " + bbox.getX() + "," + bbox.getY());
		System.out.println("Bounds Size: " + bbox.getWidth() + "x" + bbox.getHeight());
		z = 0;
	}
	
	public Texture getCardPic() {
		if(faceUp)
			return cardPic;
		else
			return cardBackPic;
	}
	
	public String getName() { return name; }
	public boolean isFaceUp() { return faceUp; }
	public void flipFaceUp() { faceUp = true; }
	public void flipFaceDown() { faceUp = false; }
	public int getY() { return y; }
	public int getX() { return x; }
	public int getZIndex() { return z; }
	public Character getRepresentsChar() { return representsCharacter; }
	
	public void setRepresentingCharacter(Character c) {
		representsCharacter = c;
	}
	
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
		bbox.setX(x);
		bbox.setY(y);
	}
	
	public void setZIndex(int z) {
		this.z = z;
	}
	
	public boolean hit(int x, int y) {
		if(bbox.contains(x,Gdx.graphics.getHeight() - y)) {
			return true;
		}
		return false;
	}

	@Override
	public int compareTo(Card c) {
		int compareZ = ((Card)c).getZIndex();
		return this.z - compareZ;
	}
}
