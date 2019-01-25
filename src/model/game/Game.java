package model.game;

import java.util.ArrayList;
import java.util.Collections;

import model.pieces.Piece;
import model.pieces.heroes.Armored;
import model.pieces.heroes.Medic;
import model.pieces.heroes.Ranged;
import model.pieces.heroes.Speedster;
import model.pieces.heroes.Super;
import model.pieces.heroes.Tech;
import model.pieces.sidekicks.SideKickP1;
import model.pieces.sidekicks.SideKickP2;

public class Game {

	private final int payloadPosTarget = 6;
	private final int boardWidth = 6;

	private final int boardHeight = 7;

	private final int p1InitRow = boardHeight - 3;

	private final int p2InitRow = 1;

	private Player player1;
	private Player player2;

	private Player currentPlayer;

	private Cell[][] board;

	public Cell getCellAt(int i, int j) {
		return board[i][j];
	}

	public void setPieceAt(int i, int j, Piece p) {
		board[i][j].setPiece(p);
	}

	public Game(Player player1, Player player2) {
		super();
		this.player1 = player1;
		this.player2 = player2;

		board = new Cell[boardHeight][boardWidth];
		assemblePieces();
		currentPlayer = player1;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public Player getPlayer1() {
		return player1;
	}

	public Player getPlayer2() {
		return player2;
	}

	public void switchTurns() {
		currentPlayer = (currentPlayer == player1) ? player2 : player1;
	}

	public int getPayloadPosTarget() {
		return payloadPosTarget;
	}

	public void assemblePieces() {

		ArrayList<Integer> ind1 = new ArrayList<Integer>();
		ArrayList<Integer> ind2 = new ArrayList<Integer>();

		for (int i = 0; i < boardWidth; i++) {
			ind1.add(i);
			ind2.add(i);
		}

		Collections.shuffle(ind1);
		Collections.shuffle(ind2);

		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				board[i][j] = new Cell();
			}
		}

		Armored a1 = new Armored(player1, this, "Wonder Woman");
		board[p1InitRow + 1][ind1.remove(0)].setPiece(a1);
		Armored a2 = new Armored(player2, this, "Captain America");
		board[p2InitRow][ind2.remove(0)].setPiece(a2);

		Medic m1 = new Medic(player1, this, "Green Lantern");
		board[p1InitRow + 1][ind1.remove(0)].setPiece(m1);

		Medic m2 = new Medic(player2, this, "Vision");
		board[p2InitRow][ind2.remove(0)].setPiece(m2);

		Ranged r1 = new Ranged(player1, this, "Green Arrow");
		board[p1InitRow + 1][ind1.remove(0)].setPiece(r1);

		Ranged r2 = new Ranged(player2, this, "Hawk Eye");
		board[p2InitRow][ind2.remove(0)].setPiece(r2);

		Speedster sp1 = new Speedster(player1, this, "Flash");
		board[p1InitRow + 1][ind1.remove(0)].setPiece(sp1);

		Speedster sp2 = new Speedster(player2, this, "Quick Silver");
		board[p2InitRow][ind2.remove(0)].setPiece(sp2);

		Super su1 = new Super(player1, this, "Superman");
		board[p1InitRow + 1][ind1.remove(0)].setPiece(su1);

		Super su2 = new Super(player2, this, "Hulk");
		board[p2InitRow][ind2.remove(0)].setPiece(su2);

		Tech t1 = new Tech(player1, this, "Batman");
		board[p1InitRow + 1][ind1.remove(0)].setPiece(t1);

		Tech t2 = new Tech(player2, this, "Ironman");
		board[p2InitRow][ind2.remove(0)].setPiece(t2);

		for (int j = 0; j < boardWidth; j++) {
			board[p1InitRow][j] = new Cell();
			board[p2InitRow + 1][j] = new Cell();
		}

		for (int j = 0; j < boardWidth; j++) {
			board[p1InitRow][j].setPiece(new SideKickP1(this, "SK1" + j));
			board[p2InitRow + 1][j].setPiece(new SideKickP2(this, "SK2" + j));
		}
		for (int i = 0; i < board.length; i++)
			for (int j = 0; j < board[0].length; j++)
				if (board[i][j].getPiece() != null) {
					board[i][j].getPiece().setPosI(i);
					board[i][j].getPiece().setPosJ(j);
				}
	}

	public boolean checkWinner() {
		if (currentPlayer.getPayloadPos() == payloadPosTarget) {
			return true;
		}

		return false;
	}

	@Override
	public String toString() {
		String s = "";
		System.out.println("      " + getPlayer2().getName());
		System.out.print("| ");
		for (int i = 0; i < board[0].length; i++)
			System.out.print("--");
		System.out.println("|");
		for (int i = 0; i < board.length; i++) {
			System.out.print("| ");
			for (int j = 0; j < board[0].length; j++) {
				if (board[i][j] == null)
					System.out.print("n ");
				else
					System.out.print(board[i][j] + " ");
			}
			System.out.println("|");
		}
		System.out.print("| ");
		for (int i = 0; i < board[0].length; i++)
			System.out.print("--");
		System.out.println("|");
		System.out.println("    " + getPlayer1().getName());
		return s;
	}

	public int getBoardWidth() {
		return boardWidth;
	}

	public int getBoardHeight() {
		return boardHeight;
	}

}
