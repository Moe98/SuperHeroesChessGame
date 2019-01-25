package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.Point;
import java.lang.reflect.Field;
import java.util.ArrayList;

import model.game.Cell;
import model.game.Direction;
import model.game.Game;
import model.game.Player;
import model.pieces.Piece;
import model.pieces.heroes.ActivatablePowerHero;
import model.pieces.heroes.Armored;
import model.pieces.heroes.Medic;
import model.pieces.heroes.Ranged;
import model.pieces.heroes.Speedster;
import model.pieces.heroes.Super;
import model.pieces.heroes.Tech;
import model.pieces.sidekicks.SideKick;
import model.pieces.sidekicks.SideKickP1;
import model.pieces.sidekicks.SideKickP2;

import org.junit.Test;

import exceptions.InvalidPowerDirectionException;
import exceptions.InvalidPowerUseException;
import exceptions.OccupiedCellException;
import exceptions.UnallowedMovementException;
import exceptions.WrongTurnException;

@SuppressWarnings("unused")
public class TestV3 {

	static final String gamePath = "model.game.Game";
	static final String rangedPath = "model.pieces.heroes.Ranged";
	static final String sideKick1Path = "model.pieces.sidekicks.SideKickP1";
	static final String sidekick2Path = "model.pieces.sidekicks.SideKickP2";
	static final int boardHeight = 7;
	static final int boardWidth = 6;
	static boolean attackCalled;
	static boolean switchTurnsCalled;
	static boolean assemblePiecesCalled;
	static Piece attackedPiece1;
	static Piece attackedPiece2;

	private static boolean attackMethodCalled = false;
	private static boolean switchCalled = false;
	private static boolean winCalled = false;
	private static Piece target = null;

	@Test(timeout = 500)
	public void testRangedUsePowerInvalidDirections() throws Exception {

		testInstanceVariableRangedRange();

		testAttack();

		testUsePowerRangedHelper("R1", "P", new Point(3, 5), Direction.LEFT,
				new Point(3, 4), false, true);
		testUsePowerRangedHelper("R2", "T", new Point(4, 2), Direction.RIGHT,
				new Point(4, 3), false, true);
		testUsePowerRangedHelper("R3", "R", new Point(2, 5), Direction.DOWN,
				new Point(3, 5), false, true);
		testUsePowerRangedHelper("R1", "A", new Point(3, 2),
				Direction.DOWNLEFT, new Point(4, 1), false, true);
		testUsePowerRangedHelper("R2", "M", new Point(1, 4),
				Direction.DOWNRIGHT, new Point(2, 5), false, true);
		testUsePowerRangedHelper("R3", "S", new Point(5, 2), Direction.UPRIGHT,
				new Point(4, 3), false, true);
		testUsePowerRangedHelper("R1", "S", new Point(5, 2), Direction.UPLEFT,
				new Point(4, 1), false, true);

	}

	@Test(timeout = 500)
	public void testRangedUsePowerSameFunctionality() throws Exception {

		testInstanceVariableRangedRange();

		testRangedUsePowerUpOnEnemyInRange();
		testRangedUsePowerUpOnFirstEnemyInRange();
		testRangedUsePowerOnFriendly();
		testRangedUsePowerSwitchesTurn();

	}

	public void testRangedUsePowerUpOnEnemyInRange() throws Exception {

		testUsePowerRangedHelper("R2", "P", new Point(3, 5), Direction.UP,
				new Point(2, 5), false, true);
		testUsePowerRangedHelper("R3", "T", new Point(4, 2), Direction.UP,
				new Point(2, 2), false, true);
		testUsePowerRangedHelper("R4", "R", new Point(5, 5), Direction.UP,
				new Point(3, 5), false, true);
		testUsePowerRangedHelper("R1", "A", new Point(3, 2), Direction.UP,
				new Point(2, 2), false, true);
		testUsePowerRangedHelper("R2", "M", new Point(4, 4), Direction.UP,
				new Point(3, 4), false, true);
		testUsePowerRangedHelper("R3", "S", new Point(3, 2), Direction.UP,
				new Point(0, 2), false, true);

	}

	@Test(timeout = 500)
	public void testRangedUsePowerUpOnEnemyOutOfRange() throws Exception {

		testInstanceVariableRangedRange();

		testAttack();

		testUsePowerRangedHelper("R1", "P", new Point(3, 5), Direction.UP,
				new Point(1, 5), false, true);
		testUsePowerRangedHelper("R2", "T", new Point(4, 2), Direction.UP,
				new Point(1, 2), false, true);
		testUsePowerRangedHelper("R3", "R", new Point(5, 5), Direction.UP,
				new Point(1, 5), false, true);
		testUsePowerRangedHelper("R1", "A", new Point(3, 2), Direction.UP,
				new Point(1, 2), false, true);
		testUsePowerRangedHelper("R2", "M", new Point(4, 4), Direction.UP,
				new Point(1, 4), false, true);
		testUsePowerRangedHelper("R3", "S", new Point(5, 2), Direction.UP,
				new Point(1, 2), false, true);

	}

	public void testRangedUsePowerUpOnFirstEnemyInRange() throws Exception {

		testUsePowerRangedHelper("R2", "PT", new Point(3, 5), Direction.UP,
				new Point(2, 5), false, true);
		testUsePowerRangedHelper("R3", "TR", new Point(4, 2), Direction.UP,
				new Point(3, 2), false, true);
		testUsePowerRangedHelper("R4", "RA", new Point(5, 5), Direction.UP,
				new Point(4, 5), false, true);
		testUsePowerRangedHelper("R3", "AM", new Point(3, 2), Direction.UP,
				new Point(2, 2), false, true);
		testUsePowerRangedHelper("R2", "MS", new Point(4, 4), Direction.UP,
				new Point(3, 4), false, true);
		testUsePowerRangedHelper("R3", "SP", new Point(5, 2), Direction.UP,
				new Point(4, 2), false, true);

	}

