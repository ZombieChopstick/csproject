package com.ruperthodgkins.csproject;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MainMenuScreen implements Screen {
	private Game main;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Button newGameButton;
	private Button quitGameButton;
	private BitmapFont font;
	private ArrayList<Button> buttons = new ArrayList<Button>();
	
	public MainMenuScreen(Game m) {
		main = m;
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		batch.begin();
		//batch.setProjectionMatrix(camera.combined);
		batch.setProjectionMatrix(batch.getProjectionMatrix().setToOrtho2D(0, 0, Gdx.graphics.getWidth(),  Gdx.graphics.getHeight()));
		//sprite.draw(batch,alpha);
		batch.draw(newGameButton.getButtonPic(), newGameButton.getX(), newGameButton.getY(), newGameButton.getButtonPic().getWidth()*(Game.getWidth()/1920f), newGameButton.getButtonPic().getHeight()*(Game.getHeight()/1080f));
		font.draw(batch, newGameButton.getText(), newGameButton.getX()+20,newGameButton.getY()+25);
		batch.draw(quitGameButton.getButtonPic(), quitGameButton.getX(), quitGameButton.getY(), quitGameButton.getButtonPic().getWidth()*(Game.getWidth()/1920f), quitGameButton.getButtonPic().getHeight()*(Game.getHeight()/1080f));
		font.draw(batch, quitGameButton.getText(), quitGameButton.getX()+20,quitGameButton.getY()+25);
		batch.end();
		
		for(Button b : buttons) {
			int result = b.hit(Gdx.input.getX(), Gdx.input.getY());
			if(result==2) {
				main.setScreen(new LoadingScreen(main));
			}
		}
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		float w = Game.getWidth();
		float h = Game.getHeight();
		camera = new OrthographicCamera(1, h/w);
		batch = new SpriteBatch();
		Texture.setEnforcePotImages(false);
		newGameButton = new Button("New Game",Game.getWidth()/2,Game.getHeight()/2);
		quitGameButton = new Button("Quit Game",Game.getWidth()/2,Game.getHeight()/2-50);
		buttons.add(newGameButton);
		buttons.add(quitGameButton);
		font = new BitmapFont();
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
