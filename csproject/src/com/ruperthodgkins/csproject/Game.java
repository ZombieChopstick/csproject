package com.ruperthodgkins.csproject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;

public class Game extends com.badlogic.gdx.Game {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Texture texture;
	private Sprite sprite;
	private AssetManager manager;
	private boolean proceed = false;
	private BitmapFont font;
	private Matrix4 normalProjection;
	
	@Override
	public void create() {		
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		camera = new OrthographicCamera(1, h/w);
		normalProjection = new Matrix4().setToOrtho2D(0, 0, Gdx.graphics.getWidth(),  Gdx.graphics.getHeight());
		batch = new SpriteBatch();
		font = new BitmapFont();
		
		manager = new AssetManager();
		manager.load("data/libgdx.png", Texture.class);
		manager.load("data/loading.png", Texture.class);
		manager.load("data/cat.ogg", Music.class);
		
		texture = new Texture(Gdx.files.internal("data/loading.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		TextureRegion region = new TextureRegion(texture, 0, 0, 32, 32);
		
		sprite = new Sprite(region);
		sprite.setSize(0.1f, 0.1f * sprite.getHeight() / sprite.getWidth());
		sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
		sprite.setPosition(-sprite.getWidth()/2, -sprite.getHeight()/2);
	}

	@Override
	public void dispose() {
		batch.dispose();
		texture.dispose();
	}

	@Override
	public void render() {		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.setProjectionMatrix(camera.combined);
		sprite.draw(batch);
		batch.setProjectionMatrix(normalProjection);
		font.setColor(Color.BLACK);
		font.setScale(2);
		font.draw(batch,"Loading...",Gdx.graphics.getWidth() / 2 - (25 * font.getScaleX()) ,Gdx.graphics.getHeight() - 40);
		
		if(manager.update()) {
			//once loaded go to new screen
		}
		else {
			//loading code
			//due to speed of loading, consider switching to loaded screen but wait for user input before injecting loaded code
		}
		
		if(sprite.getRotation() == 270) { 
			proceed = true; 
		}
		else {
			sprite.rotate(2);
		}
		
		if(manager.getProgress() == 1  && proceed  == true) {
			texture = manager.get("data/libgdx.png",Texture.class);
			TextureRegion region = new TextureRegion(texture, 0, 0, 512, 275);
			
			sprite = new Sprite(region);
			sprite.setSize(0.9f, 0.9f * sprite.getHeight() / sprite.getWidth());
			sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
			sprite.setPosition(-sprite.getWidth()/2, -sprite.getHeight()/2);
		}
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
		manager.load("data/libgdx.png", Texture.class);
		manager.load("data/loading.png", Texture.class);
		manager.load("data/cat.ogg", Music.class);
	}
}
