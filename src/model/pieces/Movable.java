package model.pieces;

import model.game.Direction;
import exceptions.OccupiedCellException;
import exceptions.UnallowedMovementException;
import exceptions.WrongTurnException;

public interface Movable {
	void move(Direction r) throws UnallowedMovementException,
			OccupiedCellException, WrongTurnException;
}
