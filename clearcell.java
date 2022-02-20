package model;
import java.awt.*;
import java.util.Random;

/**
 * This class extends GameModel and implements the logic of the clear cell game.
 * We define an empty cell as BoardCell.EMPTY. An empty row is defined as one
 * where every cell corresponds to BoardCell.EMPTY.
 * 
 * CMSC132 project
 */

public class ClearCellGame extends Game {
	private Random random; //random variable
	private int strategy;
	private int score; //player's score

	/**
	 * Defines a board with empty cells. It relies on the super class constructor to
	 * define the board. The random parameter is used for the generation of random
	 * cells. The strategy parameter defines which clearing cell strategy to use
	 * (for this project it will be 1). For fun, you can add your own strategy by
	 * using a value different that one.
	 * 
	 * @param maxRows
	 * @param maxCols
	 * @param random
	 * @param strategy
	 */
	public ClearCellGame(int maxRows, int maxCols, Random random, int strategy) {
		super(maxRows, maxCols);
		this.random = random; 
		this.strategy = strategy;
		this.score = 0;
	}

	/**
	 * The game is over when the last board row (row with index board.length -1) is
	 * different from empty row.
	 */
	public boolean isGameOver() {
		//looping through the last row and checking if every cell in last row is not empty
		for(int i=0; i<board[0].length; i++) {
			if(board[board.length-1][i]!=BoardCell.EMPTY) {
				return true;
			}	
		}
		return false;
	}

	public int getScore() {
		return score;
	}

	/**
	 * This method will attempt to insert a row of random BoardCell objects if the
	 * last board row (row with index board.length -1) corresponds to the empty row;
	 * otherwise no operation will take place.
	 */
	public void nextAnimationStep() {
		//looping through last row and see if every cell is not empty
		//if it is not empty nothing will occur
		for(int i=0; i<this.board[0].length; i++) {
			if(this.board[getMaxRows()-1][i] != BoardCell.EMPTY) {
				return;
			}
		}
		//shift rows down one
		for(int i=getMaxRows()-1; i>=1; i--) {
			this.board[i] = this.board[i-1].clone();
		}
		//insert random cells for empty top row
		for(int i=0;i<this.board[0].length; i++) {
			this.board[0][i]=BoardCell.getNonEmptyRandomBoardCell(random);
		}
	}

	/**
	 * This method will turn to BoardCell.EMPTY the cell selected and any adjacent
	 * surrounding cells in the vertical, horizontal, and diagonal directions that
	 * have the same color. The clearing of adjacent cells will continue as long as
	 * cells have a color that corresponds to the selected cell. Notice that the
	 * clearing process does not clear every single cell that surrounds a cell
	 * selected (only those found in the vertical, horizontal or diagonal
	 * directions).
	 * 
	 * IMPORTANT: Clearing a cell adds one point to the game's score.<br />
	 * <br />
	 * 
	 * If after processing cells, any rows in the board are empty,those rows will
	 * collapse, moving non-empty rows upward. For example, if we have the following
	 * board (an * represents an empty cell):<br />
	 * <br />
	 * RRR<br />
	 * GGG<br />
	 * YYY<br />
	 * * * *<br/>
	 * <br />
	 * then processing each cell of the second row will generate the following
	 * board<br />
	 * <br />
	 * RRR<br />
	 * YYY<br />
	 * * * *<br/>
	 * * * *<br/>
	 * <br />
	 * IMPORTANT: If the game has ended no action will take place.
	 * 
	 * 
	 */
	public void processCell(int rowIndex, int colIndex) {
		// if given rowIndex and colIndex is out of bounds 
		// or if the cell is already empty
		// or if game is already over 
		// nothing will happen
		if (rowIndex < 0 || 
				rowIndex >= getMaxRows() ||
				colIndex < 0 || 
				colIndex >= getMaxCols() ||
				getBoardCell(rowIndex, colIndex) == BoardCell.EMPTY ||
				isGameOver()) {
			return;
		}

		Color color = this.board[rowIndex][colIndex].getColor();
		int[][] visited = new int[getMaxRows()][getMaxCols()];
		recur(rowIndex, colIndex, color, visited);

		BoardCell[][] temp = new BoardCell[getMaxRows()][getMaxCols()];
		for (int i = 0; i < getMaxRows(); i++) {
			for (int j = 0; j < getMaxRows(); j++) {
				temp[i][j] = BoardCell.EMPTY;
			}
		}

		int row = 0;
		for (int i = 0; i < getMaxRows(); i++) {
			if (!isEmptyRow(i)) {
				temp[row] = this.board[i];
				row++;
			}
		}
		this.board = temp;
	}

	// recursive method used by process cell
	private void recur(int rowIndex, int colIndex, Color color, int[][] visited) {
		if (rowIndex < 0 ||
				rowIndex >= this.getMaxRows() ||
				colIndex < 0 ||
				colIndex >= this.getMaxCols() ||
				getBoardCell(rowIndex, colIndex).getColor() != color|| 
				visited[rowIndex][colIndex] == 1) {
			return;
		}

		this.board[rowIndex][colIndex] = BoardCell.EMPTY;
		visited[rowIndex][colIndex] = 1;
		this.score++;

		// recursive calls

		// horizontal and vertical
		recur(rowIndex - 1, colIndex, color, visited);
		recur(rowIndex + 1, colIndex, color, visited);
		recur(rowIndex, colIndex - 1, color, visited);
		recur(rowIndex, colIndex + 1, color, visited);

		// diagonals
		recur(rowIndex + 1, colIndex + 1, color, visited);
		recur(rowIndex - 1, colIndex - 1, color, visited);
		recur(rowIndex + 1, colIndex - 1, color, visited);
		recur(rowIndex - 1, colIndex + 1, color, visited);
	}


	private boolean isEmptyRow(int rowIndex) {
		//looping through the row and check if it is empty or not
		//if not empty, return true 
		//if empty, return true
		for (int i = 0; i < this.getMaxCols(); i++) {
			if (getBoardCell(rowIndex, i) != BoardCell.EMPTY) {
				return false;
			}
		}
		return true;
	}
}
