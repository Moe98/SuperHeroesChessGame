package model.pieces.heroes;

import java.awt.Point;

import model.game.Direction;
import model.game.Game;
import model.game.Player;
import model.pieces.Piece;
import exceptions.InvalidPowerDirectionException;
import exceptions.InvalidPowerUseException;
import exceptions.WrongTurnException;

public class Ranged extends ActivatablePowerHero {

	public Ranged(Player player, Game game, String name) {
		super(player, game, name);
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

		Piece hit = null;
		if (d == Direction.RIGHT) {
			for (int j = getPosJ() + 1; j < getGame().getBoardWidth(); j++) {
				hit = getGame().getCellAt(getPosI(), j).getPiece();
				if (hit != null) {
					break;
				}
			}
		} else if (d == Direction.LEFT) {
			for (int j = getPosJ() - 1; j >= 0; j--) {
				hit = getGame().getCellAt(getPosI(), j).getPiece();
				if (hit != null) {
					break;
				}
			}
		} else if (d == Direction.UP) {
			for (int i = getPosI() - 1; i >= 0; i--) {
				hit = getGame().getCellAt(i, getPosJ()).getPiece();
				if (hit != null) {
					break;
				}
			}
		} else if (d == Direction.DOWN) {
			for (int i = getPosI() + 1; i < getGame().getBoardHeight(); i++) {
				hit = getGame().getCellAt(i, getPosJ()).getPiece();
				if (hit != null) {
					break;
				}
			}
		}

		if (hit != null) {
			if (hit.getOwner() != getOwner()) {

				this.attack(hit);

			} else {
				throw new InvalidPowerDirectionException("Using "
						+ this.getName() + "'s power in direction " + d.name()
						+ " will result in hitting no enemies", this, d);
			}
		} else {
			throw new InvalidPowerDirectionException("Using " + this.getName()
					+ "'s power in direction " + d.name()
					+ " will result in hitting no enemies", this, d);
		}

		setPowerUsed(true);
		getGame().switchTurns();

	}

	@Override
	public String toString() {
		return "R";
	}
}
