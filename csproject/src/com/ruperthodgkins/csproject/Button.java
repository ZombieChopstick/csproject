package com.ruperthodgkins.csproject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Button {
	private float x;
	private float y;
	private Rectangle bbox;
	private Texture buttonPic;
	private String text;
	private ButtonState state;
	
	public Button(String text, float x, float y) {
		this.text = text;
		this.x = x;
		this.y = y;
		this.buttonPic = new Texture(Gdx.files.internal("data/buttonup.png"));
		bbox = new Rectangle(x,y,buttonPic.getWidth(),buttonPic.getHeight());
		state = ButtonState.STATIC;
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
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public void buttonPressedEvent() {
		
	}
	
	public int hit(float x, float y) {
		if(bbox.contains(x,Gdx.graphics.getHeight() - y)) {
			buttonPic = new Texture(Gdx.files.internal("data/buttonover.png"));
			if(Gdx.input.isTouched()) {
				buttonPic = new Texture(Gdx.files.internal("data/buttondown.png"));
				state = ButtonState.PRESSED;
				return 2;
			}
			else {
				if(state == ButtonState.PRESSED) state = ButtonState.RELEASED;
			}
			return 1;
		}
		else {
			buttonPic = new Texture(Gdx.files.internal("data/buttonup.png"));
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
