package com.ruperthodgkins.csproject;

import java.io.File;
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
	private Button loadProfileButton;
	private Button profileButton1;
	private Button profileButton2;
	private Button profileButton3;
	private Button profileButton4;
	
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
				buttons.remove(loadProfileButton);
				buttons.remove(optionsButton);
				buttons.remove(quitGameButton);
				newGameButton.setText("Back");
				resize(Game.getWidth(),Game.getHeight());
			}
			else {
				buttons.remove(newHumanGameButton);
				buttons.remove(newAIGameButton);
				buttons.remove(newTCPGameButton);
				buttons.add(loadProfileButton);
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
		
		if(loadProfileButton.getState() == ButtonState.RELEASED) {
			if(loadProfileButton.getText().equals("Load Profile")) {
				buttons.remove(optionsButton);
				buttons.remove(newGameButton);
				buttons.remove(quitGameButton);
				File fileList = new File("profiles/");
				String[] profileNames = {"Profile Empty","Profile Empty","Profile Empty","Profile Empty"};
				int i = 0;
				for(File f : fileList.listFiles()) {
					if(f.getName()!="last.txt") {
						profileNames[i] = f.getName().substring(0, 1).toUpperCase() + f.getName().substring(1, f.getName().length()-4);
						i++;
					}
				}
				profileButton1.setText(profileNames[0]);
				buttons.add(profileButton1);
				if(!profileButton1.getText().equals("Profile Empty")) profileButton1.setDisabled(false);
				profileButton2.setText(profileNames[1]);
				buttons.add(profileButton2);
				if(!profileButton2.getText().equals("Profile Empty")) profileButton2.setDisabled(false);
				profileButton3.setText(profileNames[2]);
				buttons.add(profileButton3);
				if(!profileButton3.getText().equals("Profile Empty")) profileButton3.setDisabled(false);
				profileButton4.setText(profileNames[3]);
				buttons.add(profileButton4);
				if(!profileButton4.getText().equals("Profile Empty")) profileButton4.setDisabled(false);
				loadProfileButton.setText("Back");
				resize(Game.getWidth(),Game.getHeight());
			}
			else if(loadProfileButton.getText().equals("Back")){
				buttons.clear();
				buttons.add(newGameButton);
				buttons.add(loadProfileButton);
				loadProfileButton.setText(Profile.getInstance().getCurrentProfile());
				buttons.add(optionsButton);
				buttons.add(quitGameButton);
				resize(Game.getWidth(),Game.getHeight());
			}
		}
		
		if(profileButton1.getState() == ButtonState.RELEASED) {
			Profile.getInstance().loadProfile(profileButton1.getText());
		}
		
		if(profileButton1.getState() == ButtonState.RELEASED) {
			Profile.getInstance().loadProfile(profileButton2.getText());	
		}
		
		if(profileButton1.getState() == ButtonState.RELEASED) {
			Profile.getInstance().loadProfile(profileButton3.getText());
		}
		
		if(profileButton1.getState() == ButtonState.RELEASED) {
			Profile.getInstance().loadProfile(profileButton4.getText());
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
		loadProfileButton = new Button("Load Profile",Game.getWidth(),Game.getHeight()/2);
		optionsButton = new Button("Options",20,Game.getHeight()/2-((newHumanGameButton.getButtonPic().getHeight() + 25) * Game.getHeight()/1080f));
		optionsButton.setDisabled(true);
		profileButton1 = new Button("Profile Empty",Game.getWidth(),Game.getHeight()/2);
		profileButton2 = new Button("Profile Empty",Game.getWidth(),Game.getHeight()/2);
		profileButton3 = new Button("Profile Empty",Game.getWidth(),Game.getHeight()/2);
		profileButton4 = new Button("Profile Empty",Game.getWidth(),Game.getHeight()/2);
		profileButton1.setDisabled(true);
		profileButton2.setDisabled(true);
		profileButton3.setDisabled(true);
		profileButton4.setDisabled(true);
		quitGameButton = new Button("Quit Game",Game.getWidth(),Game.getHeight()/2-((optionsButton.getButtonPic().getHeight() + 25) * Game.getHeight()/1080f));
		//backButton = new Button("< Back",Game.getWidth(),Game.getHeight()/2);
		buttons.add(newGameButton);
		buttons.add(loadProfileButton);
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
