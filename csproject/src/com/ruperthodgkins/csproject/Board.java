package com.ruperthodgkins.csproject;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Board {
	private HashMap<Vector2,Character> characters;
	private HashMap<Vector2,BoardHex> board;
	private int x;
	private int y;
	private AssetManager manager = AssetsManager.getInstance();
	private Preview preview = Preview.getInstance();
	private boolean characterSelected = false;
	private Character holdingCharacter;
	private boolean rememberLastPosition = false;
	private float lastCharX;
	private float lastCharY;
	private static boolean characterMoved = false;
	
	public Board(int x, int y) {
		characters = new HashMap<Vector2,Character>();
		board = new HashMap<Vector2,BoardHex>();
		this.x = x;
		this.y = y;
	}
	
	public static boolean getCharacterMoved() {
		return characterMoved;
	}
	
	public static void setCharacterMoved(boolean moved) {
		characterMoved = false;
	}
	
	public HashMap<Vector2,BoardHex> getBoard() {
		return board;
	}
	
	public Vector2 getPosition(Vector2 coords) {
		return new Vector2(board.get(coords).getX(),board.get(coords).getY());
	}
	
	public boolean isCharacterSelected() {
		return characterSelected;
	}
	
	public void addCharacter(Vector2 coord, Character c) {
		characters.put(coord, c);
		c.getOwner().getCharacters().add(c);
	}
	
	public void removeCharacter(Vector2 coord) {
		
	}
	
	public HashMap<Vector2,Character> getCharacters() {
		return characters;
	}
	
	public void resize(int width, int height) {
		x = width/2-400;
		y = height - 80;
		setupBoard(11);
		Vector2[] pos = new Vector2[characters.size()];
		int i = 0;
		for(Vector2 v: characters.keySet()) {
			pos[i] = v;
			i++;
		}
		i = 0;
		for(Character c : characters.values()) {
			c.setPosition((int)getPosition(pos[i]).x,(int)getPosition(pos[i]).y);
			i++;
		}
		
	}
	
	public void setupBoard(int rows) {
		int rowLength = 6;
		for(int i = 0; i<(rows/2)+1; i++) {
			for(int ii = 0; ii<rowLength; ii++) {
				Vector2 coords = new Vector2(i,ii);
				board.put(coords, new BoardHex(coords,x+(ii*63)-(i*31.5f),y-(i*54)));
			}
			rowLength++;
		}
		rowLength--;
		for(int i = (rows/2); i<rows; i++) {
			for(int ii = 0; ii<rowLength; ii++) {
				Vector2 coords = new Vector2(i,ii);
				board.put(coords, new BoardHex(coords,x+(ii*63)+((i-10)*31.5f),y-(i*54)));
			}
			rowLength--;
		}
	}
	
	public void update() {
		for(Character c : characters.values()) {
			if(c.hit(Gdx.input.getX(),Gdx.input.getY())) {
				/*if(Gdx.input.justTouched()) {
					c.setSelected(!c.getSelected());
					characterSelected = !characterSelected;
					holdingCharacter = c;
				}*/
				if(Gdx.input.isTouched()) {
					if(holdingCharacter == null) {
						lastCharX = c.getX();
						lastCharY = c.getY();
						rememberLastPosition = true;
						c.setPosition(Gdx.input.getX()-(c.getCharPic().getWidth()*(Game.getWidth()/1920f)/2)-10, Game.getHeight()-Gdx.input.getY()-(c.getCharPic().getHeight()*(Game.getHeight()/1080f)/2)-10);
						holdingCharacter = c;
					}
					else {
						holdingCharacter.setPosition(Gdx.input.getX()-(holdingCharacter.getCharPic().getWidth()*(Game.getWidth()/1920f)/2)-10, Game.getHeight()-Gdx.input.getY()-(holdingCharacter.getCharPic().getHeight()*(Game.getHeight()/1080f)/2)-10);
					}
				}
				else {
					for(BoardHex hex : board.values()) {
						if(holdingCharacter!=null && hex.hit(Gdx.input.getX(),Gdx.input.getY())) {
							System.out.println(hex.getCoordinates());
							holdingCharacter.setPosition(hex.getX(), hex.getY());
							rememberLastPosition = false;
							characters.values().remove(holdingCharacter);
							characters.put(hex.getCoordinates(), holdingCharacter);
							if(hex.getX() != lastCharX && hex.getY() != lastCharY)
								characterMoved  = true;
							break;
						}
					}
					if(rememberLastPosition) {
						c.setPosition(lastCharX, lastCharY);
						rememberLastPosition = false;
					}
					holdingCharacter = null;
				}
				preview.setCardPic((Texture) manager.get(AssetsManager.CARDCHARGUARD),c);
				break;
			}
			else {
				preview.setCardPic(null, null);
				preview.setCharacterInformation("");
			}
		}		
	}
}