	public void testRangedUsePowerOnFriendly() throws Exception {
		testUsePowerRangedHelper("R2", "P", new Point(3, 5), Direction.UP,
				new Point(2, 5), false, false);
		testUsePowerRangedHelper("R3", "T", new Point(4, 2), Direction.UP,
				new Point(2, 2), false, false);
		testUsePowerRangedHelper("R4", "R", new Point(5, 5), Direction.UP,
				new Point(3, 5), false, false);
		testUsePowerRangedHelper("R1", "A", new Point(3, 2), Direction.UP,
				new Point(2, 2), false, false);
		testUsePowerRangedHelper("R2", "M", new Point(4, 4), Direction.UP,
				new Point(3, 4), false, false);
		testUsePowerRangedHelper("R3", "S", new Point(5, 2), Direction.UP,
				new Point(4, 2), false, false);
	}

	@Test(timeout = 500)
	public void testRangedUsePowerOnFriendlyAndEnemy() throws Exception {

		testInstanceVariableRangedRange();

		testAttack();

		testUsePowerRangedHelper("R2", "PT", new Point(3, 5), Direction.UP,
				new Point(2, 5), false, false);
		testUsePowerRangedHelper("R3", "TR", new Point(4, 2), Direction.UP,
				new Point(2, 2), false, false);
		testUsePowerRangedHelper("R4", "RA", new Point(5, 5), Direction.UP,
				new Point(3, 5), false, false);
		testUsePowerRangedHelper("R2", "AM", new Point(3, 2), Direction.UP,
				new Point(2, 2), false, false);
		testUsePowerRangedHelper("R2", "MS", new Point(4, 4), Direction.UP,
				new Point(3, 4), false, false);
		testUsePowerRangedHelper("R3", "SP", new Point(5, 2), Direction.UP,
				new Point(4, 2), false, false);

	}

	public void testRangedUsePowerSwitchesTurn() throws Exception {

		testSwitchTurnHelper("R2", "P", new Point(2, 1), Direction.UP,
				new Point(1, 1), true);
		testSwitchTurnHelper("R3", "S", new Point(2, 2), Direction.UP,
				new Point(0, 2), true);
		testSwitchTurnHelper("R1", "T", new Point(2, 4), Direction.UP,
				new Point(1, 4), true);
		testSwitchTurnHelper("R4", "M", new Point(2, 2), Direction.UP,
				new Point(0, 2), true);

	}

	public void testAttack() throws Exception {

		ArrayList<Integer> checks = new ArrayList<Integer>();
		checks.add(7);
		attackAndCheck("SRMPTKA", "A", true, true, false, createGame(0), checks);

		attackAndCheck("SRMPTKA", "A", false, true, false, createGame(0),
				checks);

		checks = new ArrayList<Integer>();
		checks.add(6);
		attackAndCheck("SRMPTKA", "A", true, true, true, createGame(0), checks);

		attackAndCheck("SRMPTKA", "A", false, true, true, createGame(0), checks);

		checks = new ArrayList<Integer>();
		checks.add(2);
		attackAndCheck("SRMPTKA", "A", true, true, true, createGame(2), checks);

		attackAndCheck("SRMPTKA", "A", false, true, true, createGame(2), checks);

		checks = new ArrayList<Integer>();
		checks.add(1);
		attackAndCheck("SRMPTKA", "SRMPTKA", true, true, false, createGame(2),
				checks);

		attackAndCheck("SRMPTKA", "SRMPTKA", false, true, false, createGame(2),
				checks);

		checks = new ArrayList<Integer>();
		checks.add(1);
		attackAndCheck("SRMPTKA", "SRMPTKA", true, true, false, createGame(2),
				checks);

		attackAndCheck("SRMPTKA", "SRMPTKA", false, true, false, createGame(2),
				checks);

		checks = new ArrayList<Integer>();
		checks.add(9);
		Game g = createGame(0);
		attackAndCheck("SRMPTKA", "K", true, true, false, g, checks);

		attackAndCheck("SRMPTKA", "K", false, true, false, createGame(0),
				checks);

		checks = new ArrayList<Integer>();
		checks.add(9);
		g = createGame(0);
		attackAndCheck("SRMPTKA", "K", true, true, false, g, checks);

		attackAndCheck("SRMPTKA", "K", false, true, false, createGame(0),
				checks);

		checks = new ArrayList<Integer>();
		checks.add(10);
		g = createGame(0);
		attackAndCheck("SRMPTKA", "K", true, true, false, g, checks);

		attackAndCheck("SRMPTKA", "K", false, true, false, createGame(0),
				checks);

		checks = new ArrayList<Integer>();
		checks.add(3);
		attackAndCheck("SRMPTKA", "SRMPTKA", true, true, false, createGame(0),
				checks);

		attackAndCheck("SRMPTKA", "SRMPTKA", false, true, false, createGame(0),
				checks);

		checks = new ArrayList<Integer>();
		checks.add(5);
		attackAndCheck("SRMPTKA", "A", true, true, true, createGame(0), checks);

		attackAndCheck("SRMPTKA", "A", false, true, true, createGame(0), checks);

		checks = new ArrayList<Integer>();
		checks.add(4);
		attackAndCheck("SRMPTKA", "A", true, true, true, createGame(0), checks);

		attackAndCheck("SRMPTKA", "A", false, true, true, createGame(0), checks);

		checks = new ArrayList<Integer>();
		checks.add(8);
		attackAndCheck("SRMPTKA", "SRMPTA", true, true, false, createGame(0),
				checks);

		attackAndCheck("SRMPTKA", "SRMPTA", false, true, false, createGame(0),
				checks);

	}

