package com.ruperthodgkins.csproject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Button {
	private float x;
	private float y;
	private Rectangle bbox;
	private Texture buttonPic;
	private String text;
	private ButtonState state;
	private Texture buttonUp;
	private Texture buttonOver;
	private Texture buttonDown;
	private Texture buttonDisabled;
	private AssetManager manager;
	private boolean disabled;
	
	public Button(String text, float x, float y) {
		this.text = text;
		this.x = x;
		this.y = y;
		state = ButtonState.STATIC;
		manager = AssetsManager.getInstance();
		buttonUp = manager.get("data/buttonup.png",Texture.class);
		buttonOver = manager.get("data/buttonover.png",Texture.class);
		buttonDown = manager.get("data/buttondown.png",Texture.class);
		buttonDisabled = manager.get("data/buttondisabled.png",Texture.class);
		this.buttonPic = buttonUp;
		bbox = new Rectangle(x,y,buttonPic.getWidth(),buttonPic.getHeight());
		disabled = false;
	}
	
	public boolean getDisabled() {
		return disabled;
	}
	
	public void setDisabled(boolean disable) {
		disabled = disable;
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
	
	public void setX(float x) {
		this.x = x;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public int hit(float x, float y) {
		if(bbox.contains(x,Gdx.graphics.getHeight() - y) && !disabled) {
			buttonPic = buttonOver;
			if(Gdx.input.isTouched()) {
				buttonPic = buttonDown;
				state = ButtonState.PRESSED;
				return 1;
			}
			else {
				if(state == ButtonState.PRESSED) state = ButtonState.RELEASED;
				else state = ButtonState.STATIC;
				return 2;
			}
		}
		else {
			if(disabled) buttonPic = buttonDisabled;
			else buttonPic = buttonUp;
			state = ButtonState.STATIC;
			return 0;
		}
	}
	
	public ButtonState getState() {
		return state;
	}
	
	public void resize(int width, int height) {
//		x = width-(buttonPic.getWidth()*(width/1920f))-20;
//		y = 434 * (height/1080f);
		bbox.x = x;
		bbox.y = y;
		bbox.setWidth(buttonPic.getWidth()*Game.getWidth()/1920f);
		bbox.setHeight(buttonPic.getHeight()*Game.getHeight()/1080f);
	}
}
