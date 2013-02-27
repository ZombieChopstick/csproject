package com.ruperthodgkins.csproject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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

public class LoadingScreen implements Screen {

	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Texture texture;
	private Sprite sprite;
	private AssetManager manager = AssetsManager.getInstance();
	private BitmapFont font;
	private Game main;
	
	public LoadingScreen(Game m) {
		main = m;
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.setProjectionMatrix(camera.combined);
		sprite.draw(batch);
		batch.setProjectionMatrix(batch.getProjectionMatrix().setToOrtho2D(0, 0, Game.getWidth(),  Game.getHeight()));
		font.setColor(Color.BLACK);
		font.setScale(2);
		
		if(manager.update()) {
			//once loaded go to new screen
			//System.out.println("Loading Complete");
			font.draw(batch,"Loading Complete", Game.getWidth() / 2 - (50 * font.getScaleX()) ,Game.getHeight() - 40);
			main.setScreen(new GameScreen(main));
		}
		else {
			//loading code
			sprite.rotate(sprite.getRotation() + 5);
			/*try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}*/
			font.draw(batch,"Loading...", Game.getWidth() / 2 - (25 * font.getScaleX()) ,Game.getHeight() - 40);
		}
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		float w = Game.getWidth();
		float h = Game.getHeight();
		Texture.setEnforcePotImages(false);
		camera = new OrthographicCamera(1, h/w);
		batch = new SpriteBatch();
		font = new BitmapFont();
	
		manager.load("data/cat.ogg", Music.class);
		manager.load(AssetsManager.CARDBACK,Texture.class);
		manager.load(AssetsManager.CARDNONE,Texture.class);
		manager.load(AssetsManager.CARDPOTION,Texture.class);
		manager.load(AssetsManager.CARDCHARGUARD,Texture.class);
		manager.load(AssetsManager.HEXGREEN,Texture.class);
		manager.load(AssetsManager.HEXGREENHOVER,Texture.class);
		manager.load(AssetsManager.BUTTONENDTURN,Texture.class);
		manager.load(AssetsManager.CHARGUARD,Texture.class);
		
		texture = new Texture(Gdx.files.internal("data/loadingcircle.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		TextureRegion region = new TextureRegion(texture, 0, 0, 82, 82);
		
		sprite = new Sprite(region);
		sprite.setSize(0.1f, 0.1f * sprite.getHeight() / sprite.getWidth());
		sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
		sprite.setPosition(-sprite.getWidth()/2, -sprite.getHeight()/2);
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		batch.dispose();
		texture.dispose();
	}

}
