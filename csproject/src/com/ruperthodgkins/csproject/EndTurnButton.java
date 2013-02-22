package com.ruperthodgkins.csproject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class EndTurnButton {
	private int x;
	private int y;
	private Rectangle bbox;
	private Texture buttonPic;
	
	public EndTurnButton(int x, int y) {
		AssetManager manager = AssetsManager.getInstance();
		buttonPic = manager.get(AssetsManager.BUTTONENDTURN,Texture.class);
		this.x = x;
		this.y = y;
		bbox = new Rectangle(x,y,buttonPic.getWidth(),buttonPic.getHeight());
	}
	
	public Texture getButtonPic() {
		return buttonPic;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public boolean hit(int x, int y) {
		if(bbox.contains(x,Gdx.graphics.getHeight() - y)) {
			return true;
		}
		return false;
	}
	
	public void resize(int width, int height) {
		x = width-327;
		y = 434;
		bbox.x = x;
		bbox.y = y;
	}
}
