package com.ruperthodgkins.csproject;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameScreen implements Screen {

	private SpriteBatch batch;
	private Texture texture;
	private AssetManager manager = AssetsManager.getInstance();
	private Game main;
	private Card potionCard;
	private Card superPotionCard;
	private Card holdingCard = null;
	private ArrayList<Card> hand = new ArrayList<Card>();
	
	private Character guard1;
	
	public GameScreen(Game m) {
		main = m;
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		Mesh m = guard1.getMesh();
		if(m != null) {
			m.render(GL10.GL_TRIANGLE_FAN, 0, 6);
		}
		batch.begin();
		batch.setProjectionMatrix(batch.getProjectionMatrix().setToOrtho2D(0, 0, Gdx.graphics.getWidth(),  Gdx.graphics.getHeight()));
		batch.draw(guard1.getCharPic(), guard1.getX(), guard1.getY(), guard1.getCharPic().getWidth(), guard1.getCharPic().getHeight());
		batch.draw(potionCard.getCardPic(), potionCard.getX(), potionCard.getY(), potionCard.getCardPic().getWidth(), potionCard.getCardPic().getHeight());
		batch.draw(superPotionCard.getCardPic(), superPotionCard.getX(), superPotionCard.getY(), superPotionCard.getCardPic().getWidth(), superPotionCard.getCardPic().getHeight());
		batch.end();
		
		if(Gdx.input.isTouched()) {
			if(potionCard.hit(Gdx.input.getX(),Gdx.input.getY()) && holdingCard == null) {
				potionCard.setPosition(Gdx.input.getX()-(potionCard.getCardPic().getWidth()/2), main.getHeight()-Gdx.input.getY()-(potionCard.getCardPic().getHeight()/2));
				holdingCard = potionCard;
			}
			else if(superPotionCard.hit(Gdx.input.getX(),Gdx.input.getY()) && holdingCard == null) {
				superPotionCard.setPosition(Gdx.input.getX()-(potionCard.getCardPic().getWidth()/2), main.getHeight()-Gdx.input.getY()-(potionCard.getCardPic().getHeight()/2));
				holdingCard = superPotionCard;
			}
			else {
				holdingCard.setPosition(Gdx.input.getX()-(holdingCard.getCardPic().getWidth()/2), main.getHeight()-Gdx.input.getY()-(holdingCard.getCardPic().getHeight()/2));
			}
		}
		else {
			holdingCard = null;
		}
		
		int highZ = 0;
		for(Card c : hand) {
			if(c.hit(Gdx.input.getX(),Gdx.input.getY()) && c.getZIndex()>=highZ) {
				c.flipFaceUp();
				highZ = c.getZIndex();
			}
			else {
				c.flipFaceDown();
			}
		}
		//System.out.println(holdingCard);
		if(guard1.hit(Gdx.input.getX(),Gdx.input.getY())) {
			System.out.println("Hit");
		}
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		batch = new SpriteBatch();

		potionCard = new Card("Potion", manager.get(AssetsManager.CARDPOTION, Texture.class));
		superPotionCard = new Card("Super Potion", manager.get(AssetsManager.CARDSUPERPOTION, Texture.class),200,0);
		
		hand.add(potionCard);
		hand.add(superPotionCard);
		
		guard1 = new Character(800,400,"Guard 1",manager.get("data/hex.png",Texture.class), 1, 0, 0, 0, 0, 0, 0);
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
