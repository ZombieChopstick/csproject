package com.ruperthodgkins.csproject;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class EndGameScreen implements Screen {
	
	private String winnerName;
	private SpriteBatch batch;
	private BitmapFont font;
	private Button returnToMenuButton;
	private ArrayList<Button> buttons = new ArrayList<Button>();
	private Game main;
	
	public EndGameScreen(String winner, Game main) {
		winnerName = winner;
		this.main = main;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		batch.begin();
		font.draw(batch, winnerName + " has conquered the land!", Game.getWidth()/2, Game.getHeight()/2);
		for(Button b : buttons) {
			b.hit(Gdx.input.getX(), Gdx.input.getY());
			batch.draw(b.getButtonPic(), b.getX(), b.getY(), b.getButtonPic().getWidth()*(Game.getWidth()/1920f), b.getButtonPic().getHeight()*(Game.getHeight()/1080f));
			font.draw(batch, b.getText(), b.getX()+20,b.getY()+32*(Game.getHeight()/1080f));
		}
		batch.end();
		
		if(returnToMenuButton.getState() == ButtonState.RELEASED) {
			main.setScreen(new MainMenuScreen(main));
		}
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		batch = new SpriteBatch();
		font = new BitmapFont();
		returnToMenuButton = new Button("Return to Main Menu",Game.getWidth()/2,Game.getHeight()/2);
		buttons.add(returnToMenuButton);
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