	public void testInstanceVariableRangedRange() throws NoSuchFieldException,
			SecurityException, ClassNotFoundException {
		testInstanceVariableIsPresent(Class.forName(rangedPath), "range", true);
		testInstanceVariableIsPrivate(Class.forName(rangedPath), "range");
	}

	@Test(timeout = 500)
	public void testSideKickP1MoveAllDirections() throws Exception {

		testUsePowerSideKickHelper("S", 1, new Point(0, 4), Direction.DOWNLEFT,
				true, true, true);
		testUsePowerSideKickHelper("T", 1, new Point(2, 2),
				Direction.DOWNRIGHT, true, true, true);
		testUsePowerSideKickHelper("R", 1, new Point(3, 1), Direction.UP, true,
				true, true);
		testUsePowerSideKickHelper("M", 1, new Point(4, 2), Direction.UPLEFT,
				true, true, true);
		testUsePowerSideKickHelper("K", 1, new Point(0, 4), Direction.UPRIGHT,
				true, true, true);
		testUsePowerSideKickHelper("AT", 1, new Point(2, 5), Direction.LEFT,
				true, true, true);
		testUsePowerSideKickHelper("P", 1, new Point(2, 2), Direction.RIGHT,
				true, true, true);
		testUsePowerSideKickHelper("P", 1, new Point(2, 5), Direction.DOWN,
				true, true, true);

	}

	@Test(timeout = 500)
	public void testSideKickP2MoveAllDirections() throws Exception {

		testUsePowerSideKickHelper("S", 2, new Point(2, 4), Direction.UPLEFT,
				true, true, true);
		testUsePowerSideKickHelper("T", 2, new Point(2, 3), Direction.UPRIGHT,
				true, true, true);
		testUsePowerSideKickHelper("R", 2, new Point(3, 1), Direction.UP, true,
				true, true);
		testUsePowerSideKickHelper("M", 2, new Point(4, 3), Direction.DOWNLEFT,
				true, true, true);
		testUsePowerSideKickHelper("K", 2, new Point(4, 2),
				Direction.DOWNRIGHT, true, true, true);
		testUsePowerSideKickHelper("AT", 2, new Point(2, 5), Direction.LEFT,
				true, true, true);
		testUsePowerSideKickHelper("P", 2, new Point(2, 5), Direction.RIGHT,
				true, true, true);
		testUsePowerSideKickHelper("P", 2, new Point(5, 2), Direction.DOWN,
				true, true, true);
	}

	// .........................................Helpers.............................

	private void testUsePowerSideKickHelper(String powerTargets, int playerNo,
			Point userLoc, Direction powerDir, boolean correctTurn,
			boolean onEnemy, boolean validDirection) throws Exception {
		Game game = createGameForUsePower();
		Player currentPlayer = (playerNo == 1) ? game.getPlayer1() : game
				.getPlayer2();
		game.setCurrentPlayer(currentPlayer);
		attackedPiece1 = null;
		attackCalled = false;
		SideKick s = (playerNo == 1) ? new SideKickP1(game, "Sidekick") {

			@Override
			public void attack(Piece p2) {
				attackCalled = true;
				attackedPiece1 = p2;
				super.attack(p2);
			}

		} : new SideKickP2(game, "Sidekick") {

			@Override
			public void attack(Piece p2) {
				attackCalled = true;
				attackedPiece1 = p2;
				super.attack(p2);
			}

		};
		Point targetLoc = getFinalDestination(userLoc, powerDir, 1, true);
		Piece targetPiece;
		Player targetPlayer;

		if (onEnemy)
			targetPlayer = (game.getPlayer1() == currentPlayer ? game
					.getPlayer2() : game.getPlayer1());
		else
			targetPlayer = (game.getPlayer1() == currentPlayer ? game
					.getPlayer1() : game.getPlayer2());

		targetPiece = createPiece(powerTargets.charAt(0), targetPlayer, game);
		if (targetPiece instanceof Armored && powerTargets.charAt(1) == 'F')
			testVariableArmoredUpValueSetter((Armored) targetPiece, false);
		else {
			if (targetPiece instanceof Armored && powerTargets.charAt(1) == 'T')
				testVariableArmoredUpValueSetter((Armored) targetPiece, true);
		}

		game = clearAndInsert(game, new Object[][] {
				{ userLoc.x, userLoc.y, s },
				{ targetLoc.x, targetLoc.y, targetPiece } });

		if (!correctTurn) {
			try {
				game.setCurrentPlayer(targetPlayer);
				s.move(powerDir);
				fail("Whenever "
						+ s.getClass().getSimpleName()
						+ " tries to move in the wrong turn a WrongTurnException should be thrown.");
			} catch (WrongTurnException e) {

			}
			return;
		} else if (!validDirection) {
			try {
				s.move(powerDir);
				fail("Whenever "
						+ s.getClass().getSimpleName()
						+ " tries to move in a direction which is not allowed an UnallowedMovementException should be thrown.");
			} catch (UnallowedMovementException e) {

			}
			return;
		} else {
			if (!onEnemy) {
				try {
					s.move(powerDir);
					fail("Whenever "
							+ s.getClass().getSimpleName()
							+ " tries to move to a cell occupied by a friendly piece an OccupiedCellException should be thrown.");
				} catch (OccupiedCellException e) {
					return;
				}

			}
			s.move(powerDir);
			Piece newPiece = game.getCellAt(targetLoc.x, targetLoc.y)
					.getPiece();
			assertTrue("attack method should be called whenever "
					+ s.getClass().getSimpleName()
					+ " moves to a cell containing an enemy piece.",
					attackCalled);
			assertEquals(
					"attack method should be called with the correct parameter whenever "
							+ s.getClass().getSimpleName()
							+ " moves to a cell containing an enemy piece.",
					targetPiece, attackedPiece1);

			if (targetPiece instanceof Armored && powerTargets.charAt(1) == 'T') {
				testVariableArmoredUpValue((Armored) targetPiece, false);
				return;
			}

			assertNotEquals(
					"Whenever "
							+ s.getClass().getSimpleName()
							+ " eliminates a hero piece, this sidekick piece is replaced with a new hero piece of the same type of the eliminated hero piece.",
					targetPiece, newPiece);

			String c = "";

			if (!(newPiece instanceof SideKick)) {
				assertEquals(
						"Whenever a sidekick piece eliminates a hero piece, this sidekick piece should be replaced with a new hero piece of the same player of the eliminated hero piece.",
						currentPlayer, game.getCellAt(targetLoc.x, targetLoc.y)
								.getPiece().getOwner());

				switch (powerTargets.charAt(0)) {
				case 'P':
					c = Speedster.class.getSimpleName();
					break;
				case 'S':
					c = Super.class.getSimpleName();
					break;
				case 'T':
					c = Tech.class.getSimpleName();
					break;
				case 'R':
					c = Ranged.class.getSimpleName();
					break;
				case 'A':
					c = Armored.class.getSimpleName();
					break;

				case 'M':
					c = Medic.class.getSimpleName();
					break;

				}
				assertEquals(
						"Whenever a sidekick piece eliminates a hero piece, this sidekick piece should be replaced with a new hero piece of the same type of the eliminated hero piece.",
						c, newPiece.getClass().getSimpleName());

				if (targetPiece instanceof ActivatablePowerHero)
					testVariablePowerUsedValue((ActivatablePowerHero) newPiece,
							false);
				else if (targetPiece instanceof Armored)
					testVariableArmoredUpValue((Armored) newPiece, true);
			}
		}

	}

