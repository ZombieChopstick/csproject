package com.ruperthodgkins.csproject;

import java.util.ArrayList;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class GameEvents {
	private float x;
	private float y;
	private Texture backgroundPic;
	private AssetManager manager;
	private static GameEvents instance;
	private ArrayList<String> events = new ArrayList<String>();
	
	private GameEvents() {
		manager = AssetsManager.getInstance();
		backgroundPic = manager.get(AssetsManager.CARDNONE);
		x = Game.getWidth()-backgroundPic.getWidth()*2-30;
		y = Game.getHeight()-(backgroundPic.getHeight()*2);
	}
	
	public static GameEvents getInstance() {
		if(instance == null) {
			instance = new GameEvents();
		}
		return instance;
	}
	
	public Texture getBackgroundPic() {
		return backgroundPic;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public void resize(int width, int height) {
		x = width-((backgroundPic.getWidth()*2)*(width/1920f))-30;
		y = height-(backgroundPic.getHeight()*(height/1080f)+160*(height/1080f));
	}
	
	public void addEvent(String eventText) {
		events.add(eventText);
		if(events.size()>7) {
			events.remove(0);
		}
	}
	
	public int displayedEvents() {
		return events.size();
	}
	
	public String getEventText() {
		StringBuilder sb = new StringBuilder();
		for(String s : events) {
			sb.append(s).append("\n");
		}
		return sb.toString();
	}
	
	public String getEvents() {
		return getEventText();
	}
}
