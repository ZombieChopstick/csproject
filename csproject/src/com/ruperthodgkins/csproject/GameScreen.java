package com.ruperthodgkins.csproject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;

public class GameScreen implements Screen {

	private SpriteBatch batch;
	private Texture texture;
	private AssetManager manager = AssetsManager.getInstance();
	@SuppressWarnings("unused")
	private Game game;
	private BoundingBox box;
	private boolean canMove = false;
	private Card potionCard;
	
	public GameScreen(Game g) {
		game = g;
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		batch.begin();
		batch.setProjectionMatrix(batch.getProjectionMatrix().setToOrtho2D(0, 0, Gdx.graphics.getWidth(),  Gdx.graphics.getHeight()));
		batch.draw(potionCard.getCardPic(), potionCard.getPosition().x, potionCard.getPosition().y, potionCard.getCardPic().getWidth(), potionCard.getCardPic().getHeight());
		batch.end();
		
		if(Gdx.input.isTouched()) {
			//potionCard.flip();
			if(box.contains(new Vector3(Gdx.input.getX(),Gdx.input.getY(),0))) {
				canMove = true;
			}
			else {
				canMove = false;
			}
			
			if(canMove) {
				potionCard.setPosition(Gdx.input.getX()-(potionCard.getCardPic().getWidth()/2), Gdx.graphics.getHeight()-Gdx.input.getY()-(potionCard.getCardPic().getHeight()/2));
			}
		}
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		batch = new SpriteBatch();

		potionCard = new Card("Potion", manager.get(AssetsManager.CARDPOTION, Texture.class));
		box = new BoundingBox(new Vector3(potionCard.getPosition().x,potionCard.getPosition().y,0),new Vector3(potionCard.getPosition().x+345,potionCard.getPosition().y+480,0));
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