	private void testVariableArmoredUpValue(Armored r, boolean shouldBeUp)
			throws NoSuchFieldException, SecurityException,
			IllegalArgumentException, IllegalAccessException {
		Field f = Armored.class.getDeclaredField("armorUp");
		f.setAccessible(true);
		assertEquals(
				r.getClass().getSimpleName()
						+ " hero should update the value of the \"armorUp\" variable correctly.",
				shouldBeUp, (boolean) f.get(r));
	}

	private void testVariableArmoredUpValueSetter(Armored r, boolean value)
			throws NoSuchFieldException, SecurityException,
			IllegalArgumentException, IllegalAccessException {
		Field f = Armored.class.getDeclaredField("armorUp");
		f.setAccessible(true);
		f.set(r, value);
	}

	private String getPieceName(char c) {
		String res = "";
		switch (c) {
		case 'P':
			res = "Speedster";
			break;
		case 'S':
			res = "Super";
			break;
		case 'T':
			res = "Tech";
			break;
		case 'R':
			res = "Ranged";
			break;
		case 'A':
			res = "Armored";
			break;
		case 'M':
			res = "Medic";
			break;
		case 'K':
			res = "SideKick";
			break;
		}
		return res;
	}

	private Game createGame(int i) {

		int random = (int) (Math.random() * 40) + 10;
		Player p1 = new Player("Player_" + random);
		random = (int) (Math.random() * 40) + 10;
		Player p2 = new Player("Player_" + random);
		Game g = null;
		if (i == 1) {
			g = new Game(p1, p2) {
				@Override
				public void switchTurns() {
					switchCalled = true;
				}
			};
		} else if (i == 2) {
			g = new Game(p1, p2) {
				@Override
				public boolean checkWinner() {
					winCalled = true;
					return false;
				}
			};
		} else
			g = new Game(p1, p2);
		return g;
	}

	private Piece createPiece(char c, Player p, Game g, boolean attack) {

		Piece r = null;

		switch (c) {
		case 'P': {
			if (!attack)
				r = new Speedster(p, g, "Speedster");
			else
				r = new Speedster(p, g, "Speedster") {
					@Override
					public void attack(Piece p) {
						attackMethodCalled = true;
						target = p;
						super.attack(p);
					}
				};
		}
			break;
		case 'S': {
			if (!attack)
				r = new Super(p, g, "Super");
			else
				r = new Super(p, g, "Super") {
					@Override
					public void attack(Piece p) {
						attackMethodCalled = true;
						target = p;
						super.attack(p);
					}
				};
		}
			break;
		case 'T': {
			if (!attack)
				r = new Tech(p, g, "Tech");
			else
				r = new Tech(p, g, "Tech") {
					@Override
					public void attack(Piece p) {
						attackMethodCalled = true;
						target = p;
						super.attack(p);
					}
				};
		}
			break;
		case 'R': {
			if (!attack)
				r = new Ranged(p, g, "Ranged");
			else
				r = new Ranged(p, g, "Ranged") {
					@Override
					public void attack(Piece p) {
						attackMethodCalled = true;
						target = p;
						super.attack(p);
					}
				};
		}
			break;

		case 'A': {
			if (!attack)
				r = new Armored(p, g, "Armored");
			else
				r = new Armored(p, g, "Armored") {
					@Override
					public void attack(Piece p) {
						attackMethodCalled = true;
						target = p;
						super.attack(p);
					}
				};
		}
			break;

		case 'M': {
			if (!attack)
				r = new Medic(p, g, "Medic");
			else
				r = new Medic(p, g, "Medic") {
					@Override
					public void attack(Piece p) {
						attackMethodCalled = true;
						target = p;
						super.attack(p);
					}
				};
		}
			break;

		case 'K':
			if (p == g.getPlayer1()) {
				if (!attack)
					r = new SideKickP1(g, "SideKickP1");
				else
					r = new SideKickP1(g, "SideKickP1") {
						@Override
						public void attack(Piece p) {
							attackMethodCalled = true;
							target = p;
							super.attack(p);
						}
					};
			}
			if (p == g.getPlayer2()) {
				if (!attack)
					r = new SideKickP2(g, "SideKickP2");
				else
					r = new SideKickP2(g, "SideKickP2") {
						@Override
						public void attack(Piece p) {
							attackMethodCalled = true;
							target = p;
							super.attack(p);
						}
					};
			}
			break;

		case 'N':
			r = null;
			break;

		}

		return (r);

	}

