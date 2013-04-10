package com.ruperthodgkins.csproject;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MainMenuScreen implements Screen {
	private Game main;
	private SpriteBatch batch;
	private Button newHumanGameButton;
	private Button newAIGameButton;
	private Button quitGameButton;
	private BitmapFont font;
	private ArrayList<Button> buttons = new ArrayList<Button>();
	private Button optionsButton;
	private Texture texture;
	private Sprite sprite;
	private OrthographicCamera camera;
	private Button newGameButton;
	
	public MainMenuScreen(Game m) {
		main = m;
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.setProjectionMatrix(camera.combined);
		sprite.draw(batch);
		batch.setProjectionMatrix(batch.getProjectionMatrix().setToOrtho2D(0, 0, Gdx.graphics.getWidth(),  Gdx.graphics.getHeight()));
		for(Button b : buttons) {
			b.hit(Gdx.input.getX(), Gdx.input.getY());
			batch.draw(b.getButtonPic(), b.getX(), b.getY(), b.getButtonPic().getWidth()*(Game.getWidth()/1920f), b.getButtonPic().getHeight()*(Game.getHeight()/1080f));
			font.draw(batch, b.getText(), b.getX()+20,b.getY()+32*(Game.getHeight()/1080f));
		}
		batch.end();
		
		if(newGameButton.getState() == ButtonState.RELEASED) {
			buttons.add(newHumanGameButton);
			buttons.add(newAIGameButton);
		}
		
		if(newHumanGameButton.getState() == ButtonState.RELEASED) {
			main.setScreen(new LoadingScreen(main,0));
		}
		
		if(newAIGameButton.getState() == ButtonState.RELEASED) {
			main.setScreen(new LoadingScreen(main,1));
		}
		
		if(quitGameButton.getState() == ButtonState.RELEASED) {
			System.exit(0);
		}
		
		
	}

	@Override
	public void resize(int width, int height) {
		float y = Game.getHeight()/2;
		for(Button b : buttons) {
			b.setX(Game.getWidth()-(b.getButtonPic().getWidth() * Game.getWidth()/1920f)+25);
			b.setY(y);
			y = y - ((b.getButtonPic().getHeight() + 25) * Game.getHeight()/1080f);
			b.resize(width, height);
		}
	}

	@Override
	public void show() {
		batch = new SpriteBatch();
		float w = Game.getWidth();
		float h = Game.getHeight();
		camera = new OrthographicCamera(1, h/w);
		Texture.setEnforcePotImages(false);
		texture = new Texture(Gdx.files.internal("data/splash.jpg"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		TextureRegion region = new TextureRegion(texture, 0, 0, texture.getWidth(), texture.getHeight());
		sprite = new Sprite(region);
		sprite.setSize(1, 1 * sprite.getHeight() / sprite.getWidth());
		sprite.setPosition(-sprite.getWidth()/2, -sprite.getHeight()/2);
		newGameButton = new Button("New Game",Game.getWidth(),Game.getHeight()/2);
		newHumanGameButton = new Button("Human Versus",Game.getWidth(),Game.getHeight()/2);
		newAIGameButton = new Button("AI Versus",Game.getWidth(),Game.getHeight()/2);
		optionsButton = new Button("Options",20,Game.getHeight()/2-((newHumanGameButton.getButtonPic().getHeight() + 25) * Game.getHeight()/1080f));
		quitGameButton = new Button("Quit Game",Game.getWidth(),Game.getHeight()/2-((optionsButton.getButtonPic().getHeight() + 25) * Game.getHeight()/1080f));
		buttons.add(newGameButton);
		buttons.add(optionsButton);
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
		batch.dispose();
		font.dispose();
	}

}
