package model.pieces.heroes;

import java.awt.Point;

import model.game.Direction;
import model.game.Game;
import model.game.Player;
import model.pieces.Piece;
import exceptions.InvalidPowerTargetException;
import exceptions.InvalidPowerUseException;
import exceptions.UnallowedMovementException;
import exceptions.WrongTurnException;

public class Tech extends ActivatablePowerHero {

	public Tech(Player player, Game game, String name) {
		super(player, game, name);
	}

	@Override
	public void moveUp() throws UnallowedMovementException {
		throw new UnallowedMovementException(this, Direction.UP);
	}

	@Override
	public void moveDown() throws UnallowedMovementException {
		throw new UnallowedMovementException(this, Direction.DOWN);
	}

	@Override
	public void moveRight() throws UnallowedMovementException {
		throw new UnallowedMovementException(this, Direction.RIGHT);
	}

	@Override
	public void moveLeft() throws UnallowedMovementException {
		throw new UnallowedMovementException(this, Direction.LEFT);
	}

	@Override
	public void usePower(Direction d, Piece target, Point newPos)
			throws InvalidPowerUseException, WrongTurnException {

		super.usePower(d, target, newPos);

		// Teleport
		if (newPos != null) {

			if (target.getOwner() == getOwner()) {

				if (getGame().getCellAt(newPos.x, newPos.y).getPiece() != null) {
					throw new InvalidPowerTargetException(
							this.getName()
									+ " can not place "
									+ target.getName()
									+ " in the specified cell or direction as this cell is occupied",
							this, target);
				} else {

					getGame().getCellAt(newPos.x, newPos.y).setPiece(target);
					getGame().getCellAt(target.getPosI(), target.getPosJ())
							.setPiece(null);
					target.setPosI(newPos.x);
					target.setPosJ(newPos.y);
					setPowerUsed(true);

				}
			} else {
				throw new InvalidPowerTargetException(
						this.getName()
								+ " can not choose "
								+ target.getName()
								+ " as a target because of incompatible target's side with the power requirement (Enemy/Ally)",
						this, target);
			}
		} else {

			// Hack Power
			if (target instanceof Hero && target.getOwner() != this.getOwner()) {

				if (target instanceof Armored) {

					if (((Armored) target).isArmorUp()) {
						((Armored) target).setArmorUp(false);
						setPowerUsed(true);
					} else
						throw new InvalidPowerTargetException(this.getName()
								+ " can not hack " + target.getName()
								+ " as target's power is already used", this,
								target);
				}

				if (target instanceof ActivatablePowerHero) {

					if (!((ActivatablePowerHero) target).isPowerUsed()) {
						((ActivatablePowerHero) target).setPowerUsed(true);
						setPowerUsed(true);
					} else {
						throw new InvalidPowerTargetException(this.getName()
								+ " can not hack " + target.getName()
								+ " as target's power is already used", this,
								target);
					}
				}
			}

			// Restore Power
			if (target instanceof Hero && target.getOwner() == this.getOwner()) {

				if (target instanceof Armored) {

					if (!((Armored) target).isArmorUp()) {
						((Armored) target).setArmorUp(true);
						setPowerUsed(true);
					} else
						throw new InvalidPowerTargetException(this.getName()
								+ " can not restore " + target.getName()
								+ "'s power as it is already not used", this,
								target);

				}

				if (target instanceof ActivatablePowerHero) {

					if (((ActivatablePowerHero) target).isPowerUsed()) {
						((ActivatablePowerHero) target).setPowerUsed(false);
						setPowerUsed(true);
					} else {
						throw new InvalidPowerTargetException(this.getName()
								+ " can not restore " + target.getName()
								+ "'s power as it is already not used", this,
								target);
					}
				}
			}
		}
	}

	@Override
	public String toString() {
		return "T";
	}
}
