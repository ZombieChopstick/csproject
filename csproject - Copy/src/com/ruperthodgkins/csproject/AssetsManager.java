package com.ruperthodgkins.csproject;

import com.badlogic.gdx.assets.AssetManager;

public class AssetsManager {
	
	private static AssetManager instance;
	
	public static final String CARDBACK = "data/cardback.png";
	public static final String CARDPOTION = "data/cardpotion.png";
	public static final String CARDSUPERPOTION = "data/cardpotion.png";
	
	public static final String BUTTONENDTURN = "data/buttonendturn.png";
	
	private AssetsManager() {
		
	}
	
	public static AssetManager getInstance() {
		if(instance == null) {
			instance = new AssetManager();
		}
		return instance;
	}
	
}
