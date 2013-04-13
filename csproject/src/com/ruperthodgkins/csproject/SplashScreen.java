package com.ruperthodgkins.csproject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SplashScreen implements Screen {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Texture texture;
	private Sprite sprite;
	private Game main;
	private float alpha = 0;
	private boolean fadedIn = false;
	private boolean fadedOut = false;
	private AssetManager manager;
	
	public SplashScreen(Game m) {
		main = m;
		manager = AssetsManager.getInstance();
	}
	
	public void fadeIn(float delta) {
		if(alpha<1) alpha+=delta/2;
		else fadedIn = true;
	}
	
	public void fadeOut(float delta) {
		if(alpha>0.01) alpha-=delta/2;
		else {
			fadedOut = true;
			alpha = 0;
		}
	}
	
	@Override
	public void render(float delta) {
		//System.out.println(alpha);
		if(fadedOut) main.setScreen(new MainMenuScreen(main));
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.setProjectionMatrix(camera.combined);
		sprite.draw(batch,alpha);
		if(!fadedIn) fadeIn(delta);
		else fadeOut(delta);
		batch.end();
		
		//SKIP SCREEN
		if(Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			main.setScreen(new MainMenuScreen(main));
		}
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		float w = Game.getWidth();
		float h = Game.getHeight();
		camera = new OrthographicCamera(1, h/w);
		batch = new SpriteBatch();
		Texture.setEnforcePotImages(false);
		manager.load("data/splash.jpg", Texture.class);
		manager.load("data/buttonup.png",Texture.class);
		manager.load("data/buttonover.png",Texture.class);
		manager.load("data/buttondown.png",Texture.class);
		manager.load("data/buttondisabled.png",Texture.class);
		manager.finishLoading();
		texture = manager.get("data/splash.jpg");
		//texture = new Texture(Gdx.files.internal("data/splash.jpg"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		TextureRegion region = new TextureRegion(texture, 0, 0, texture.getWidth(), texture.getHeight());
		sprite = new Sprite(region);
		sprite.setSize(1, 1 * sprite.getHeight() / sprite.getWidth());
		sprite.setPosition(-sprite.getWidth()/2, -sprite.getHeight()/2);
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