	private ArrayList<Piece> preparePieces(char p1, char p2, Direction d,
			boolean isP1, boolean testWrapping, boolean armorRaised, Game g)
			throws Exception {
		int randomI = 0;
		int randomJ = 0;

		if (!testWrapping) {
			if (d == Direction.UP || d == Direction.UPRIGHT
					|| d == Direction.UPLEFT)
				randomI = ((int) (Math.random() * (g.getBoardHeight() - 1))) + 1;
			else if (d == Direction.DOWN || d == Direction.DOWNRIGHT
					|| d == Direction.DOWNLEFT)
				randomI = (int) (Math.random() * (g.getBoardHeight() - 1));
			else
				randomI = (int) (Math.random() * g.getBoardHeight());
			if (d == Direction.RIGHT || d == Direction.UPRIGHT
					|| d == Direction.DOWNRIGHT)
				randomJ = (int) (Math.random() * (g.getBoardWidth() - 1));
			else if (d == Direction.LEFT || d == Direction.UPLEFT
					|| d == Direction.DOWNLEFT)
				randomJ = ((int) (Math.random() * (g.getBoardWidth() - 1))) + 1;
			else
				randomJ = (int) (Math.random() * g.getBoardWidth());
		} else {
			if (d == Direction.UP)
				randomI = 0;
			else if (d == Direction.DOWN)
				randomI = g.getBoardHeight() - 1;
			else
				randomI = (int) (Math.random() * g.getBoardHeight());
			if (d == Direction.LEFT)
				randomJ = 0;
			else if (d == Direction.RIGHT)
				randomJ = g.getBoardWidth() - 1;
			else {
				if ((d == Direction.UPRIGHT && randomI != 0)
						|| (d == Direction.DOWNRIGHT && randomI != g
								.getBoardHeight() - 1))
					randomJ = g.getBoardWidth() - 1;
				else if ((d == Direction.UPLEFT && randomI != 0)
						|| (d == Direction.DOWNLEFT && randomI != g
								.getBoardHeight() - 1))
					randomJ = 0;
				else
					randomJ = (int) (Math.random() * g.getBoardWidth());

			}

		}
		Piece p = null;
		if (isP1)
			p = createPiece(p1, g.getPlayer1(), g, true);
		else
			p = createPiece(p1, g.getPlayer2(), g, true);
		p.setPosI(randomI);
		p.setPosJ(randomJ);
		Piece t = null;
		if (isP1)
			t = createPiece(p2, g.getPlayer2(), g, false);
		else
			t = createPiece(p2, g.getPlayer1(), g, false);
		if (p2 == 'A' && !armorRaised)
			((Armored) t).setArmorUp(false);
		int i2 = randomI;
		int j2 = randomJ;
		if (d == Direction.UP || d == Direction.UPRIGHT
				|| d == Direction.UPLEFT)
			i2 = i2 - 1;
		else if (d == Direction.DOWN || d == Direction.DOWNRIGHT
				|| d == Direction.DOWNLEFT)
			i2 = i2 + 1;
		if (i2 < 0)
			i2 = g.getBoardHeight() - 1;
		else if (i2 >= g.getBoardHeight())
			i2 = 0;
		t.setPosI(i2);
		if (d == Direction.RIGHT || d == Direction.UPRIGHT
				|| d == Direction.DOWNRIGHT)
			j2 = j2 + 1;
		else if (d == Direction.LEFT || d == Direction.UPLEFT
				|| d == Direction.DOWNLEFT)
			j2 = j2 - 1;
		if (j2 < 0)
			j2 = g.getBoardWidth() - 1;
		else if (j2 >= g.getBoardWidth())
			j2 = 0;
		t.setPosJ(j2);

		clearBoardAndInsert(g, p, randomI, randomJ, t, i2, j2);
		if (isP1)
			g.setCurrentPlayer(g.getPlayer1());
		else
			g.setCurrentPlayer(g.getPlayer2());

		ArrayList<Piece> res = new ArrayList<Piece>();
		res.add(p);
		res.add(t);
		return res;
	}

	private void clearBoardAndInsert(Game g, Piece p, int i1, int j1, Piece t,
			int i2, int j2) throws Exception {
		Field f = Class.forName(gamePath).getDeclaredField("board");
		f.setAccessible(true);

		Cell[][] board = new Cell[g.getBoardHeight()][g.getBoardWidth()];
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				board[i][j] = new Cell();
			}

