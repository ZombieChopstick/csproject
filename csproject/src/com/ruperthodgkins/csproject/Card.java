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
	private float x;
	private float y;
	private Rectangle bbox;
	private int z = 0;
	//private Character representsCharacter = null;
	
	public Card(String name, Texture pic) {
		this.name = name;
		cardPic = pic;
		AssetManager manager = AssetsManager.getInstance();
		cardBackPic = manager.get(AssetsManager.CARDBACK,Texture.class);
		x = 0;
		y = 0;
		bbox = new Rectangle(x,y,cardPic.getWidth()*(Game.getWidth()/1920f),cardPic.getHeight()*(Game.getHeight()/1080f));
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
		bbox = new Rectangle(x,y,cardPic.getWidth()*(Game.getWidth()/1920f),cardPic.getHeight()*(Game.getHeight()/1080f));
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
	public float getY() { return y; }
	public float getX() { return x; }
	public int getZIndex() { return z; }
	//public Character getRepresentsChar() { return representsCharacter; }
	
	/*public void setRepresentingCharacter(Character c) {
		representsCharacter = c;
	}*/
	
	public void setPosition(float x, float y) {
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
	
	public void resize(int width, int height) {
		//x = x*(width/1920f);
		//y = y*(height/1080f);
		bbox.setX(x);
		bbox.setY(y);
		bbox.setWidth(cardPic.getWidth()*(width/1920f));
		bbox.setHeight(cardPic.getHeight()*(height/1080f));
		System.out.println("Bounds Position: " + bbox.getX() + "," + bbox.getY());
		System.out.println("Bounds Size: " + bbox.getWidth() + "x" + bbox.getHeight());
	}
}
