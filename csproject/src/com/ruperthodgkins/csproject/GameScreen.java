package com.ruperthodgkins.csproject;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
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
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class GameScreen implements Screen {
	private SpriteBatch batch;
	private Texture texture;
	private AssetManager manager = AssetsManager.getInstance();
	private Game main;
	private BitmapFont font;
	private Character[] chars = new Character[14];
	private Player player1;
	private Player player2;
	private TurnController turns;
	private Board board;
	private Preview preview;
	private GameEvents gameEvents;
	private int gameMode;
	private Button endGameButton;
	private Button endTurnButton;
	private ArrayList<Button> buttons = new ArrayList<Button>();
	private Button withdrawUnitButton;

	public GameScreen(Game m, int mode, Socket serverConn) {
		main = m;
		gameMode = mode;
		if (serverConn != null) {
		}
	}

	@Override
	public void render(float delta) {
		// CLEAR SCREEN
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		// DRAW HEXAGON MESHES (DEBUGGING)
		/*
		 * Mesh m = guard1.getMesh(); Mesh m2 = guard2.getMesh(); if(m != null
		 * && m2!=null) { m.render(GL10.GL_TRIANGLE_FAN, 0, 6);
		 * m2.render(GL10.GL_TRIANGLE_FAN, 0, 6); }
		 */

		// DEBUGGING MESHES (REPLACE WITH TEXTURE)
		/*
		 * for(Character c : chars) { Mesh m = c.getMesh(); if(m != null) {
		 * m.render(GL10.GL_TRIANGLE_FAN, 0, 6); } }
		 */

		/*for (BoardHex hex : board.getBoard().values()) {
			Mesh m = hex.getMesh();
			if (m != null) {
				m.render(GL10.GL_TRIANGLE_FAN, 0, 6);
			}
		}*/

		/*Mesh mesh = new Mesh(true, 6, 6, new VertexAttribute(Usage.Position, 3,
				"a_position"));
		float[] v = { 297.12354f, 288.00583f, 0, 297.12354f, 313.61835f, 0,
				319.5345f, 326.4246f, 0, 341.9454f, 313.61835f, 0, 341.9454f,
				288.00583f, 0, 319.5345f, 275.1996f, 0 };
		mesh.setVertices(v);
		mesh.setIndices(new short[] { 0, 1, 2, 3, 4, 5 });
		mesh.render(GL10.GL_TRIANGLE_FAN, 0, 6);*/

		// BEGIN RENDERING
		batch.begin();
		batch.setProjectionMatrix(batch.getProjectionMatrix().setToOrtho2D(0,
				0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

		// DEBUGGING
		font.setColor(Color.WHITE);
		font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 20,
				Game.getHeight() - 10);
		font.draw(batch, "Mouse: " + Gdx.input.getX() + "," + Gdx.input.getY(),
				20, Game.getHeight() - 30);
		font.draw(batch, "Current Player: "
				+ turns.getCurrentPlayer().getName(), 20, Game.getHeight() - 50);

		// RENDER BOARD
		for (BoardHex hex : board.getBoard().values()) {
			if (hex.hit(Gdx.input.getX(), Gdx.input.getY())) {
				font.draw(batch, "Co-ordinates: " + hex.getCoordinates(), 20,
						Game.getHeight() - 70);
			}
			batch.draw(hex.getHexPic(), hex.getX(), hex.getY(),hex.getHexPic().getWidth() * Game.getWidth() / 1920f,hex.getHexPic().getHeight() * Game.getHeight() / 1080f);
		}
		
		// RENDER PREVIEW
		batch.draw(preview.getCardPic(), preview.getX(), preview.getY(),
				preview.getCardPic().getWidth() * (Game.getWidth() / 1920f),
				preview.getCardPic().getHeight() * (Game.getHeight() / 1080f));
		if (turns.getCurrentPlayer().getHand().getHoldingCard() == null) {
			font.setColor(Color.BLACK);
			font.drawMultiLine(batch, preview.getCharacterInformation(),
					preview.getX() + 30,
					preview.getY() + 200 * (Game.getHeight() / 1080f), preview
							.getCardPic().getWidth()
							* (Game.getWidth() / 1920f) - 65,
					BitmapFont.HAlignment.CENTER);
		}
		font.setColor(Color.WHITE);

		// RENDER CHARACTERS ON BOARD
		for (Character c : Board.getCharacters().values()) {
			batch.draw(c.getCharPic(), c.getX(), c.getY(), c.getCharPic()
					.getWidth() * (Game.getWidth() / 1920f), c.getCharPic()
					.getHeight() * (Game.getHeight() / 1080f));
			if (c.getSelected()) {
				// Sprite select = new Sprite((Texture)
				// manager.get(AssetsManager.HEXGREENHOVER));
				// select.translate(c.getX(), c.getY());
				// select.scale(Game.getWidth()/1920f);
				// select.draw(batch,0.6f);
				Texture select = (Texture) manager
						.get(AssetsManager.HEXGREENHOVER);
				batch.setColor(1, 1, 1, 0.6f);
				batch.draw(select, c.getX(), c.getY(), select.getWidth()
						* (Game.getWidth() / 1920f),
						select.getHeight() * (Game.getHeight() / 1080f));
				batch.setColor(1, 1, 1, 1);
				withdrawUnitButton.hit(Gdx.input.getX(), Gdx.input.getY());
				batch.draw(withdrawUnitButton.getButtonPic(), withdrawUnitButton.getX(), withdrawUnitButton.getY(), withdrawUnitButton.getButtonPic()
						.getWidth() * (Game.getWidth() / 1920f), withdrawUnitButton.getButtonPic()
						.getHeight() * (Game.getHeight() / 1080f));
				font.draw(batch, withdrawUnitButton.getText(), withdrawUnitButton.getX() + 20,
						withdrawUnitButton.getY() + 32 * (Game.getHeight() / 1080f));
			}
		}

		// RENDER HAND
		// ArrayList<Card> cardsInHand =
		// turns.getPlayer("Rupert").getHand().getCards();
		ArrayList<Card> cardsInHand = turns.getCurrentPlayer().getHand()
				.getCards();
		if (cardsInHand.size() > 0) {
			Card[] handArray = new Card[cardsInHand.size()];
			cardsInHand.toArray(handArray);
			Arrays.sort(handArray);
			for (Card c : handArray) {
				batch.draw(c.getCardPic(), c.getX(), c.getY(), c.getCardPic()
						.getWidth() * (Game.getWidth() / 1920f), c.getCardPic()
						.getHeight() * (Game.getHeight() / 1080f));
			}
		}

		// RENDER DECK
		// batch.draw(turns.getPlayer("Rupert").getDeck().getDeckTexture(),
		// turns.getPlayer("Rupert").getDeck().getX(),
		// turns.getPlayer("Rupert").getDeck().getY(),
		// turns.getPlayer("Rupert").getDeck().getDeckTexture().getWidth(),
		// turns.getPlayer("Rupert").getDeck().getDeckTexture().getHeight());
		if (turns.getCurrentPlayer().getDeck().visible()) {
			batch.draw(turns.getCurrentPlayer().getDeck().getDeckTexture(),
					turns.getCurrentPlayer().getDeck().getX(), turns
							.getCurrentPlayer().getDeck().getY(), turns
							.getCurrentPlayer().getDeck().getDeckTexture()
							.getWidth()
							* (Game.getWidth() / 1920f), turns
							.getCurrentPlayer().getDeck().getDeckTexture()
							.getHeight()
							* (Game.getHeight() / 1080f));
		} else {
			batch.draw(turns.getCurrentPlayer().getDeck().getNoDeckPic(), turns
					.getCurrentPlayer().getDeck().getX(), turns
					.getCurrentPlayer().getDeck().getY(), turns
					.getCurrentPlayer().getDeck().getNoDeckPic().getWidth()
					* (Game.getWidth() / 1920f), turns.getCurrentPlayer()
					.getDeck().getNoDeckPic().getHeight()
					* (Game.getHeight() / 1080f));
		}

		// RENDER DISCARD PILE
		batch.setColor(Color.GRAY);
		batch.draw(turns.getCurrentPlayer().getDiscardPile().getDiscardPic(),
				turns.getCurrentPlayer().getDiscardPile().getX(), turns
						.getCurrentPlayer().getDiscardPile().getY(), turns
						.getCurrentPlayer().getDiscardPile().getDiscardPic()
						.getWidth()
						* (Game.getWidth() / 1920f), turns.getCurrentPlayer()
						.getDiscardPile().getDiscardPic().getHeight()
						* (Game.getHeight() / 1080f));
		font.drawMultiLine(batch, turns.getCurrentPlayer().getDiscardPile()
				.getDiscardedCharText(), turns.getCurrentPlayer()
				.getDiscardPile().getX() + 30, turns.getCurrentPlayer()
				.getDiscardPile().getY()
				+ 200 * (Game.getHeight() / 1080f), turns.getCurrentPlayer()
				.getDiscardPile().getDiscardPic().getWidth()
				* (Game.getWidth() / 1920f) - 65, BitmapFont.HAlignment.CENTER);
		batch.setColor(Color.WHITE);

		font.drawMultiLine(
				batch,
				"Game Time: " + turns.getGameTime(),
				preview.getX() - preview.getCardPic().getWidth()
						* (Game.getWidth() / 1920f), preview.getY()+ preview.getCardPic().getWidth()
						* (Game.getHeight() / 1920f),
				preview.getCardPic().getWidth() * (Game.getWidth() / 1920f)
						- 25 * (Game.getWidth() / 1920f),
				BitmapFont.HAlignment.CENTER);

		// RENDER GAME EVENTS
		batch.draw(gameEvents.getBackgroundPic(), gameEvents.getX(), gameEvents
				.getY(),
				gameEvents.getBackgroundPic().getWidth()
						* (Game.getWidth() / 1920f), gameEvents
						.getBackgroundPic().getHeight()
						* (Game.getHeight() / 1080f));
		font.setColor(Color.WHITE);
		font.drawWrapped(batch, "Game Events",
				gameEvents.getX() + 20 * (Game.getWidth() / 1920f),
				gameEvents.getY() + 400 * (Game.getHeight() / 1080f),
				gameEvents.getBackgroundPic().getWidth()
						* (Game.getWidth() / 1920f) - 35,
				BitmapFont.HAlignment.CENTER);
		font.drawWrapped(batch, gameEvents.getEvents(), gameEvents.getX() + 20
				* (Game.getWidth() / 1920f),
				gameEvents.getY() + 15 * (Game.getHeight() / 1080f) + 35
						* gameEvents.displayedEvents(), gameEvents
						.getBackgroundPic().getWidth()
						* (Game.getWidth() / 1920f) - 35,
				BitmapFont.HAlignment.LEFT);

		// RENDER GAME EVENTS
		batch.draw((Texture) manager.get(AssetsManager.CHARGUARD),
				gameEvents.getX(), gameEvents.getY() + 465 * Game.getHeight()
						/ 1080f, 63, 72);
		font.draw(batch, "Player 1: " + turns.getPlayer(0).getName(),
				gameEvents.getX() + 70,
				gameEvents.getY() + 525 * Game.getHeight() / 1080f);
		font.draw(batch, "Green Team", gameEvents.getX() + 70,
				gameEvents.getY() + 360);
		batch.draw((Texture) manager.get(AssetsManager.CHARGUARDRED),
				gameEvents.getX() + 235, gameEvents.getY() + 330, 63, 72);
		font.draw(batch, "Player 2: " + turns.getPlayer(1).getName(),
				gameEvents.getX() + 305, gameEvents.getY() + 380);
		font.draw(batch, "Red Team", gameEvents.getX() + 305,
				gameEvents.getY() + 360);

		font.draw(batch, "It is currently "
				+ turns.getCurrentPlayer().getName() + "\'s turn.",
				gameEvents.getX() + 130, gameEvents.getY() + 325);

		// RENDER END TURN BUTTON
		// batch.draw(btnEndTurn.getButtonPic(), btnEndTurn.getX(),
		// btnEndTurn.getY(),
		// btnEndTurn.getButtonPic().getWidth()*(Game.getWidth()/1920f),
		// btnEndTurn.getButtonPic().getHeight()*(Game.getHeight()/1080f));
		// if(btnEndTurn.hit(Gdx.input.getX(),Gdx.input.getY()) &&
		// Gdx.input.justTouched()) {
		// turns.switchPlayer(turns.getCurrentPlayer());
		// }
		
		//RENDER FIELD
		int i = 0;
		for(Texture t : turns.getCurrentPlayer().getField().getReserveSpaces()) {
			if(i<3) {
				batch.draw(t, turns.getCurrentPlayer().getField().getX(), turns.getCurrentPlayer().getField().getY()+i*t.getHeight()/2+i*5 * (Game.getHeight() / 1080f), t.getWidth()/2 * (Game.getWidth() / 1920f), t.getHeight()/2 * (Game.getHeight() / 1080f));
			} else {
				batch.draw(t, turns.getCurrentPlayer().getField().getX()+t.getWidth()/2+5 * (Game.getHeight() / 1080f), turns.getCurrentPlayer().getField().getY()+(i-3)*t.getHeight()/2+(i-3)*5 * (Game.getHeight() / 1080f), t.getWidth()/2 * (Game.getWidth() / 1920f), t.getHeight()/2 * (Game.getHeight() / 1080f));
			}
			
			if(i < 6-turns.getCurrentPlayer().getField().getEmptySpaces()) {
				ArrayList<Card> reserves = turns.getCurrentPlayer().getField().getReserveCharacters();
				font.setColor(Color.BLACK);
				Character c = reserves.get(i).getRepresentsChar();
				font.drawMultiLine(batch, c.getName() + "\nHP: " + c.getHP() + "/" + c.getMaxHP(),turns.getCurrentPlayer().getField().getX()+18,turns.getCurrentPlayer().getField().getY()+i*t.getHeight()/2+i*5 * (Game.getHeight() / 1080f)+95, t.getWidth()/2 * (Game.getWidth() / 1920f) - 36,BitmapFont.HAlignment.CENTER);
			}
			i++;
		}
		font.setColor(Color.WHITE);
		
		//RENDER BUTTONS
		for (Button b : buttons) {
			b.hit(Gdx.input.getX(), Gdx.input.getY());
			batch.draw(b.getButtonPic(), b.getX(), b.getY(), b.getButtonPic()
					.getWidth() * (Game.getWidth() / 1920f), b.getButtonPic()
					.getHeight() * (Game.getHeight() / 1080f));
			font.draw(batch, b.getText(), b.getX() + 20,
					b.getY() + 32 * (Game.getHeight() / 1080f));
		}

		if (endGameButton.getState() == ButtonState.RELEASED) {
			Timer.getInstance().end();
			main.setScreen(new MainMenuScreen(main));
		}

		if (endTurnButton.getState() == ButtonState.RELEASED) {
			turns.switchPlayer(turns.getCurrentPlayer());
		}
		
		if(withdrawUnitButton.getState() == ButtonState.RELEASED) {
			for(Character c : turns.getCurrentPlayer().getCharacters()) {
				if(c.getSelected()) {
					c.withdraw();
					Vector2 coords = board.getCharacterCoords(c);
					Vector2[] movePositions = board.getValidCoords(coords);
					for(Vector2 v : movePositions) {
						if(v!=null) {
							c.setSelected(false);
							board.setNoCharacterSel();
							board.getBoard().get(v).setSelected(false);
						}
					}
					Board.getCharacters().values().remove(c);
					break;
				}
			}
		}

		// UPDATE THE BOARD
		board.update();
		if(board.getGameEnded()) {
			main.setScreen(new EndGameScreen(board.getWinner(),main));
		}
		else if(turns.getGameEnded()) {
			main.setScreen(new EndGameScreen(turns.getOppositionPlayer().getName(),main));
		}

		// UPDATE TURN CONTROLLER
		turns.update();

		// CHARACTER LOGIC (MOVE TO TURN CONTROLLER)
		/*
		 * if(guard1.hit(Gdx.input.getX(),Gdx.input.getY())) {
		 * batch.draw(guard1.getCharPic(), guard1.getX(), guard1.getY(),
		 * guard1.getCharPic().getWidth(), guard1.getCharPic().getHeight()); }
		 * if(guard2.hit(Gdx.input.getX(),Gdx.input.getY())) {
		 * batch.draw(guard2.getCharPic(), guard2.getX(), guard2.getY(),
		 * guard2.getCharPic().getWidth(), guard2.getCharPic().getHeight()); }
		 */

		// END RENDER
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		// turns.getPlayer("Rupert").getDeck().resize(width, height);
		for (Player p : turns.getPlayers()) {
			p.getDeck().resize(width, height);
			p.getHand().resize(width, height);
			p.getDiscardPile().resize(width, height);
		}
		// turns.getCurrentPlayer().getDeck().resize(width, height);
		// turns.getCurrentPlayer().getDiscardPile().resize(width,height);
		// btnEndTurn.resize(width, height);
		board.resize(width, height);
		preview.resize(width, height);
		gameEvents.resize(width, height);

		float x = 20;
		for (Button b : buttons) {
			b.setX((Game.getWidth() - x) - (307 * Game.getWidth() / 1920f));
			b.setY((turns.getCurrentPlayer().getDeck().getDeckTexture()
					.getHeight() + 10)
					* Game.getHeight() / 1080f);
			// y = y - ((b.getButtonPic().getHeight() + 25) *
			// Game.getHeight()/1080f);
			x += 317 * width / 1920f;
			b.resize(width, height);
		}
		withdrawUnitButton.setX(endGameButton.getX());
		withdrawUnitButton.setY(endGameButton.getY()+60*height/1080f);
		withdrawUnitButton.resize(width, height);
		/*
		 * currentWidth = width; currentHeight = height;
		 * System.out.println(currentWidth + "x" + currentHeight);
		 * System.out.println
		 * (turns.getCurrentPlayer().getDeck().getDeckTexture()
		 * .getWidth()*(currentWidth/1920));
		 * System.out.println(turns.getCurrentPlayer
		 * ().getDeck().getDeckTexture().getHeight()*(currentHeight/1080));
		 * float scale = currentWidth/1920f; System.out.println(scale);
		 */
	}

	@Override
	public void show() {
		batch = new SpriteBatch();
		font = new BitmapFont();
		if (gameMode == 2) {
			player1 = Profile.getInstance().getPlayerInstance();
			try {
				new Socket("127.0.0.1", 8000);
				// BufferedReader confirmGame = new BufferedReader(new
				// InputStreamReader(serverConn.getInputStream()));
				// String response = confirmGame.readLine();
				// System.out.println("Server Response: " + response);
				// while(response == null) {
				// response = confirmGame.readLine();
				// System.out.println("Server Response: " + response);
				// }
				// ObjectOutputStream outs = new
				// ObjectOutputStream(serverConn.getOutputStream());
				// outs.writeObject(player1);
				// ObjectInputStream ins = new
				// ObjectInputStream(serverConn.getInputStream());
				// Object remotePlayer = ins.readObject();
				// Player player2 = (Player) remotePlayer;
				player2 = new Player();
				turns = new TurnController(player1, player2);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			player1 = Profile.getInstance().getPlayerInstance();
			player2 = new Player();
			turns = new TurnController(player1, player2);
		}

		// POPULATE WITH DEMO DATA
		for (int i = 0; i < 10; i++) {
			// potionCard = new Card("Potion",
			// manager.get(AssetsManager.CARDPOTION, Texture.class));
			Card potionCard = new Card();
			potionCard.setName("Potion");
			potionCard.setCardPic((Texture) manager
					.get(AssetsManager.CARDPOTION));
			// potionCard.setOwner(player1);
			player1.getDeck().addCard(potionCard);
		}
		for (int i = 0; i < 10; i++) {
			// potionCard = new Card("Potion",
			// manager.get(AssetsManager.CARDPOTION, Texture.class));
			Card potionCard = new Card();
			potionCard.setName("Potion");
			potionCard.setCardPic((Texture) manager
					.get(AssetsManager.CARDPOTION));
			// potionCard.setOwner(player2);
			player2.getDeck().addCard(potionCard);
		}

		player1.setName("Rupert");
		player1.setTeamColour("green");
		player2.setName("Adam");
		player2.setTeamColour("red");

		if (gameMode == 0) {
			player1.setAIControlled(false);
			player2.setAIControlled(false);
		} else if (gameMode == 1) {
			player1.setAIControlled(false);
			player2.setAIControlled(true);
		}

		board = new Board(200, Game.getHeight() - 80);
		board.setupBoard(11);
		Vector2[] pos = new Vector2[14];
		// pos[0] = new Vector2(0,0);
		// pos[1] = new Vector2(0,3);
		// pos[2] = new Vector2(0,5);
		// pos[3] = new Vector2(2,0);
		// pos[4] = new Vector2(2,3);
		// pos[5] = new Vector2(4,8);
//		pos[0] = new Vector2(0, 0);
//		pos[1] = new Vector2(0, 1);
//		pos[2] = new Vector2(0, 2);
//		pos[3] = new Vector2(0, 3);
//		pos[4] = new Vector2(0, 4);
//		pos[5] = new Vector2(0, 5);
		pos[0] = new Vector2(0, 2);
		pos[1] = new Vector2(1, 6);
		pos[2] = new Vector2(2, 0);
		pos[3] = new Vector2(6, 0);
		pos[4] = new Vector2(6, 9);
		pos[5] = new Vector2(10, 1);
		pos[6] = new Vector2(10, 5);

		for (int i = 0; i <= 6; i++) {
			// hexs[i] = new Character(200+(i*63),Game.getHeight()-100,"Guard "
			// + (i+1),manager.get(AssetsManager.CHARGUARD,Texture.class), 1, 0,
			// 0, 0, 0, 0, 0);
			chars[i] = new Character((int) board.getPosition(pos[i]).x,
					(int) board.getPosition(pos[i]).y, "Guard " + (i + 1),
					manager.get(AssetsManager.CHARGUARD, Texture.class), 1, 0,
					0, 0, 0, 0, 50);
			chars[i].setOwner(player1);
			chars[i].setCharacterType(CharacterType.GUARD);
			board.addCharacter(pos[i], chars[i]);
		}
		//
		// pos[6] = new Vector2(10,0);
		// pos[6] = new Vector2(10,0);
		// pos[7] = new Vector2(9,3);
		// pos[8] = new Vector2(8,5);
		// pos[9] = new Vector2(7,0);
		// pos[10] = new Vector2(7,3);
		// pos[11] = new Vector2(5,8);
		
		pos[7] = new Vector2(0, 0);
		pos[8] = new Vector2(0, 4);
		pos[9] = new Vector2(4, 0);
		pos[10] = new Vector2(4, 9);
		pos[11] = new Vector2(8, 7);
		pos[12] = new Vector2(9, 0);
		pos[13] = new Vector2(10, 3);

		for (int i = 7; i <= 13; i++) {
			// hexs[i] = new Character(200+(i*63),Game.getHeight()-100,"Guard "
			// + (i+1),manager.get(AssetsManager.CHARGUARD,Texture.class), 1, 0,
			// 0, 0, 0, 0, 0);
			chars[i] = new Character((int) board.getPosition(pos[i]).x,
					(int) board.getPosition(pos[i]).y, "Guard " + (i + 1),
					manager.get(AssetsManager.CHARGUARDRED, Texture.class), 1,
					0, 0, 0, 0, 0, 50);
			chars[i].setOwner(player2);
			chars[i].setCharacterType(CharacterType.GUARD);
			board.addCharacter(pos[i], chars[i]);
		}

		preview = Preview.getInstance();
		gameEvents = GameEvents.getInstance();
		
		endGameButton = new Button("End Game", Game.getWidth()
				- (644 * Game.getWidth() / 1920f),
				434 * Game.getHeight() / 1080f);
		endTurnButton = new Button("End Turn", Game.getWidth()
				- (307 * Game.getWidth() / 1920f),
				434 * Game.getHeight() / 1080f);
		withdrawUnitButton = new Button("Withdraw",endTurnButton.getX(),endTurnButton.getY() + 25);
		buttons.add(endGameButton);
		buttons.add(endTurnButton);
		Music background = manager.get("data/music_background.ogg");
		// background.play();
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