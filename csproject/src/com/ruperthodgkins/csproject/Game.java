package com.ruperthodgkins.csproject;

public class Game extends com.badlogic.gdx.Game {
	
	@Override
	public void create() {		
		setScreen(new LoadingScreen(this));
	}
}
