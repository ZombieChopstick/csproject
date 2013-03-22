package com.ruperthodgkins.csproject;

import com.badlogic.gdx.assets.AssetManager;

public class AssetsManager {
	
	private static AssetManager instance;
	
	public static final String CARDBACK = "data/cardback.png";
	public static final String CARDPOTION = "data/cardpotion.png";
	public static final String CARDSUPERPOTION = "data/cardpotion.png";
	public static final String CARDNONE = "data/nocard.png";
	public static final String CARDCHARGUARD = "data/cardchar.png";
	public static final String CARDCHARGUARDRED = "data/cardcharred.png";
	
	public static final String BUTTONENDTURN = "data/buttonendturn.png";
	
	public static final String HEXGREEN = "data/hex.png";
	public static final String HEXGREENHOVER = "data/hexhover.png";
	
	public static final String CHARGUARD = "data/guard.png";
	public static final String CHARGUARDRED = "data/guard_red.png";
	
	private AssetsManager() {
		
	}
	
	public static AssetManager getInstance() {
		if(instance == null) {
			instance = new AssetManager();
		}
		return instance;
	}
	
}
