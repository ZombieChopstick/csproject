package com.ruperthodgkins.csproject;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class Preview {
	private int x;
	private int y;
	private Texture previewCardPic;
	private AssetManager manager;
	private String chInfo = "";
	private static Preview instance;
	
	public Preview() {
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
				chInfo = "Name: " + c.getName() + "\nHP: " + c.getHP() + "\nXP: " + c.getXP();
				System.out.println(chInfo);
			}
		}
		else {
			previewCardPic = manager.get(AssetsManager.CARDNONE);
			chInfo = "";
		}
	}
	
	public Texture getCardPic() {
		return previewCardPic;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public String getCharacterInformation() {
		return chInfo;
	}
	
	public void resize(int width, int height) {
		x = width-previewCardPic.getWidth()-20;
		y = height-(previewCardPic.getHeight()+100);
	}

	public void setCharacterInformation(String info) {
		chInfo = info;
	}
}