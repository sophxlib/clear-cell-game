package model;

/**
 * This class represents the logic of a game where a board is updated on each
 * step of the game animation. The board can also be updated by selecting a
 * board cell.
 * 
 * CMSC132 project
 */

public abstract class Game {
	protected BoardCell[][] board;

	/**
	 * Defines a board with BoardCell.EMPTY cells.
	 * 
	 * @param maxRows
	 * @param maxCols
	 */
	public Game(int maxRows, int maxCols) {
		this.board = new BoardCell[maxRows][maxCols];

		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				board[i][j] = BoardCell.EMPTY;
			}
		}
	}

	public int getMaxRows() {
		return this.board.length;
	}

	public int getMaxCols() {
		return this.board[0].length;
	}

	public void setBoardCell(int rowIndex, int colIndex, BoardCell boardCell) {
		if (rowIndex >= 0 && rowIndex < this.getMaxRows() && colIndex >= 0 && colIndex < this.getMaxCols()) {
			this.board[rowIndex][colIndex] = boardCell;
		}
	}

	public BoardCell getBoardCell(int rowIndex, int colIndex) {
		if (rowIndex < this.getMaxRows() && colIndex < this.getMaxCols() && rowIndex >= 0 && colIndex >= 0) {
			return this.board[rowIndex][colIndex];
		}
		return null;
	}

	/**
	 * Initializes row with the specified color.
	 * 
	 * @param rowIndex
	 * @param cell
	 */
	public void setRowWithColor(int rowIndex, BoardCell cell) {
		if (rowIndex < 0 || rowIndex >= getMaxRows()) {
			return;
		}

		for (int i = 0; i < this.getMaxCols(); i++) {
			this.board[rowIndex][i] = cell;
		}
	}

	/**
	 * Initializes column with the specified color.
	 * 
	 * @param colIndex
	 * @param cell
	 */
	public void setColWithColor(int colIndex, BoardCell cell) {
		if (colIndex < 0 || colIndex >= getMaxCols()) {
			return;
		}

		for (int i = 0; i < this.getMaxRows(); i++) {
			this.board[i][colIndex] = cell;
		}
	}

	/**
	 * Initializes the board with the specified color.
	 * 
	 * @param cell
	 */
	public void setBoardWithColor(BoardCell cell) {
		for (int i = 0; i < this.getMaxRows(); i++) {
			for (int j = 0; j < this.getMaxCols(); j++) {
				this.board[i][j] = cell;
			}
		}
	}

	public abstract boolean isGameOver();

	public abstract int getScore();

	/**
	 * Advances the animation one step.
	 */
	public abstract void nextAnimationStep();

	/**
	 * Adjust the board state according to the current board state and the selected
	 * cell.
	 * 
	 * @param rowIndex
	 * @param colIndex
	 */
	public abstract void processCell(int rowIndex, int colIndex);
}
