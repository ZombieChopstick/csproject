package com.ruperthodgkins.csproject;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;

public class TurnController {
	private ArrayList<Player> players = new ArrayList<Player>();
	private Player currentPlayer;
	private boolean drawnCard = false;
	private boolean movedCharacterInBattle = false;
	private int fieldCharacterSpaces = 0;
	private int cardLimit = 1;
	private int numberOfCardsPlayed = 0;
	private boolean attackedOpponent = false;
	private Card cardToRemove = null;
	private Timer gameTimer;
	private boolean mouseHeld = false;
	private GameEvents gameEvents = GameEvents.getInstance();
	private boolean eventsAdded = false;

	public TurnController(Player p1, Player p2) {
		players.add(p1);
		players.add(p2);
		currentPlayer = p1;
		gameTimer = Timer.getInstance();
		gameTimer.start();
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public Player getOppositionPlayer() {
		for (Player p : getPlayers()) {
			if (p != currentPlayer) {
				return p;
			}
		}
		return null;
	}

	public Player getPlayer(String name) {
		for (Player p : players) {
			if (p.getName().equals(name)) {
				return p;
			}
		}
		return null;
	}

	public Player getPlayer(int number) {
		return players.get(number);
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public void setupNewGame() {
		// CREATE BOARD
		// SETUP BOARD
		// LOAD PLAYERS DATA
	}

	public String getGameTime() {
		return gameTimer.getGameTime();
	}

	public void removePlayerFromGame(Player p) {
		players.remove(p);
	}

	public void switchPlayer(Player current) {
		// for(Character c : current.getCharacters()) {
		// c.setSelected(false);
		// }
		eventsAdded = false;
		if (eventsAdded == false)
			gameEvents.addEvent(gameTimer.getGameTime() + ": " + "End of "
					+ currentPlayer.getName() + "\'s turn\n------------------------------------");
		eventsAdded = true;
		try {
			System.out.println("switching players");
			Thread.sleep(250);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (players.indexOf(current) == 0) {
			currentPlayer = players.get(1);
		} else if (players.indexOf(current) == 1) {
			currentPlayer = players.get(0);
		}
		drawnCard = false;
		numberOfCardsPlayed = 0;
		movedCharacterInBattle = false;
		attackedOpponent = false;
		Board.setCharacterMoved(false);
		Board.setCanMoveCharacter(false);
		Board.setCharacterAttacked(false);
		Board.setCanAttackCharacter(false);
		eventsAdded = false;
	}

	public boolean mouseButtonReleased() {
		if (mouseHeld == false) {
			mouseHeld = Gdx.input.justTouched();
		} else {
			if (Gdx.input.isTouched() == false) {
				mouseHeld = false;
				return true;
			}
		}
		return false;
	}

	public void update() {
		// System.out.println(currentPlayer.getName() + " Phases:\tDrawn Card: "
		// + drawnCard +"\tMoved Piece: " + movedCharacterInBattle +
		// "\tAttacked Piece: " + attackedOpponent);
		Player p = currentPlayer;
		Board.setControllingPlayer(p);
		Board.setOpposingPlayer(getOppositionPlayer());

		if (currentPlayer.getCharacters().size() == 0) {
			System.out.println(currentPlayer.getName() + "loses");
			System.out.println(getOppositionPlayer().getName() + "wins");
			System.exit(0);
		}

		if (!p.getAIControlled()) {
			if (p.getMissTurn()) {
				p.setMissTurn(false);
				// break;
			}

			// wait for player to draw card
			if (p.getFirstTurn() && drawnCard == false) {
				if (eventsAdded == false)
					gameEvents.addEvent(gameTimer.getGameTime()
							+ ": Waiting for " + currentPlayer.getName()
							+ " to draw 5 cards from the deck.");
				eventsAdded = true;
				p.startTurn();
				p.getDeck().shuffle();
				if (p.getDeck().hit(Gdx.input.getX(), Gdx.input.getY())
						&& mouseButtonReleased()) {
					eventsAdded = false;
					if (eventsAdded == false)
						gameEvents.addEvent(gameTimer.getGameTime() + ": "
								+ currentPlayer.getName()
								+ " has drawn 5 cards.");
					eventsAdded = true;
					for (int i = 0; i < 5; i++) {
						Card c = p.getDeck().drawCard();
						p.getHand().addCard(c);
					}
					p.setFirstTurn(false);
					drawnCard = true;
					Board.setCanMoveCharacter(true);
				}
			} else if (drawnCard == false) {
				if (eventsAdded == false)
					gameEvents.addEvent(gameTimer.getGameTime()
							+ ": Waiting for " + currentPlayer.getName()
							+ " to draw a card from the deck.");
				eventsAdded = true;
				p.startTurn();
				if (p.getDeck().hit(Gdx.input.getX(), Gdx.input.getY())
						&& mouseButtonReleased()) {
					if (p.getDeck().getCardsRemaining() > 0) {
						eventsAdded = false;
						if (eventsAdded == false)
							gameEvents.addEvent(gameTimer.getGameTime() + ": "
									+ currentPlayer.getName()
									+ " has drawn a card.");
						eventsAdded = true;
						Card c = p.getDeck().drawCard();
						p.getHand().addCard(c);
						drawnCard = true;
						Board.setCanMoveCharacter(true);
					} else {
						eventsAdded = false;
						if (eventsAdded == false)
							gameEvents.addEvent(gameTimer.getGameTime() + ": "
									+ currentPlayer.getName()
									+ " has no cards left in the deck.");
						eventsAdded = true;
						p.getDeck().hide();
						drawnCard = true;
						Board.setCanMoveCharacter(true);
					}
				}
			}

			// wait for player to arrange hand
			p.getHand().update();

			// wait for player to move characters to field
			if (fieldCharacterSpaces > 0) {
				// move characters to field
			}

			// wait for player to apply card to character
			if (numberOfCardsPlayed < cardLimit && drawnCard) {
				for (Character c : p.getCharacters()) {
					if (p.getHand().getHoldingCard() != null) {
						if (c.hit(Gdx.input.getX(), Gdx.input.getY())
								&& c.getOwner() == p) {
							System.out.println("Added 20HP to " + c.getName());
							eventsAdded = false;
							if (eventsAdded == false)
								gameEvents.addEvent(gameTimer.getGameTime()
										+ ": "
										+ currentPlayer.getName()
										+ " applied a "
										+ p.getHand().getHoldingCard()
												.getName() + " to "
										+ c.getName() + ".");
							eventsAdded = true;
							c.increaseHP(20);
							cardToRemove = p.getHand().getHoldingCard();
							p.getDiscardPile().addCard(cardToRemove);
							numberOfCardsPlayed++;
							p.getHand().removeCard(cardToRemove);
						}
					}
				}
			}

			// wait for player to move character
			if (!movedCharacterInBattle) {
				// move character in battle
				if (Board.getCharacterMoved()) {
					// eventsAdded = false;
					// if(eventsAdded==false)
					// gameEvents.addEvent(gameTimer.getGameTime() + ": " +
					// currentPlayer.getName() + " moved a character.");
					// eventsAdded = true;
					movedCharacterInBattle = true;
					numberOfCardsPlayed = cardLimit;
					Board.setCanAttackCharacter(true);
				}
			}

			if (!attackedOpponent) {
				if (Board.getCharacterAttacked()) {
					// eventsAdded = false;
					// if(eventsAdded==false)
					// gameEvents.addEvent(gameTimer.getGameTime() + ": " +
					// currentPlayer.getName() + " attacked a character.");
					// eventsAdded = true;
					attackedOpponent = true;
					Board.setCanAttackCharacter(false);
				}
			}

			if (drawnCard && fieldCharacterSpaces == 0
					&& numberOfCardsPlayed == cardLimit
					&& movedCharacterInBattle && attackedOpponent) {
				p.endTurn();
			}

			// wait for player to attack opposition
			if (p.turnEnded()) {
				switchPlayer(p);
			}
		}
		else {
			//AI TURN
			// wait for player to draw card
			if (p.getFirstTurn() && drawnCard == false) {
				if (eventsAdded == false)
					gameEvents.addEvent(gameTimer.getGameTime()
							+ ": Waiting for " + currentPlayer.getName()
							+ " to draw 5 cards from the deck.");
				eventsAdded = true;
				p.startTurn();
				p.getDeck().shuffle();
//				try {
//					Thread.sleep(5000);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
				eventsAdded = false;
				if (eventsAdded == false)
					gameEvents.addEvent(gameTimer.getGameTime() + ": "
							+ currentPlayer.getName()
							+ " has drawn 5 cards.");
				eventsAdded = true;
				for (int i = 0; i < 5; i++) {
					Card c = p.getDeck().drawCard();
					p.getHand().addCard(c);
				}
				p.setFirstTurn(false);
				drawnCard = true;
				Board.setCanMoveCharacter(true);
			} else if (drawnCard == false) {
				if (eventsAdded == false)
					gameEvents.addEvent(gameTimer.getGameTime()
							+ ": Waiting for " + currentPlayer.getName()
							+ " to draw a card from the deck.");
				eventsAdded = true;
				p.startTurn();

				if (p.getDeck().getCardsRemaining() > 0) {
					eventsAdded = false;
					if (eventsAdded == false)
						gameEvents.addEvent(gameTimer.getGameTime() + ": "
								+ currentPlayer.getName()
								+ " has drawn a card.");
					eventsAdded = true;
					Card c = p.getDeck().drawCard();
					p.getHand().addCard(c);
					drawnCard = true;
					Board.setCanMoveCharacter(true);
				} else {
					eventsAdded = false;
					if (eventsAdded == false)
						gameEvents.addEvent(gameTimer.getGameTime() + ": "
								+ currentPlayer.getName()
								+ " has no cards left in the deck.");
					eventsAdded = true;
					p.getDeck().hide();
					drawnCard = true;
					Board.setCanMoveCharacter(true);
				}
			}
			
			if (!movedCharacterInBattle) {
				// move character in battle
				int numOfCharacters = p.getCharacters().size();
				Random rand = new Random();
				int number = rand.nextInt(numOfCharacters);
				System.out.println(number);
				Character c = p.getCharacters().get(number);
				System.out.println(c.getName());
				movedCharacterInBattle = true;
				numberOfCardsPlayed = cardLimit;
			}	
		}	
	}
}