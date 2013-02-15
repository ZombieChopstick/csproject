package com.ruperthodgkins.csproject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class CardActor extends Actor{
	private String name;
	private Texture cardPic;
	private Texture cardBackPic;
	private boolean faceUp = false;
	private int x;
	private int y;
	private Rectangle bbox;
	private int zindex;
	private static int lastz = 0;
	
	public CardActor(String name, Texture pic) {
		this.name = name;
		cardPic = pic;
		AssetManager manager = AssetsManager.getInstance();
		cardBackPic = manager.get(AssetsManager.CARDBACK,Texture.class);
		x = 0;
		y = 0;
		bbox = new Rectangle(x,y,cardPic.getWidth(),cardPic.getHeight());
		System.out.println("Card Position: " + x + "," + y);
		System.out.println("Card Size: " + cardPic.getWidth() + "x" + cardPic.getHeight());
		System.out.println("Bounds Position: " + bbox.getX() + "," + bbox.getY());
		System.out.println("Bounds Size: " + bbox.getWidth() + "x" + bbox.getHeight());
		zindex = lastz;
		lastz++;
	}
	
	public CardActor(String name, Texture pic, int x, int y) {
		this.name = name;
		cardPic = pic;
		AssetManager manager = AssetsManager.getInstance();
		cardBackPic = manager.get(AssetsManager.CARDBACK,Texture.class);
		this.x = x;
		this.y = y;
		bbox = new Rectangle(x,y,cardPic.getWidth(),cardPic.getHeight());
		System.out.println("Card Position: " + x + "," + y);
		System.out.println("Card Size: " + cardPic.getWidth() + "x" + cardPic.getHeight());
		System.out.println("Bounds Position: " + bbox.getX() + "," + bbox.getY());
		System.out.println("Bounds Size: " + bbox.getWidth() + "x" + bbox.getHeight());
		zindex = lastz;
		lastz++;
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
	public int getZIndex() { return zindex; }
	
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
		bbox.setX(x);
		bbox.setY(y);
	}
	
	public boolean hit(int x, int y) {
		if(bbox.contains(x,Gdx.graphics.getHeight() - y)) {
			//System.out.println("Mouse Hit: " + x + "," + y);
			return true;
		}
		return false;
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		// TODO Auto-generated method stub
		batch.draw(getCardPic(), getX(), getY(), getCardPic().getWidth(), getCardPic().getHeight());
	}

	public Actor hit(float x, float y) {
		if(bbox.contains(x,Gdx.graphics.getHeight() - y)) {
			//System.out.println("Mouse Hit: " + x + "," + y);
			return this;
		}
		return null;
	}
	
	public void touchDragged(float x, float y, int pointer) {
		if(hit(x,y) != null) {
			setPosition((int)x,(int)y);
			System.out.println("Moving");
		}
	}
}
