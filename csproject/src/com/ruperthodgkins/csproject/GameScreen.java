package com.ruperthodgkins.csproject;

import java.util.ArrayList;
import java.util.Arrays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class GameScreen implements Screen {
	private SpriteBatch batch;
	private Texture texture;
	private AssetManager manager = AssetsManager.getInstance();
	@SuppressWarnings("unused")
	private Game main;
	private BitmapFont font;	
	private Character[] chars = new Character[12];
	private Player player1 = new Player();
	private Player player2 = new Player();
	private TurnController turns = new TurnController(player1, player2);
	private EndTurnButton btnEndTurn = new EndTurnButton(turns.getCurrentPlayer().getDeck().getX(),turns.getCurrentPlayer().getDeck().getY()+434);
	private Board board;
	private Preview preview;
	private GameEvents gameEvents;
	
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
		/*for(Character c : chars) {
			Mesh m = c.getMesh();
			if(m != null) {
				m.render(GL10.GL_TRIANGLE_FAN, 0, 6);
			}
		}*/
		
		/*for(BoardHex hex : board.getBoard().values()) {
			Mesh m = hex.getMesh();
			if(m!=null) {
				m.render(GL10.GL_TRIANGLE_FAN,0,6);
			}
		}*/
		
		//BEGIN RENDERING
		batch.begin();
		batch.setProjectionMatrix(batch.getProjectionMatrix().setToOrtho2D(0, 0, Gdx.graphics.getWidth(),  Gdx.graphics.getHeight()));
		
		//DEBUGGING
		font.setColor(Color.WHITE);
		font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 20, Game.getHeight()-10);
		font.draw(batch, "Mouse: " + Gdx.input.getX() + "," + Gdx.input.getY(), 20, Game.getHeight()-30);
		font.draw(batch, "Current Player: " + turns.getCurrentPlayer().getName(), 20, Game.getHeight()-50);
		
		//RENDER BOARD
		for(BoardHex hex : board.getBoard().values()) {
			if(hex.hit(Gdx.input.getX(),Gdx.input.getY())) {
				font.draw(batch, "Co-ordinates: " + hex.getCoordinates(), 20, Game.getHeight()-70);
			}
			batch.draw(hex.getHexPic(), hex.getX(), hex.getY(), hex.getHexPic().getWidth(), hex.getHexPic().getHeight());
		}
		
		//RENDER CHARACTERS ON BOARD
		for(Character c : Board.getCharacters().values()) {
			batch.draw(c.getCharPic(), c.getX(), c.getY(), c.getCharPic().getWidth(), c.getCharPic().getHeight());
			if(c.getSelected()) {
				Sprite select = new Sprite((Texture) manager.get(AssetsManager.HEXGREENHOVER));
				select.translate(c.getX(), c.getY());
				select.draw(batch,0.6f);
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
				batch.draw(c.getCardPic(), c.getX(), c.getY(), c.getCardPic().getWidth()*(Game.getWidth()/1920f), c.getCardPic().getHeight()*(Game.getHeight()/1080f));
			}
		}
		
		//RENDER DECK
		//batch.draw(turns.getPlayer("Rupert").getDeck().getDeckTexture(), turns.getPlayer("Rupert").getDeck().getX(), turns.getPlayer("Rupert").getDeck().getY(), turns.getPlayer("Rupert").getDeck().getDeckTexture().getWidth(), turns.getPlayer("Rupert").getDeck().getDeckTexture().getHeight());
		if(turns.getCurrentPlayer().getDeck().visible()) {
			batch.draw(turns.getCurrentPlayer().getDeck().getDeckTexture(), turns.getCurrentPlayer().getDeck().getX(), turns.getCurrentPlayer().getDeck().getY(), turns.getCurrentPlayer().getDeck().getDeckTexture().getWidth()*(Game.getWidth()/1920f), turns.getCurrentPlayer().getDeck().getDeckTexture().getHeight()*(Game.getHeight()/1080f));
		}
		else {
			batch.draw(turns.getCurrentPlayer().getDeck().getNoDeckPic(), turns.getCurrentPlayer().getDeck().getX(), turns.getCurrentPlayer().getDeck().getY(), turns.getCurrentPlayer().getDeck().getNoDeckPic().getWidth()*(Game.getWidth()/1920f), turns.getCurrentPlayer().getDeck().getNoDeckPic().getHeight()*(Game.getHeight()/1080f));
		}
		
		//RENDER DISCARD PILE
		batch.setColor(Color.GRAY);
		batch.draw(turns.getCurrentPlayer().getDiscardPile().getDiscardPic(), turns.getCurrentPlayer().getDiscardPile().getX(), turns.getCurrentPlayer().getDiscardPile().getY(), turns.getCurrentPlayer().getDiscardPile().getDiscardPic().getWidth()*(Game.getWidth()/1920f), turns.getCurrentPlayer().getDiscardPile().getDiscardPic().getHeight()*(Game.getHeight()/1080f));
		font.drawMultiLine(batch, turns.getCurrentPlayer().getDiscardPile().getDiscardedCharText(), turns.getCurrentPlayer().getDiscardPile().getX()+30, turns.getCurrentPlayer().getDiscardPile().getY()+200*(Game.getHeight()/1080f), turns.getCurrentPlayer().getDiscardPile().getDiscardPic().getWidth()*(Game.getWidth()/1920f)-65, BitmapFont.HAlignment.CENTER);
		batch.setColor(Color.WHITE);
		
		font.drawMultiLine(batch, "Game Time: " + turns.getGameTime(), preview.getX()-preview.getCardPic().getWidth()*(Game.getWidth()/1920f), preview.getY()-50*(Game.getHeight()/1920f), preview.getCardPic().getWidth()*(Game.getWidth()/1920f)-25*(Game.getWidth()/1920f), BitmapFont.HAlignment.CENTER);
		
		//RENDER PREVIEW
		batch.draw(preview.getCardPic(), preview.getX(), preview.getY(), preview.getCardPic().getWidth()*(Game.getWidth()/1920f), preview.getCardPic().getHeight()*(Game.getHeight()/1080f));
		if(turns.getCurrentPlayer().getHand().getHoldingCard() == null) {
			font.setColor(Color.BLACK);
			font.drawMultiLine(batch, preview.getCharacterInformation(), preview.getX()+30, preview.getY()+200*(Game.getHeight()/1080f), preview.getCardPic().getWidth()*(Game.getWidth()/1920f)-65, BitmapFont.HAlignment.CENTER);
		}
		
		//RENDER GAME EVENTS
		batch.draw(gameEvents.getBackgroundPic(), gameEvents.getX(), gameEvents.getY(), gameEvents.getBackgroundPic().getWidth()*(Game.getWidth()/1920f), gameEvents.getBackgroundPic().getHeight()*(Game.getHeight()/1080f));
		font.setColor(Color.WHITE);
		font.drawWrapped(batch, "Game Events", gameEvents.getX()+20*(Game.getWidth()/1920f), gameEvents.getY()+400*(Game.getHeight()/1080f), gameEvents.getBackgroundPic().getWidth()*(Game.getWidth()/1920f)-35, BitmapFont.HAlignment.CENTER);
		font.drawWrapped(batch, gameEvents.getEvents(), gameEvents.getX()+20*(Game.getWidth()/1920f), gameEvents.getY()+15*(Game.getHeight()/1080f)+35*gameEvents.displayedEvents(), gameEvents.getBackgroundPic().getWidth()*(Game.getWidth()/1920f)-35, BitmapFont.HAlignment.LEFT);
		
		//RENDER TURN CONTROLLER
		batch.draw((Texture) manager.get(AssetsManager.CHARGUARD), gameEvents.getX(), gameEvents.getY()+330,63, 72);
		font.draw(batch, "Player 1: " + turns.getPlayer(0).getName(), gameEvents.getX()+ 70, gameEvents.getY()+380);
		font.draw(batch, "Green Team", gameEvents.getX()+ 70, gameEvents.getY()+360);
		batch.draw((Texture) manager.get(AssetsManager.CHARGUARDRED), gameEvents.getX()+235, gameEvents.getY()+330,63, 72);
		font.draw(batch, "Player 2: " + turns.getPlayer(1).getName(), gameEvents.getX()+ 305, gameEvents.getY()+380);
		font.draw(batch, "Red Team", gameEvents.getX()+ 305, gameEvents.getY()+360);
		
		font.draw(batch, "It is currently " + turns.getCurrentPlayer().getName() + "\'s turn.", gameEvents.getX()+ 130, gameEvents.getY()+325);
		
		//RENDER END TURN BUTTON
		batch.draw(btnEndTurn.getButtonPic(), btnEndTurn.getX(), btnEndTurn.getY(), btnEndTurn.getButtonPic().getWidth()*(Game.getWidth()/1920f), btnEndTurn.getButtonPic().getHeight()*(Game.getHeight()/1080f));
		if(btnEndTurn.hit(Gdx.input.getX(),Gdx.input.getY()) && Gdx.input.justTouched()) {
			turns.switchPlayer(turns.getCurrentPlayer());
		}
		
		//UPDATE THE BOARD
		board.update();
		
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
		for(Player p : turns.getPlayers()) {
			p.getDeck().resize(width, height);
			p.getHand().resize(width, height);
			p.getDiscardPile().resize(width, height);
		}
		//turns.getCurrentPlayer().getDeck().resize(width, height);
		//turns.getCurrentPlayer().getDiscardPile().resize(width,height);
		btnEndTurn.resize(width, height);
		board.resize(width, height);
		preview.resize(width, height);
		gameEvents.resize(width, height);
		/*currentWidth = width;
		currentHeight = height;
		System.out.println(currentWidth + "x" + currentHeight);
		System.out.println(turns.getCurrentPlayer().getDeck().getDeckTexture().getWidth()*(currentWidth/1920));
		System.out.println(turns.getCurrentPlayer().getDeck().getDeckTexture().getHeight()*(currentHeight/1080));
		float scale = currentWidth/1920f;
		System.out.println(scale);*/
	}

	@Override
	public void show() {
		batch = new SpriteBatch();
		font = new BitmapFont();
		
		//POPULATE WITH DEMO DATA
		for(int i = 0; i<10; i++) {
			//potionCard = new Card("Potion", manager.get(AssetsManager.CARDPOTION, Texture.class));
			Card potionCard = new Card();
			potionCard.setName("Potion");
			potionCard.setCardPic((Texture)manager.get(AssetsManager.CARDPOTION));
			//potionCard.setOwner(player1);
			player1.getDeck().addCard(potionCard);
		}
		for(int i = 0; i<10; i++) {
			//potionCard = new Card("Potion", manager.get(AssetsManager.CARDPOTION, Texture.class));
			Card potionCard = new Card();
			potionCard.setName("Potion");
			potionCard.setCardPic((Texture)manager.get(AssetsManager.CARDPOTION));
			//potionCard.setOwner(player2);
			player2.getDeck().addCard(potionCard);
		}
		
		player1.setName("Rupert");
		player1.setTeamColour("green");
		player2.setName("Adam");
		player2.setTeamColour("red");
		board = new Board(200,Game.getHeight()-80);
		board.setupBoard(11);
		Vector2[] pos = new Vector2[12];
//		pos[0] = new Vector2(0,0);
//		pos[1] = new Vector2(0,3);
//		pos[2] = new Vector2(0,5);
//		pos[3] = new Vector2(2,0);
//		pos[4] = new Vector2(2,3);
//		pos[5] = new Vector2(4,8);
		pos[0] = new Vector2(0,0);
		pos[1] = new Vector2(0,1);
		pos[2] = new Vector2(0,2);
		pos[3] = new Vector2(0,3);
		pos[4] = new Vector2(0,4);
		pos[5] = new Vector2(0,5);
		
		for(int i=0; i<=5; i++) {
			//hexs[i] = new Character(200+(i*63),Game.getHeight()-100,"Guard " + (i+1),manager.get(AssetsManager.CHARGUARD,Texture.class), 1, 0, 0, 0, 0, 0, 0);
			chars[i] = new Character((int)board.getPosition(pos[i]).x,(int)board.getPosition(pos[i]).y,"Guard " + (i+1),manager.get(AssetsManager.CHARGUARD,Texture.class), 1, 0, 0, 0, 0, 0, 50);
			chars[i].setOwner(player1);
			chars[i].setCharacterType(CharacterType.GUARD);
			board.addCharacter(pos[i], chars[i]);
		}
//		
//		pos[6] = new Vector2(10,0);
//		pos[6] = new Vector2(10,0);
//		pos[7] = new Vector2(9,3);
//		pos[8] = new Vector2(8,5);
//		pos[9] = new Vector2(7,0);
//		pos[10] = new Vector2(7,3);
//		pos[11] = new Vector2(5,8);
		pos[6] = new Vector2(1,0);
		pos[7] = new Vector2(1,1);
		pos[8] = new Vector2(1,2);
		pos[9] = new Vector2(1,3);
		pos[10] = new Vector2(1,4);
		pos[11] = new Vector2(1,5);
		
		for(int i=6; i<=11; i++) {
			//hexs[i] = new Character(200+(i*63),Game.getHeight()-100,"Guard " + (i+1),manager.get(AssetsManager.CHARGUARD,Texture.class), 1, 0, 0, 0, 0, 0, 0);
			chars[i] = new Character((int)board.getPosition(pos[i]).x,(int)board.getPosition(pos[i]).y,"Guard " + (i+1),manager.get(AssetsManager.CHARGUARDRED,Texture.class), 1, 0, 0, 0, 0, 0, 50);
			chars[i].setOwner(player2);
			chars[i].setCharacterType(CharacterType.GUARD);
			board.addCharacter(pos[i], chars[i]);
		}
		
		preview = Preview.getInstance();
		gameEvents = GameEvents.getInstance();
		
		Music background = manager.get("data/music_background.ogg");
		//background.play();
		background.setLooping(true);
		
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