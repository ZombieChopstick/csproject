package com.ruperthodgkins.csproject;

import java.util.HashMap;
import java.util.Map.Entry;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Board {
	private static HashMap<Vector2,Character> characters;
	private HashMap<Vector2,BoardHex> board;
	private int x;
	private int y;
	private AssetManager manager = AssetsManager.getInstance();
	private Preview preview = Preview.getInstance();
	private boolean characterSelected = false;
	private Character characterSel = null;
	private static boolean characterMoved = false;
	private static boolean characterAttacked = false;
	private boolean mouseHeld = false;
	private static boolean canAttackCharacter = false;
	private static Player controller = null;
	private static Player opponent = null;
	private static boolean canMoveCharacter = false;
	private boolean centreHolding = false;
	private Player playerCurrentlyHolding = null;
	private boolean gameEnded = false;
	private Vector2[] centreCoords = {new Vector2(4,4), new Vector2(4,5), new Vector2(5,4), new Vector2(5,5), new Vector2(5,6), new Vector2(6,4), new Vector2(6,5)};
	
	public Board(int x, int y) {
		characters = new HashMap<Vector2,Character>();
		board = new HashMap<Vector2,BoardHex>();
		this.x = x;
		this.y = y;
	}
	
	public boolean getGameEnded() {
		return gameEnded;
	}
	
	public void setupBoard(int rows) {
		int rowLength = 6;
		for(int i = 0; i<(rows/2)+1; i++) {
			for(int ii = 0; ii<rowLength; ii++) {
				Vector2 coords = new Vector2(i,ii);
				board.put(coords, new BoardHex(coords,x+(ii*(63*Game.getWidth() / 1920f))-(i*(31.5f*Game.getWidth()/1920f)),y-(i*(54*Game.getWidth()/1920f))));
			}
			rowLength++;
		}
		rowLength--;
		for(int i = (rows/2); i<rows; i++) {
			for(int ii = 0; ii<rowLength; ii++) {
				Vector2 coords = new Vector2(i,ii);
				board.put(coords, new BoardHex(coords,x+(ii*(63*Game.getWidth() / 1920f))+((i-10)*31.5f*Game.getWidth()/1920f),y-(i*(54*Game.getWidth()/1920f))));
			}
			rowLength--;
		}
	}
	
	public Vector2 getCharacterCoords(Character c) {
		for (Entry<Vector2, Character> entry : characters.entrySet()) {
	        if (c.equals(entry.getValue())) {
	            return entry.getKey();
	        }
	    }
	    return null;
	}
	
	public Vector2[] getValidCoords(Vector2 startingCoords) {
		Vector2[] coords = new Vector2[6];
		Vector2 left = new Vector2(startingCoords.x,startingCoords.y-1);
		Vector2 right = new Vector2(startingCoords.x,startingCoords.y+1);
		Vector2 leftdown;
		Vector2 leftup;
		Vector2 rightdown;
		Vector2 rightup;
		
		if(startingCoords.x < 5) {
			leftdown = new Vector2(startingCoords.x+1,startingCoords.y);
			leftup = new Vector2(startingCoords.x-1,startingCoords.y-1);
			rightdown = new Vector2(startingCoords.x+1,startingCoords.y+1);
			rightup = new Vector2(startingCoords.x-1,startingCoords.y);
		}
		else if(startingCoords.x > 5) {
			leftdown = new Vector2(startingCoords.x+1,startingCoords.y-1);
			leftup = new Vector2(startingCoords.x-1,startingCoords.y);
			rightdown = new Vector2(startingCoords.x+1,startingCoords.y);
			rightup = new Vector2(startingCoords.x-1,startingCoords.y+1);
		}
		else {
			leftdown = new Vector2(startingCoords.x+1,startingCoords.y-1);
			leftup = new Vector2(startingCoords.x-1,startingCoords.y-1);
			rightdown = new Vector2(startingCoords.x+1,startingCoords.y);
			rightup = new Vector2(startingCoords.x-1,startingCoords.y);
		}
		
		if(board.containsKey(left)) { coords[0] = left; }
		if(board.containsKey(right)) { coords[1] = right; }
		if(board.containsKey(leftdown)) { coords[2] = leftdown; }
		if(board.containsKey(leftup)) { coords[3] = leftup; }
		if(board.containsKey(rightdown)) { coords[4] = rightdown; }
		if(board.containsKey(rightup)) { coords[5] = rightup; }
		
		return coords;
	}
	
	public Vector2[] getTargetsInRange(Vector2 startingCoords,int range) {
		Vector2[] targets = new Vector2[6];
		Vector2 left = new Vector2(startingCoords.x,startingCoords.y-range);
		Vector2 right = new Vector2(startingCoords.x,startingCoords.y+range);
		Vector2 leftdown;
		Vector2 leftup;
		Vector2 rightdown;
		Vector2 rightup;
		
		if(startingCoords.x < 5) {
			leftdown = new Vector2(startingCoords.x+range,startingCoords.y);
			leftup = new Vector2(startingCoords.x-range,startingCoords.y-range);
			rightdown = new Vector2(startingCoords.x+range,startingCoords.y+range);
			rightup = new Vector2(startingCoords.x-range,startingCoords.y);
		}
		else if(startingCoords.x > 5) {
			leftdown = new Vector2(startingCoords.x+range,startingCoords.y-range);
			leftup = new Vector2(startingCoords.x-range,startingCoords.y);
			rightdown = new Vector2(startingCoords.x+range,startingCoords.y);
			rightup = new Vector2(startingCoords.x-range,startingCoords.y+range);
		}
		else {
			leftdown = new Vector2(startingCoords.x+range,startingCoords.y-range);
			leftup = new Vector2(startingCoords.x-range,startingCoords.y-range);
			rightdown = new Vector2(startingCoords.x+range,startingCoords.y);
			rightup = new Vector2(startingCoords.x-range,startingCoords.y);
		}
		
		if(characters.containsKey(left) && characters.get(left).getOwner() != controller) { targets[0] = left; }
		if(characters.containsKey(right) && characters.get(right).getOwner() != controller) { targets[1] = right; }
		if(characters.containsKey(leftdown) && characters.get(leftdown).getOwner() != controller) { targets[2] = leftdown; }
		if(characters.containsKey(leftup) && characters.get(leftup).getOwner() != controller) { targets[3] = leftup; }
		if(characters.containsKey(rightdown) && characters.get(rightdown).getOwner() != controller) { targets[4] = rightdown; }
		if(characters.containsKey(rightup) && characters.get(rightup).getOwner() != controller) { targets[5] = rightup; }
		
		return targets;
	}
	
	public static boolean getCharacterMoved() {
		return characterMoved;
	}
	
	public static boolean getCharacterAttacked() {
		return characterAttacked;
	}
	
	public static void setCharacterMoved(boolean moved) {
		characterMoved = moved;
	}
	
	public static void setCharacterAttacked(boolean attacked) {
		characterAttacked = attacked;
	}
	
	public static void setControllingPlayer(Player p) {
		controller = p;
	}
	
	public static void setOpposingPlayer(Player p) {
		opponent = p;
	}
	
	public static boolean getCanMoveCharacter() {
		return canMoveCharacter;
	}
	
	public static void setCanMoveCharacter(boolean canMove) {
		canMoveCharacter = canMove;
	}
	
	public static void setCanAttackCharacter(boolean canAttack) {
		canAttackCharacter = canAttack;
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
	
	public void removeCharacter(Character c) {
		
	}
	
	public static HashMap<Vector2,Character> getCharacters() {
		return characters;
	}
	
	public boolean mouseButtonReleased() {
		if(mouseHeld == false) {
			mouseHeld = Gdx.input.justTouched();
		}
		else {
			if(Gdx.input.isTouched() == false) {
				mouseHeld = false;
				return true;
			}
		}
		return false;
	}
	
	public void update() {
		/*if(Gdx.input.isTouched()) System.out.println("Is Touched " + Gdx.input.isTouched());
		if(Gdx.input.justTouched()) System.out.println("Just Touched " + Gdx.input.justTouched());
		if(Gdx.input.isButtonPressed(Buttons.LEFT)) System.out.println("Left Mouse Button Pressed");*/
		
		for(Character c : characters.values()) {
			if(c.hit(Gdx.input.getX(),Gdx.input.getY()) && canMoveCharacter) {
				Vector2[] movePositions = null;
				if(Gdx.input.isTouched() && controller == c.getOwner() && characterSel == c) {
					System.out.println("Deselecting Character");
					c.setSelected(false);
					characterSel = null;
					break;
				}
				if(mouseButtonReleased() && controller == c.getOwner() && characterSel == null) {
					System.out.println("Selecting Character");
					Vector2 coords = getCharacterCoords(c);
					movePositions = getValidCoords(coords);
					for(Vector2 v : movePositions) {
						if(v!=null) {
							c.setSelected(true);
							characterSel = c;
							board.get(v).setSelected(true);
						}
					}
				}				
				//COLLAPSED CODE MOVED TO BOTTOM OF CLASS
				if(c.getOwner().getTeamColour().equals("green")) {
					preview.setCardPic((Texture) manager.get(AssetsManager.CARDCHARGUARD),c);
					break;
				}
				else if(c.getOwner().getTeamColour().equals("red")) {
					preview.setCardPic((Texture) manager.get(AssetsManager.CARDCHARGUARDRED),c);
					break;
				}
				//break;
			}
			else if(c.hit(Gdx.input.getX(),Gdx.input.getY()) && canAttackCharacter) {
				Vector2[] attackPositions = null;
				if(Gdx.input.isTouched() && controller == c.getOwner() && characterSel == c) {
					System.out.println("Deselecting Character");
					Vector2 coords = getCharacterCoords(c);
					c.setSelected(false);
					attackPositions = getTargetsInRange(coords,1);
					//System.out.println(attackPositions);
					for(Vector2 v : attackPositions) {
						if(v!=null) {
							characters.get(v).setSelected(false);
						}
					}
					characterSel = null;
					attackPositions = null;
					break;
				}
				else if(mouseButtonReleased() && controller == c.getOwner() && characterSel == null) {
					System.out.println("Selecting Character");
					Vector2 coords = getCharacterCoords(c);
					attackPositions = getTargetsInRange(coords,1);
					//System.out.println(attackPositions);
					c.setSelected(true);
					characterSel = c;
					for(Vector2 v : attackPositions) {
						System.out.println(v);
						if(v!=null) {
							characters.get(v).setSelected(true);
						}
					}
				}
				else if(Gdx.input.justTouched() && controller != c.getOwner() && characterSel!=null && c.getSelected()) {
					
					GameEvents.getInstance().addEvent(Timer.getInstance().getGameTime() + ": " + controller.getName() + "\'s " + characterSel.getName() + " attacked " + opponent.getName() + "\'s " + c.getName());
					characterSel.attackCharacter(c);
					if(c.getHP()<=0) {
						c.die();
						characters.values().remove(c);
						GameEvents.getInstance().addEvent(Timer.getInstance().getGameTime() + ": " + controller.getName()  + "\'s " + characterSel.getName() + " killed " + opponent.getName() + "\'s " + c.getName());
					}
					Vector2 coords = getCharacterCoords(characterSel);
					attackPositions = getTargetsInRange(coords,1);
					for(Vector2 v : attackPositions) {
						if(v!=null) {
							characters.get(v).setSelected(false);
						}
					}
					characterSel.setSelected(false);
					characterSel = null;
					c.setSelected(false);
					canAttackCharacter = false;
					characterAttacked = true;
				}
				//COLLAPSED CODE MOVED TO BOTTOM OF CLASS
				if(c.getOwner().getTeamColour().equals("green")) {
					preview.setCardPic((Texture) manager.get(AssetsManager.CARDCHARGUARD),c);
					break;
				}
				else if(c.getOwner().getTeamColour().equals("red")) {
					preview.setCardPic((Texture) manager.get(AssetsManager.CARDCHARGUARDRED),c);
					break;
				}
				
				
			}
			else {
				preview.setCardPic(null, null);
				preview.setCharacterInformation("");
			}
		}
		
		for(BoardHex hex : board.values()) {		
			if(hex.hit(Gdx.input.getX(),Gdx.input.getY()) && !hex.isSelected() && characterSel == null) {
				hex.setSelected(true);
			}
			else if(!hex.hit(Gdx.input.getX(),Gdx.input.getY()) && characterSel == null){
				hex.setSelected(false);
			}
			
			if(hex.hit(Gdx.input.getX(),Gdx.input.getY()) && characterSel != null && hex.isSelected() && Gdx.input.isTouched()) {
				if(canMoveCharacter && !canAttackCharacter) {
					GameEvents.getInstance().addEvent(Timer.getInstance().getGameTime() + ": " + controller.getName() + " moved " + characterSel.getName());
					Vector2 destPosition = getPosition(hex.getCoordinates());
					characterSel.setPosition(destPosition.x, destPosition.y);
					characterSel.setSelected(false);
					characters.get(getCharacterCoords(characterSel)).setSelected(false);
					characters.values().remove(characterSel);
					characters.put(hex.getCoordinates(), characterSel);
					characterSel = null;
					characterMoved = true;
					canMoveCharacter = false;
				}
			}
		}
		
		Character centreCharacter = characters.get(centreCoords[4]);
		if(centreCharacter != null) {
			playerCurrentlyHolding = centreCharacter.getOwner();
		}
		
		int positionsHeld = 0;
		for(Vector2 v : centreCoords) {
			Character ch = characters.get(v);
			if(ch != null && ch.getOwner() == playerCurrentlyHolding) {
				//resolve whether win/lose condition
				positionsHeld++;
			}
		}
		
		if(positionsHeld == 7) { 
			System.out.println(playerCurrentlyHolding.getName() + " wins the game."); 
			gameEnded = true;
		}
	}
	
	public String getWinner() {
		return playerCurrentlyHolding.getName();
	}
	
	public void resize(int width, int height) {
		System.out.println(width + "," + height);
		x = (int) (width/2-400 * Game.getWidth() / 1920f);
		y = (int) (height - 80 / (Game.getHeight() / 1080f * 2));
		setupBoard(11);
		Vector2[] pos = new Vector2[characters.size()];
		int i = 0;
		for(Vector2 v: characters.keySet()) {
			pos[i] = v;
			i++;
		}
		for(BoardHex hex : board.values()) {
			hex.resize(width, height);
		}
		i = 0;
		for(Character c : characters.values()) {
			c.setPosition((int)getPosition(pos[i]).x,(int)getPosition(pos[i]).y);
			i++;
		}
		
	}
}