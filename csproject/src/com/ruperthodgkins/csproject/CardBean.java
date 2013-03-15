package com.ruperthodgkins.csproject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class CardBean implements Comparable<CardBean> {
	private float x;
	private float y;
	private String name;
	private Texture cardPic;
	private Texture cardBackPic;
	private boolean faceUp;
	private Rectangle bbox = null;
	private int zIndex;
	private Player owner;
	
	public CardBean() {
		AssetManager manager = AssetsManager.getInstance();
		cardBackPic = manager.get(AssetsManager.CARDBACK,Texture.class);
		x = 0;
		y = 0;
		zIndex = 0;
		owner = null;
		faceUp = false;
	}
	
	public Texture getCardPic() {
		if(faceUp)
			return cardPic;
		else
			return cardBackPic;
	}
	
	public void setCardPic(Texture pic) {
		this.cardPic = pic;
		bbox = new Rectangle(x,y,cardPic.getWidth()*(Game.getWidth()/1920f),cardPic.getHeight()*(Game.getHeight()/1080f));
	}
	
	public Player getOwner() {
		return owner;
	}
	
	public void setOwner(Player p) {
		this.owner = p;
	}
	
	public String getName() { 
		return name; 
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isFaceUp() { 
		return faceUp; 
	}
	
	public void setFaceUp(boolean faceUp) { 
		this.faceUp = faceUp; 
	}
	
	public void flipFaceUp() { 
		faceUp = true; 
	}
	
	public float getY() { 
		return y; 
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public float getX() { 
		return x;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public int getZIndex() {
		return zIndex; 
	}	
	
	public void setZIndex(int z) {
		zIndex = z;
	}
	
	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
		bbox.setX(x);
		bbox.setY(y);
	}
	
	public boolean hit(int x, int y) {
		if(bbox.contains(x,Gdx.graphics.getHeight() - y)) {
			return true;
		}
		return false;
	}

	@Override
	public int compareTo(CardBean c) {
		int compareZ = ((CardBean)c).getZIndex();
		return this.zIndex - compareZ;
	}
	
	public void resize(int width, int height) {
		bbox.setX(x);
		bbox.setY(y);
		bbox.setWidth(cardPic.getWidth()*(width/1920f));
		bbox.setHeight(cardPic.getHeight()*(height/1080f));
		System.out.println("Bounds Position: " + bbox.getX() + "," + bbox.getY());
		System.out.println("Bounds Size: " + bbox.getWidth() + "x" + bbox.getHeight());
	}
}
