package model.pieces.heroes;

import java.awt.Point;

import model.game.Cell;
import model.game.Direction;
import model.game.Game;
import model.game.Player;
import model.pieces.Piece;
import exceptions.InvalidPowerDirectionException;
import exceptions.InvalidPowerUseException;
import exceptions.UnallowedMovementException;
import exceptions.WrongTurnException;

public class Super extends ActivatablePowerHero {

	public Super(Player player, Game game, String name) {
		super(player, game, name);
	}

	@Override
	public void moveUpRight() throws UnallowedMovementException {
		throw new UnallowedMovementException(this, Direction.UPRIGHT);
	}

	@Override
	public void moveUpLeft() throws UnallowedMovementException {
		throw new UnallowedMovementException(this, Direction.UPLEFT);
	}

	@Override
	public void moveDownRight() throws UnallowedMovementException {
		throw new UnallowedMovementException(this, Direction.DOWNRIGHT);
	}

	@Override
	public void moveDownLeft() throws UnallowedMovementException {
		throw new UnallowedMovementException(this, Direction.DOWNLEFT);
	}

	@Override
	public void usePower(Direction d, Piece target, Point newPos)
			throws InvalidPowerUseException, WrongTurnException {

		super.usePower(d, target, newPos);

		switch (d) {
		case DOWNLEFT:
		case DOWNRIGHT:
		case UPRIGHT:
		case UPLEFT:
			throw new InvalidPowerDirectionException(this.getName()
					+ "'s power cannot be used in direction: " + d, this, d);
		default:
			break;
		}

		Point adjacent1 = new Point();
		Point adjacent2 = new Point();
		adjacent1 = getDirectionPos(new Point(getPosI(), getPosJ()), d);
		adjacent2 = getDirectionPos(adjacent1, d);

		if (adjacent1.x >= 0 && adjacent1.x < getGame().getBoardHeight()
				&& adjacent1.y >= 0 && adjacent1.y < getGame().getBoardWidth()) {

			Cell c1 = getGame().getCellAt(adjacent1.x, adjacent1.y);
			Piece p1 = c1.getPiece();

			if (p1 != null && p1.getOwner() != getOwner()) {
				this.attack(p1);
			}
		}
		if (adjacent2.x >= 0 && adjacent2.x < getGame().getBoardHeight()
				&& adjacent2.y >= 0 && adjacent2.y < getGame().getBoardWidth()) {

			Cell c2 = getGame().getCellAt(adjacent2.x, adjacent2.y);
			Piece p2 = c2.getPiece();

			if (p2 != null && p2.getOwner() != getOwner()) {
				this.attack(p2);
			}
		}

		setPowerUsed(true);
		getGame().switchTurns();

	}

	@Override
	public String toString() {
		return "P";
	}
}