			board[i1][j1] = new Cell(p);
			board[i2][j2] = new Cell(t);
			f.set(g, board);
		}

	}

	private Direction getDirectionFromNumber(int i) {
		Direction d = null;
		switch (i) {
		case 1:
			d = Direction.UP;
			break;
		case 2:
			d = Direction.DOWN;
			break;
		case 3:
			d = Direction.RIGHT;
			break;
		case 4:
			d = Direction.LEFT;
			break;
		case 5:
			d = Direction.UPRIGHT;
			break;
		case 6:
			d = Direction.UPLEFT;
			break;
		case 7:
			d = Direction.DOWNRIGHT;
			break;
		case 8:
			d = Direction.DOWNLEFT;
			break;
		}
		return d;
	}

	private void attackAndCheck(String pieces, String targets, boolean isP1,
			boolean testWrapping, boolean armorRaised, Game g,
			ArrayList<Integer> checks) throws Exception {
		for (int i = 0; i < pieces.length(); i++) {
			for (int j = 0; j < targets.length(); j++) {
				int r = ((int) (Math.random() * 8)) + 1;
				Direction d = getDirectionFromNumber(r);
				ArrayList<Piece> res = preparePieces(pieces.charAt(i),
						targets.charAt(j), d, isP1, testWrapping, armorRaised,
						g);
				winCalled = false;
				if (isP1) {
					g.getPlayer1().setPayloadPos(0);
					if (checks.contains(9))
						g.getPlayer1().setSideKilled(0);
					if (checks.contains(10))
						g.getPlayer1().setSideKilled(1);
					g.getPlayer2().getDeadCharacters().clear();
				} else {
					g.getPlayer2().setPayloadPos(0);
					if (checks.contains(9))
						g.getPlayer2().setSideKilled(0);
					if (checks.contains(10))
						g.getPlayer2().setSideKilled(1);
					g.getPlayer1().getDeadCharacters().clear();
				}
				int pi = res.get(0).getPosI();
				int pj = res.get(0).getPosJ();
				int ti = res.get(1).getPosI();
				int tj = res.get(1).getPosJ();
				res.get(0).attack(res.get(1));
				String pOwner = "";
				if (isP1)
					pOwner = "player one";
				else
					pOwner = "player two";

				String firstPart = "If " + pOwner + "'s "
						+ getPieceName(pieces.charAt(i))
						+ " located at position (" + pi + "," + pj
						+ ") is attacking an enemy "
						+ getPieceName(targets.charAt(j));
				if (targets.charAt(j) == 'A') {
					if (armorRaised)
						firstPart += " which has his armor up";
					else
						firstPart += " which has his armor down";
				}
				if (checks.contains(1)) {
					assertTrue(
							firstPart
									+ ", the checkWinner method should be invoked on its game instance variable.",
							winCalled);
				}
				if (checks.contains(2)) {
					assertFalse(
							firstPart
									+ ", the checkWinner method should not be invoked on its game instance variable.",
							winCalled);
				}
				if (checks.contains(3)) {
					boolean isDead = false;
					if (isP1)
						isDead = g.getPlayer2().getDeadCharacters()
								.contains(res.get(1));
					else
						isDead = g.getPlayer1().getDeadCharacters()
								.contains(res.get(1));
					assertTrue(
							firstPart
									+ ", the attacked "
									+ getPieceName(targets.charAt(j))
									+ " should be added to its owner's dead characters list.",
							isDead);
				}
				if (checks.contains(4)) {
					boolean isDead = false;
					if (isP1)
						isDead = g.getPlayer2().getDeadCharacters()
								.contains(res.get(1));
					else
						isDead = g.getPlayer1().getDeadCharacters()
								.contains(res.get(1));
					assertFalse(
							firstPart
									+ ", the attacked "
									+ getPieceName(targets.charAt(j))
									+ " should not be added to its owner's dead characters list.",
							isDead);
				}
				if (checks.contains(5)) {
					Armored a = (Armored) res.get(1);
					assertEquals(
							firstPart
									+ ", the enemy armored's armorUp attribute should be set to false.",
							false, a.isArmorUp());

				}
				if (checks.contains(6)) {
					assertEquals(
							firstPart
									+ ", the attack method should not remove the enemy's armored piece from the board therefore, it should remain in its original position on the board.",
							res.get(1), g.getCellAt(ti, tj).getPiece());
					assertEquals(
							firstPart
									+ ", the attack method should not remove the enemy's armored piece from the board therefore, the armored's posI attibute should not change.",
							ti, res.get(1).getPosI());
					assertEquals(
							firstPart
									+ ", the attack method should not remove the enemy's armored piece from the board therefore, the armored's posJ attibute should not change.",
							tj, res.get(1).getPosJ());
				}
				if (checks.contains(7)) {
					assertEquals(
							firstPart
									+ ", the attack method should remove the enemy's armored piece from the board therefore, the removed armored's position on the board should be empty.",
							null, g.getCellAt(ti, tj).getPiece());
				}
				if (checks.contains(8)) {
					Player current = null;
					if (isP1)
						current = g.getPlayer1();
					else
						current = g.getPlayer2();
					assertEquals(firstPart + " and eliminates it, the "
							+ pOwner + "'s payLoad should move one step.", 1,
							current.getPayloadPos());
				}
				if (checks.contains(9)) {
					Player current = null;
					if (isP1)
						current = g.getPlayer1();
					else
						current = g.getPlayer2();

					assertEquals(
							firstPart
									+ " and it was its first eliminated side kick, the "
									+ pOwner
									+ "'s sideKilled attribute should be increased by one.",
							1, current.getSideKilled());
					assertEquals(
							firstPart
									+ " and it was its first eliminated side kick, the "
									+ pOwner + "'s payLoad should not move.",
							0, current.getPayloadPos());
				}
				if (checks.contains(10)) {
					Player current = null;
					if (isP1)
						current = g.getPlayer1();
					else
						current = g.getPlayer2();
					assertEquals(
							firstPart
									+ " and it was its second eliminated side kick, the "
									+ pOwner
									+ "'s payLoad should move one step.", 1,
							current.getPayloadPos());
				}
				if (checks.contains(11)) {
					assertEquals(
							firstPart
									+ ", the "
									+ getPieceName(pieces.charAt(i))
									+ " should not move therefore, it should remain in its original position on the board.",
							res.get(0), g.getCellAt(pi, pj).getPiece());
					assertEquals(
							firstPart + ", the "
									+ getPieceName(pieces.charAt(i))
									+ " should not move therefore, the "
									+ getPieceName(pieces.charAt(i))
									+ "'s posI attibute should not change.",
							pi, res.get(0).getPosI());
					assertEquals(
							firstPart + ", the "
									+ getPieceName(pieces.charAt(i))
									+ " should not move therefore, the "
									+ getPieceName(pieces.charAt(i))
									+ "'s posJ attibute should not change.",
							pj, res.get(0).getPosJ());
				}
			}
		}
	}

	private Game createGameForSwitchTurn() {

		int random = (int) (Math.random() * 40) + 10;
		random = (int) (Math.random() * 40) + 10;
		Player p1 = new Player("Player_" + random);
		random = (int) (Math.random() * 40) + 10;
		Player p2 = new Player("Player_" + random);
		switchTurnsCalled = false;
		Game g = new Game(p1, p2) {
			@Override
			public void switchTurns() {
				switchTurnsCalled = true;
			}
		};

		return g;

	}

	private void testSwitchTurnHelper(String powerUser, String powerTargets,
			Point userLoc, Direction powerDir, Point targetLoc,
			boolean shouldSwitch) throws Exception {

		Game game = createGameForSwitchTurn();
		Player currentPlayer = game.getCurrentPlayer();
		Player enemy = (game.getPlayer1() == currentPlayer ? game.getPlayer2()
				: game.getPlayer1());

		switch (powerUser.charAt(0)) {

		case 'R':

			Ranged r = new Ranged(currentPlayer, game, "Ranged");
			Field f = Class.forName(rangedPath).getDeclaredField("range");
			f.setAccessible(true);
			f.set(r, Integer.parseInt(powerUser.charAt(1) + ""));

			game = clearAndInsert(
					game,
					new Object[][] {
							{ userLoc.x, userLoc.y, r },
							{
									targetLoc.x,
									targetLoc.y,
									createPiece(powerTargets.charAt(0), enemy,
											game) } });

			r.usePower(powerDir, null, null);

			break;

		}

		assertEquals(
				"After using the "
						+ createPiece(powerUser.charAt(0), currentPlayer, game)
								.getClass().getSimpleName()
						+ " power, the turn should "
						+ (shouldSwitch ? "" : "not ") + "be switched",
				shouldSwitch, switchTurnsCalled);

	}

	private void testInstanceVariableIsPresent(Class<?> aClass, String varName,
			boolean implementedVar) throws SecurityException {

		boolean thrown = false;
		try {
			aClass.getDeclaredField(varName);
		} catch (NoSuchFieldException e) {
			thrown = true;
		}
		if (implementedVar) {
			assertFalse("There should be \"" + varName
					+ "\" instance variable in class " + aClass.getSimpleName()
					+ ".", thrown);
		} else {
			assertTrue(
					"The instance variable \"" + varName
							+ "\" should not be declared in class "
							+ aClass.getSimpleName() + ".", thrown);
		}
	}

	private void testInstanceVariableIsPrivate(Class<?> aClass, String varName)
			throws NoSuchFieldException, SecurityException {
		Field f = aClass.getDeclaredField(varName);
		assertEquals("The \"" + varName + "\" instance variable in class "
				+ aClass.getSimpleName()
				+ " should not be accessed outside that class.", 2,
				f.getModifiers());
	}

	private Game createGameForUsePower() {

		int random = (int) (Math.random() * 40) + 10;
		random = (int) (Math.random() * 40) + 10;
		Player p1 = new Player("Player_" + random);
		random = (int) (Math.random() * 40) + 10;
		Player p2 = new Player("Player_" + random);

		Game g = new Game(p1, p2);

		return g;

	}

	private Ranged createRangedForAttack(Player p, Game g, String s) {

		Ranged r = null;
		attackedPiece1 = null;
		attackedPiece2 = null;
		attackCalled = false;
		r = new Ranged(p, g, s) {
			@Override
			public void attack(Piece p2) {
				attackCalled = true;
				if (attackedPiece1 == null)
					attackedPiece1 = p2;
				else
					attackedPiece2 = p2;
			}

		};
		return (r);

	}

	private void testUsePowerRangedHelper(String powerUser,
			String powerTargets, Point userLoc, Direction powerDir,
			Point targetLoc, boolean powerUsedBefore, boolean onEnemy)
			throws Exception {
		Game game = createGameForUsePower();
		Player currentPlayer = game.getCurrentPlayer();
		Player enemy = (game.getPlayer1() == currentPlayer ? game.getPlayer2()
				: game.getPlayer1());

		Ranged r = createRangedForAttack(currentPlayer, game, "Ranged");
		Field f = Class.forName(rangedPath).getDeclaredField("range");
		f.setAccessible(true);
		f.set(r, Integer.parseInt(powerUser.charAt(1) + ""));
		Piece targetPiece;
		Piece targetPiece2 = null;
		if (onEnemy)
			targetPiece = createPiece(powerTargets.charAt(0), enemy, game);
		else
			targetPiece = createPiece(powerTargets.charAt(0), currentPlayer,
					game);
		if (powerTargets.length() > 1) {
			targetPiece2 = createPiece(powerTargets.charAt(1), enemy, game);

		}
		if (powerTargets.length() > 1) {
			game = clearAndInsert(game, new Object[][] {
					{ userLoc.x, userLoc.y, r },
					{ targetLoc.x, targetLoc.y, targetPiece },
					{ targetLoc.x - 1, targetLoc.y, targetPiece2 } });
		} else {
			game = clearAndInsert(game, new Object[][] {
					{ userLoc.x, userLoc.y, r },
					{ targetLoc.x, targetLoc.y, targetPiece } });
		}

		// on friendly
		if (!onEnemy && targetPiece2 == null) {
			try {
				r.usePower(powerDir, null, null);
				fail("If a "
						+ "Ranged"
						+ " hero tries to use its power in a direction that will hit a friendly piece, an InvalidPowerDirectionException should be thrown.");
			} catch (InvalidPowerUseException e) {
			}
			return;
		}

		// invalid direction
		if (powerDir != Direction.UP) {
			try {
				r.usePower(powerDir, null, null);
				fail("Whenever "
						+ "Ranged"
						+ " hero tries to use its power in a direction which is not allowed an InvalidPowerDirectionException should be thrown.");
			} catch (InvalidPowerDirectionException e) {
			}
			return;
		}

		testVariablePowerUsedValueSetter(r, false);
		if (userLoc.x - Integer.parseInt(powerUser.charAt(1) + "") <= targetLoc.x) {

			try {
				r.usePower(powerDir, null, null);

				assertTrue(
						"attack method should be called whenever "
								+ "Ranged"
								+ " hero uses its power on an enemy piece in its range.",
						attackCalled);
				if (targetPiece.getOwner() == enemy) {
					assertEquals(
							"attack method should be called with the correct parameter (only first enemy in range) whenever "
									+ "Ranged"
									+ " hero uses its power on an enemy piece in its range.",
							targetPiece, attackedPiece1);
					if (targetPiece2 != null)
						assertNotEquals(
								"attack method should be called with the correct parameter (only first enemy in range) whenever "
										+ "Ranged"
										+ " hero uses its power on an enemy piece in its range.",
								targetPiece2, attackedPiece2);
				} else {
					assertNotEquals(
							"attack method should be called with the correct parameter (first enemy in range) whenever "
									+ "Ranged"
									+ " hero uses its power on an enemy piece in its range.",
							targetPiece, attackedPiece1);
					assertEquals(
							"attack method should be called with the correct parameter (first enemy in range) whenever "
									+ "Ranged"
									+ " hero uses its power on an enemy piece in its range.",
							targetPiece2, attackedPiece1);
				}

				testVariablePowerUsedValue(r, true);

			} catch (Exception e) {
				fail("Ranged"
						+ " hero should be able to use its power if it was not used before, its direction is valid and there are enemy heroes in its range.");
			}
			return;
		} else {
			try {
				r.usePower(powerDir, null, null);
				fail("Whenever "
						+ "Ranged"
						+ " hero tries to use its power in an allowed direction and there is no enemy in its range, an InvalidPowerDirectionException should be thrown.");
			} catch (InvalidPowerDirectionException e) {
			}

		}

	}

	private Point getFinalDestination(Point pos, Direction d, int step,
			boolean wrapping) {

		Point p = new Point();
		p.x = pos.x;
		p.y = pos.y;
		switch (d) {
		case DOWN:
			p.x += step;
			break;
		case DOWNLEFT:
			p.x += step;
			p.y -= step;
			break;
		case DOWNRIGHT:
			p.x += step;
			p.y += step;
			break;
		case LEFT:
			p.y -= step;
			break;
		case RIGHT:
			p.y += step;
			break;
		case UP:
			p.x -= step;
			break;
		case UPLEFT:
			p.x -= step;
			p.y -= step;
			break;
		case UPRIGHT:
			p.x -= step;
			p.y += step;
			break;

		}

		if (!wrapping)
			return p;
		else {
			p.x += boardHeight;
			p.y += boardWidth;
			p.x %= boardHeight;
			p.y %= boardWidth;
			return p;
		}

	}

	private void testVariablePowerUsedValue(ActivatablePowerHero r,
			boolean shouldBeUsed) throws NoSuchFieldException,
			SecurityException, IllegalArgumentException, IllegalAccessException {
		Field f = ActivatablePowerHero.class.getDeclaredField("powerUsed");
		f.setAccessible(true);
		assertEquals(
				r.getClass().getSimpleName()
						+ " hero should update the value of the \"powerUsed\" variable correctly",
				shouldBeUsed, (boolean) f.get(r));
	}

	private Piece createPiece(char c, Player p, Game g) {

		Piece r = null;

		switch (c) {
		case 'P':
			r = new Speedster(p, g, "Speedster");
			break;
		case 'S':
			r = new Super(p, g, "Super");
			break;
		case 'T':
			r = new Tech(p, g, "Tech");
			break;
		case 'R':
			r = new Ranged(p, g, "Ranged");
			break;
		case 'A':
			r = new Armored(p, g, "Armored");
			break;

		case 'M':
			r = new Medic(p, g, "Medic");
			break;

		case 'K':
			if (p == g.getPlayer1())
				r = new SideKickP1(g, "SideKickP1");
			if (p == g.getPlayer2())
				r = new SideKickP2(g, "SideKickP2");
			break;

		case 'N':
			r = null;
			break;

		}
		return (r);

	}

	private void testVariablePowerUsedValueSetter(ActivatablePowerHero r,
			boolean value) throws NoSuchFieldException, SecurityException,
			IllegalArgumentException, IllegalAccessException {
		Field f = ActivatablePowerHero.class.getDeclaredField("powerUsed");
		f.setAccessible(true);
		f.set(r, value);
	}

	private Game clearAndInsert(Game g, Object[][] insertionDetails)
			throws Exception {

		Field f = Class.forName(gamePath).getDeclaredField("board");
		f.setAccessible(true);

		Cell[][] board = new Cell[g.getBoardHeight()][g.getBoardWidth()];
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				board[i][j] = new Cell();
			}
		}

		for (int i = 0; i < insertionDetails.length; i++) {

			if (insertionDetails[i].length == 3) {
				int x = (int) insertionDetails[i][0];
				int y = (int) insertionDetails[i][1];
				Piece p = (Piece) insertionDetails[i][2];
				board[x][y] = new Cell(p);
				if (p != null) {
					p.setPosI(x);
					p.setPosJ(y);
				}

			}

		}

		f.set(g, board);
		return g;

	}

}
