package model.pieces.heroes;

import model.game.Direction;
import model.game.Game;
import model.game.Player;
import exceptions.OccupiedCellException;
import exceptions.WrongTurnException;

public class Speedster extends NonActivatablePowerHero {

	public Speedster(Player player, Game game, String name) {
		super(player, game, name);
	}

	@Override
	public void moveUp() throws OccupiedCellException, WrongTurnException {

		int newX = getPosI() - 2 + getGame().getBoardHeight();

		newX %= getGame().getBoardHeight();

		MoveHelper(newX, getPosJ(), Direction.UP);

	}

	@Override
	public void moveDown() throws OccupiedCellException, WrongTurnException {

		int newX = getPosI() + 2;

		newX %= getGame().getBoardHeight();

		MoveHelper(newX, getPosJ(), Direction.DOWN);

	}

	@Override
	public void moveRight() throws OccupiedCellException, WrongTurnException {

		int newY = getPosJ() + 2;

		newY %= getGame().getBoardWidth();

		MoveHelper(getPosI(), newY, Direction.RIGHT);

	}

	@Override
	public void moveLeft() throws OccupiedCellException, WrongTurnException {

		int newY = getPosJ() - 2 + getGame().getBoardWidth();

		newY %= getGame().getBoardWidth();

		MoveHelper(getPosI(), newY, Direction.LEFT);

	}

	@Override
	public void moveUpRight() throws OccupiedCellException, WrongTurnException {

		int newX = getPosI() - 2 + getGame().getBoardHeight();
		int newY = getPosJ() + 2;

		newX %= getGame().getBoardHeight();

		newY %= getGame().getBoardWidth();

		MoveHelper(newX, newY, Direction.UPRIGHT);

	}

	@Override
	public void moveUpLeft() throws OccupiedCellException, WrongTurnException {

		int newX = getPosI() - 2 + getGame().getBoardHeight();
		int newY = getPosJ() - 2 + getGame().getBoardWidth();

		newX %= getGame().getBoardHeight();

		newY %= getGame().getBoardWidth();

		MoveHelper(newX, newY, Direction.UPLEFT);

	}

	@Override
	public void moveDownRight() throws OccupiedCellException,
			WrongTurnException {

		int newX = getPosI() + 2;
		int newY = getPosJ() + 2;

		newX %= getGame().getBoardHeight();

		newY %= getGame().getBoardWidth();

		MoveHelper(newX, newY, Direction.DOWNRIGHT);

	}

	@Override
	public void moveDownLeft() throws OccupiedCellException, WrongTurnException {

		int newX = getPosI() + 2;
		int newY = getPosJ() - 2 + getGame().getBoardWidth();

		newX %= getGame().getBoardHeight();

		newY %= getGame().getBoardWidth();

		MoveHelper(newX, newY, Direction.DOWNLEFT);

	}

	@Override
	public String toString() {
		return "S";
	}
}
