package com.ruperthodgkins.csproject;

import com.badlogic.gdx.assets.AssetManager;

public class AssetsManager {
	
	private static AssetManager instance;
	
	public static final String CARDBACK = "data/kof_cardback.png";
	public static final String CARDPOTION = "data/cpotion.png";
	
	private AssetsManager() {
		
	}
	
	public static AssetManager getInstance() {
		if(instance == null) {
			instance = new AssetManager();
		}
		return instance;
	}
	
}
