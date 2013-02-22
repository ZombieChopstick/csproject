package com.ruperthodgkins.csproject;

import java.util.ArrayList;
import java.util.Arrays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameScreen implements Screen {
	private SpriteBatch batch;
	private Texture texture;
	private AssetManager manager = AssetsManager.getInstance();
	@SuppressWarnings("unused")
	private Game main;
	private Card potionCard;
	private BitmapFont font;	
	private Character[] hexs = new Character[6];
	private Player player1 = new Player("Rupert");
	private Player player2 = new Player("Adam");
	private TurnController turns = new TurnController(player1, player2);
	private EndTurnButton btnEndTurn = new EndTurnButton(turns.getCurrentPlayer().getDeck().getX(),turns.getCurrentPlayer().getDeck().getY()+434);
	
	public GameScreen(Game m) {
		main = m;
	}
	
	@Override
	public void render(float delta) {
		//CLEAR SCREEN
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		//DRAW HEXAGON MESHES (DEBUGGING)
		/*Mesh m = guard1.getMesh();
		Mesh m2 = guard2.getMesh();
		if(m != null && m2!=null) {
			m.render(GL10.GL_TRIANGLE_FAN, 0, 6);
			m2.render(GL10.GL_TRIANGLE_FAN, 0, 6);
		}*/
		
		//DEBUGGING MESHES (REPLACE WITH TEXTURE)
		for(Character c : hexs) {
			Mesh m = c.getMesh();
			if(m != null) {
				m.render(GL10.GL_TRIANGLE_FAN, 0, 6);
			}
		}
		
		//BEGIN RENDERING
		batch.begin();
		batch.setProjectionMatrix(batch.getProjectionMatrix().setToOrtho2D(0, 0, Gdx.graphics.getWidth(),  Gdx.graphics.getHeight()));
		
		//DEBUGGING
		font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), Game.getWidth()-180, Game.getHeight()-10);
		font.draw(batch, "Mouse: " + Gdx.input.getX() + "," + Gdx.input.getY(), Game.getWidth()-180, Game.getHeight()-30);
		font.draw(batch, "Current Player: " + turns.getCurrentPlayer().getName(), Game.getWidth()-180, Game.getHeight()-50);
		
		//RENDER HEX OVERLAY
		for(Character c : hexs) {
			if(c.hit(Gdx.input.getX(),Gdx.input.getY())) {
				batch.draw(c.getCharPic(), c.getX(), c.getY(), c.getCharPic().getWidth(), c.getCharPic().getHeight());
			}
		}
		
		//RENDER HAND
		//ArrayList<Card> cardsInHand = turns.getPlayer("Rupert").getHand().getCards();
		ArrayList<Card> cardsInHand = turns.getCurrentPlayer().getHand().getCards();
		if(cardsInHand.size() > 0) {
			Card[] handArray = new Card[cardsInHand.size()];
			cardsInHand.toArray(handArray);
			Arrays.sort(handArray);
			for(Card c : handArray) {
				batch.draw(c.getCardPic(), c.getX(), c.getY(), c.getCardPic().getWidth(), c.getCardPic().getHeight());
			}
		}
		
		//RENDER DECK
		//batch.draw(turns.getPlayer("Rupert").getDeck().getDeckTexture(), turns.getPlayer("Rupert").getDeck().getX(), turns.getPlayer("Rupert").getDeck().getY(), turns.getPlayer("Rupert").getDeck().getDeckTexture().getWidth(), turns.getPlayer("Rupert").getDeck().getDeckTexture().getHeight());
		if(turns.getCurrentPlayer().getDeck().visible()) {
			batch.draw(turns.getCurrentPlayer().getDeck().getDeckTexture(), turns.getCurrentPlayer().getDeck().getX(), turns.getCurrentPlayer().getDeck().getY(), turns.getCurrentPlayer().getDeck().getDeckTexture().getWidth(), turns.getCurrentPlayer().getDeck().getDeckTexture().getHeight());
		}
		
		batch.draw(btnEndTurn.getButtonPic(), btnEndTurn.getX(), btnEndTurn.getY(), btnEndTurn.getButtonPic().getWidth(), btnEndTurn.getButtonPic().getHeight());
		if(btnEndTurn.hit(Gdx.input.getX(),Gdx.input.getY()) && Gdx.input.justTouched()) {
			turns.switchPlayer(turns.getCurrentPlayer());
		}
		
		//UPDATE TURN CONTROLLER
		turns.update();
		
		//CHARACTER LOGIC (MOVE TO TURN CONTROLLER)
		/*if(guard1.hit(Gdx.input.getX(),Gdx.input.getY())) {
			batch.draw(guard1.getCharPic(), guard1.getX(), guard1.getY(), guard1.getCharPic().getWidth(), guard1.getCharPic().getHeight());
		}
		if(guard2.hit(Gdx.input.getX(),Gdx.input.getY())) {
			batch.draw(guard2.getCharPic(), guard2.getX(), guard2.getY(), guard2.getCharPic().getWidth(), guard2.getCharPic().getHeight());
		}*/
		
		
		//END RENDER
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		//turns.getPlayer("Rupert").getDeck().resize(width, height);
		turns.getCurrentPlayer().getDeck().resize(width, height);
		btnEndTurn.resize(width, height);
	}

	@Override
	public void show() {
		batch = new SpriteBatch();
		font = new BitmapFont();
		
		//POPULATE WITH DEMO DATA
		for(int i = 0; i<10; i++) {
			potionCard = new Card("Potion", manager.get(AssetsManager.CARDPOTION, Texture.class));
			player1.getDeck().addCard(potionCard);
		}
		for(int i = 0; i<10; i++) {
			potionCard = new Card("Potion", manager.get(AssetsManager.CARDPOTION, Texture.class));
			player2.getDeck().addCard(potionCard);
		}		
		for(int i=0; i<=5; i++) {
			hexs[i] = new Character(200+(i*63),Game.getHeight()-100,"Guard " + (i+1),manager.get("data/hex.png",Texture.class), 1, 0, 0, 0, 0, 0, 0);
		}
		
		//guard1 = new Character(800,400,"Guard 1",manager.get("data/hex.png",Texture.class), 1, 0, 0, 0, 0, 0, 0);
		//guard2 = new Character(863,400,"Guard 1",manager.get("data/hex.png",Texture.class), 1, 0, 0, 0, 0, 0, 0);
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
