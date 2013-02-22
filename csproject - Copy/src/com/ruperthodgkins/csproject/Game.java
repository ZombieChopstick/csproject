package com.ruperthodgkins.csproject;

import com.badlogic.gdx.Gdx;

public class Game extends com.badlogic.gdx.Game {
	
	@Override
	public void create() {		
		setScreen(new LoadingScreen(this));
	}
	
	public static int getHeight() { return Gdx.graphics.getHeight(); }
	public static int getWidth() { return Gdx.graphics.getWidth(); }
}
