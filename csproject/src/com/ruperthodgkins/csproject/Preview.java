package com.ruperthodgkins.csproject;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class Preview {
	private float x;
	private float y;
	private Texture previewCardPic;
	private AssetManager manager;
	private String chInfo = "";
	private static Preview instance;
	
	private Preview() {
		manager = AssetsManager.getInstance();
		previewCardPic = manager.get(AssetsManager.CARDNONE);
		x = Game.getWidth()-previewCardPic.getWidth()-20;
		y = Game.getHeight()-(previewCardPic.getHeight()*2);
	}
	
	public static Preview getInstance() {
		if(instance == null) {
			instance = new Preview();
		}
		return instance;
	}
	
	public void setCardPic(Texture tex, Character c) {
		if(tex!=null) {
			previewCardPic = tex;
			if(c!=null) {
				chInfo = c.getName() + "\nHP: " + c.getHP() + "/" + c.getMaxHP() + "\nXP: " + c.getXP() + "\nOwner: " + c.getOwner().getName();
			}
		}
		else if(tex==null && c==null) {
			previewCardPic = manager.get(AssetsManager.CARDNONE);
			chInfo = "";
		}
	}
	
	public Texture getCardPic() {
		return previewCardPic;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public String getCharacterInformation() {
		return chInfo;
	}
	
	public void resize(int width, int height) {
		x = width-(previewCardPic.getWidth()*(width/1920f))-20;
		y = height-(previewCardPic.getHeight()*(height/1080f)+160*(height/1080f));
	}

	public void setCharacterInformation(String info) {
		chInfo = info;
	}
}