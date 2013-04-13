package com.ruperthodgkins.csproject;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

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
	private Button newGameButton;
	private AssetManager manager;
	private Button newTCPGameButton;
	
	public MainMenuScreen(Game m) {
		main = m;
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		batch.begin();
//		batch.setProjectionMatrix(camera.combined);
//		sprite.draw(batch);
		batch.setProjectionMatrix(batch.getProjectionMatrix().setToOrtho2D(0, 0, Gdx.graphics.getWidth(),  Gdx.graphics.getHeight()));
		
		batch.draw(texture,0,0,texture.getWidth() * Game.getWidth()/1920f,texture.getHeight() * Game.getHeight() / 1080f);
		for(Button b : buttons) {
			b.hit(Gdx.input.getX(), Gdx.input.getY());
			batch.draw(b.getButtonPic(), b.getX(), b.getY(), b.getButtonPic().getWidth()*(Game.getWidth()/1920f), b.getButtonPic().getHeight()*(Game.getHeight()/1080f));
			font.draw(batch, b.getText(), b.getX()+20,b.getY()+32*(Game.getHeight()/1080f));
		}
		font.setColor(Color.WHITE);
		font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 20, Game.getHeight()-10);
		batch.end();
		
		if(newGameButton.getState() == ButtonState.RELEASED) {
			if(!buttons.contains(newHumanGameButton) && !buttons.contains(newAIGameButton)) {
				buttons.add(1,newHumanGameButton);
				buttons.add(2,newAIGameButton);
				buttons.add(3,newTCPGameButton);
				buttons.remove(optionsButton);
				buttons.remove(quitGameButton);
				newGameButton.setText("Back");
				resize(Game.getWidth(),Game.getHeight());
			}
			else {
				buttons.remove(newHumanGameButton);
				buttons.remove(newAIGameButton);
				buttons.remove(newTCPGameButton);
				buttons.add(optionsButton);
				buttons.add(quitGameButton);
				newGameButton.setText("New Game");
				resize(Game.getWidth(),Game.getHeight());
			}
			System.out.println(buttons.size());
		}
		
		if(newHumanGameButton.getState() == ButtonState.RELEASED) {
			main.setScreen(new LoadingScreen(main,0));
		}
	
		if(newAIGameButton.getState() == ButtonState.RELEASED) {
			main.setScreen(new LoadingScreen(main,1));
		}
		
		if(newTCPGameButton.getState() == ButtonState.RELEASED) {
			main.setScreen(new LoadingScreen(main,2));
		}
		
		if(quitGameButton.getState() == ButtonState.RELEASED) {
			System.exit(0);
		}
		
		
	}

	@Override
	public void resize(int width, int height) {
		System.out.println("resizing window");
		float y = Game.getHeight()/2+65;
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
//		float w = Game.getWidth();
//		float h = Game.getHeight();
//		camera = new OrthographicCamera(1, h/w);
		Texture.setEnforcePotImages(false);
		manager = AssetsManager.getInstance();
		texture = manager.get("data/splash.jpg");
		//texture = new Texture(Gdx.files.internal("data/splash.jpg"));
//		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
//		TextureRegion region = new TextureRegion(texture, 0, 0, texture.getWidth(), texture.getHeight());
//		sprite = new Sprite(region);
//		sprite.setSize(1, 1 * sprite.getHeight() / sprite.getWidth());
//		sprite.setPosition(-sprite.getWidth()/2, -sprite.getHeight()/2);
		newGameButton = new Button("New Game",Game.getWidth(),Game.getHeight()/2);
		newHumanGameButton = new Button("Human Versus",Game.getWidth(),Game.getHeight()/2);
		newAIGameButton = new Button("AI Versus",Game.getWidth(),Game.getHeight()/2);
		newTCPGameButton = new Button("TCP/IP Versus",Game.getWidth(),Game.getHeight()/2);
		optionsButton = new Button("Options",20,Game.getHeight()/2-((newHumanGameButton.getButtonPic().getHeight() + 25) * Game.getHeight()/1080f));
		optionsButton.setDisabled(true);
		quitGameButton = new Button("Quit Game",Game.getWidth(),Game.getHeight()/2-((optionsButton.getButtonPic().getHeight() + 25) * Game.getHeight()/1080f));
		//backButton = new Button("< Back",Game.getWidth(),Game.getHeight()/2);
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
		texture.dispose();
		font.dispose();
	}

}
