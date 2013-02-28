package com.ruperthodgkins.csproject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class EndTurnButton {
	private float x;
	private float y;
	private Rectangle bbox;
	private Texture buttonPic;
	
	public EndTurnButton(float x, float y) {
		AssetManager manager = AssetsManager.getInstance();
		buttonPic = manager.get(AssetsManager.BUTTONENDTURN,Texture.class);
		this.x = x;
		this.y = y;
		bbox = new Rectangle(x,y,buttonPic.getWidth(),buttonPic.getHeight());
	}
	
	public Texture getButtonPic() {
		return buttonPic;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public boolean hit(float x, float y) {
		if(bbox.contains(x,Gdx.graphics.getHeight() - y)) {
			return true;
		}
		return false;
	}
	
	public void resize(int width, int height) {
		//x = width-327;
		//y = 434;
		x = width-(buttonPic.getWidth()*(width/1920f))-20;
		y = 434 * (height/1080f);
		bbox.x = x;
		bbox.y = y;
		bbox.setWidth(buttonPic.getWidth()*Game.getWidth()/1920f);
		bbox.setHeight(buttonPic.getHeight()*Game.getHeight()/1080f);
	}
}
