package model.pieces;

import java.awt.Point;

import model.game.Cell;
import model.game.Direction;
import model.game.Game;
import model.game.Player;
import model.pieces.heroes.Armored;
import model.pieces.sidekicks.SideKick;
import exceptions.OccupiedCellException;
import exceptions.UnallowedMovementException;
import exceptions.WrongTurnException;

public abstract class Piece implements Movable {

	private String name;
	private Player owner;
	private Game game;
	private int posI;
	private int posJ;

	public Piece(Player p, Game g, String name) {
		owner = p;
		this.game = g;
		this.name = name;
	}

	@Override
	public void move(Direction r) throws UnallowedMovementException,
			OccupiedCellException, WrongTurnException {

		if (this.getOwner() != getGame().getCurrentPlayer())
			throw new WrongTurnException(this);

		switch (r) {

		case DOWN:
			moveDown();
			break;

		case DOWNLEFT:
			moveDownLeft();
			break;

		case DOWNRIGHT:
			moveDownRight();
			break;

		case LEFT:
			moveLeft();
			break;

		case RIGHT:
			moveRight();
			break;

		case UP:
			moveUp();
			break;

		case UPLEFT:
			moveUpLeft();
			break;

		case UPRIGHT:
			moveUpRight();
			break;

		}
	}

	public void moveUp() throws UnallowedMovementException,
			OccupiedCellException, WrongTurnException {

		int newX = posI - 1;

		if (newX < 0) {
			newX = game.getBoardHeight() - 1;
		}

		MoveHelper(newX, posJ, Direction.UP);

	}

	public void moveDown() throws UnallowedMovementException,
			OccupiedCellException, WrongTurnException {

		int newX = posI + 1;

		if (newX == game.getBoardHeight()) {
			newX = 0;
		}

		MoveHelper(newX, posJ, Direction.DOWN);

	}

	public void moveRight() throws UnallowedMovementException,
			OccupiedCellException, WrongTurnException {

		int newY = posJ + 1;

		if (newY == game.getBoardWidth()) {
			newY = 0;
		}

		MoveHelper(posI, newY, Direction.RIGHT);

	}

	public void moveLeft() throws UnallowedMovementException,
			OccupiedCellException, WrongTurnException {

		int newY = posJ - 1;

		if (newY < 0) {
			newY = game.getBoardWidth() - 1;
		}

		MoveHelper(posI, newY, Direction.LEFT);

	}

	public void moveUpRight() throws UnallowedMovementException,
			OccupiedCellException, WrongTurnException {

		int newX = posI - 1;
		int newY = posJ + 1;

		if (newX < 0) {
			newX = game.getBoardHeight() - 1;
		}

		if (newY == game.getBoardWidth()) {
			newY = 0;
		}

		MoveHelper(newX, newY, Direction.UPRIGHT);

	}

	public void moveUpLeft() throws UnallowedMovementException,
			OccupiedCellException, WrongTurnException {

		int newX = posI - 1;
		int newY = posJ - 1;

		if (newX < 0) {
			newX = game.getBoardHeight() - 1;
		}

		if (newY < 0) {
			newY = game.getBoardWidth() - 1;
		}

		MoveHelper(newX, newY, Direction.UPLEFT);

	}

	public void moveDownRight() throws UnallowedMovementException,
			OccupiedCellException, WrongTurnException {

		int newX = posI + 1;
		int newY = posJ + 1;

		if (newX == game.getBoardHeight()) {
			newX = 0;
		}

		if (newY == game.getBoardWidth()) {
			newY = 0;
		}

		MoveHelper(newX, newY, Direction.DOWNRIGHT);

	}

	public void moveDownLeft() throws UnallowedMovementException,
			OccupiedCellException, WrongTurnException {

		int newX = posI + 1;
		int newY = posJ - 1;

		if (newX == game.getBoardHeight()) {
			newX = 0;
		}
		if (newY < 0) {
			newY = game.getBoardWidth() - 1;
		}

		MoveHelper(newX, newY, Direction.DOWNLEFT);

	}

	public void attack(Piece target) {

		if (!(target instanceof Armored && ((Armored) target).isArmorUp())) {

			if (target instanceof SideKick) {
				owner.setSideKilled(owner.getSideKilled() + 1);
				target.getOwner().getDeadCharacters().add(target);

				if (owner.getSideKilled() % 2 == 0) {
					owner.setPayloadPos(owner.getPayloadPos() + 1);
				}

			} else {
				owner.setPayloadPos(owner.getPayloadPos() + 1);
				target.getOwner().getDeadCharacters().add(target);

			}

			game.getCellAt(target.getPosI(), target.getPosJ()).setPiece(null);

			if (game.checkWinner()) {
				return;
			}

		} else {

			((Armored) target).setArmorUp(false);

		}
	}

	public void MoveHelper(int newX, int newY, Direction d)
			throws OccupiedCellException, WrongTurnException {

		Cell oldCell = game.getCellAt(posI, posJ);
		Cell newCell = game.getCellAt(newX, newY);
		Piece movingPiece = oldCell.getPiece();
		Piece occupantPiece = newCell.getPiece();

		if ((occupantPiece != null && movingPiece.getOwner() != occupantPiece.owner)
				|| (occupantPiece == null)) {
			if (occupantPiece != null) {
				movingPiece.attack(occupantPiece);
				movingPiece = oldCell.getPiece();
			}
			if (getGame().getCellAt(newX, newY).getPiece() == null) {
				newCell.setPiece(movingPiece);
				oldCell.setPiece(null);
				movingPiece.posI = newX;
				movingPiece.posJ = newY;
			}
			game.switchTurns();
		} else {
			throw new OccupiedCellException(this, d);
		}

	}

	public String getName() {
		return name;
	}

	public void setPosI(int i) {
		posI = i;
	}

	public void setPosJ(int j) {
		posJ = j;
	}

	public Game getGame() {
		return game;
	}

	public Player getOwner() {
		return owner;
	}

	public int getPosI() {
		return posI;
	}

	public int getPosJ() {
		return posJ;
	}

	public Point getDirectionPos(Point pos, Direction d) {

		Point p = new Point();
		p.x = pos.x;
		p.y = pos.y;

		switch (d) {

		case DOWN:
			p.x++;
			break;

		case DOWNLEFT:
			p.x++;
			p.y--;
			break;

		case DOWNRIGHT:
			p.x++;
			p.y++;
			break;

		case LEFT:
			p.y--;
			break;

		case RIGHT:
			p.y++;
			break;

		case UP:
			p.x--;
			break;

		case UPLEFT:
			p.x--;
			p.y--;
			break;

		case UPRIGHT:
			p.x--;
			p.y++;
			break;

		}

		return p;

	}

	public void adjustBounds(Point p) {

		if (p.x >= game.getBoardHeight()) {
			p.x = 0;
		}

		if (p.y >= game.getBoardWidth()) {
			p.y = 0;
		}

		if (p.x < 0) {
			p.x = game.getBoardHeight() - 1;
		}

		if (p.y < 0) {
			p.y = game.getBoardWidth() - 1;
		}
	}
}
