package tests;

import static org.junit.Assert.*;

import model.BoardCell;
import model.ClearCellGame;
import model.Game;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.Arrays;
import java.util.Random;
/* The following directive executes tests in sorted order */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class StudentTests {

	@Test
	public void testNewClearCellGame() {
		Random random = new Random();
		Game game = new ClearCellGame(5,5,random,1);

		// checking the score
		assertTrue(game.getScore() == 0);
		// checking maximum cols
		assertTrue(game.getMaxCols() == 5);
		// checking maximum rows
		assertTrue(game.getMaxRows() == 5);

		// checking if all board cells are initialized as empty
		for (int i = 0; i < game.getMaxRows(); i++) {
			for (int j = 0; j < game.getMaxCols(); j++) {
				assertTrue(game.getBoardCell(i,j) == BoardCell.EMPTY);
			}
		}
	}

	@Test
	public void getMaxRowsAndCols() {
		//checking retrieval of maxRows and maxCols for big boards
		Random random = new Random();
		Game game = new ClearCellGame(500,600,random,1);
		assertTrue(game.getMaxRows()==500);
		assertTrue(game.getMaxCols()==600);
		assertFalse(game.getMaxRows()==501);
		assertFalse(game.getMaxCols()==601);
	}

	@Test
	public void testGameOver() {

		Random random = new Random();
		Game game = new ClearCellGame(10,10,random,1);
		//game should not be over since it's an empty board
		assertFalse(game.isGameOver()); 
		//fill entire board with blue
		game.setBoardWithColor(BoardCell.BLUE);

		//check status of game over;game should be over
		assertTrue(game.isGameOver());
		//when the last row is empty, game should not be over
		game.setRowWithColor(9, BoardCell.EMPTY);
		assertFalse(game.isGameOver());
		//when the last row is not empty(contains colors) 
		//game should be over
		game.setRowWithColor(9, BoardCell.RED);
		assertTrue(game.isGameOver());
	}

	@Test
	public void testNewAnimation() {
		Random random = new Random();
		Game game = new ClearCellGame(10,10,random,1);

		// initialized on the first row
		game.setRowWithColor(0, BoardCell.RED);

		// calling the method being tested
		game.nextAnimationStep();	

		// CHECK:
		// did the first row move down to the second row?
		//check if random Board cell is inserted in last row
		for (int i = 0; i < game.getMaxCols();i++) {
			assertTrue(game.getBoardCell(1,i) == BoardCell.RED);

		}
		for(int c=0; c<game.getMaxCols();c++) {
			assertTrue(game.getBoardCell(10,c)!=BoardCell.EMPTY);
		}
	}

	@Test
	public void testScore() {
		Random random = new Random();
		Game game = new ClearCellGame(5,5,random,1);
		//set board to one color 
		game.setBoardWithColor(BoardCell.BLUE); 
		//set last row empty so it's not game over
		game.setRowWithColor(4, BoardCell.EMPTY); 
		game.processCell(1,1); //this should make all the cells empty

		assertTrue(game.getScore()==20);
	}

	@Test
	public void testScore2() {
		Random random = new Random();
		Game game = new ClearCellGame(5,5,random,1);
		//test if if counts clearing of horizontal and vertical cells
		game.setBoardWithColor(BoardCell.GREEN);
		game.setRowWithColor(3, BoardCell.RED);
		game.setColWithColor(3, BoardCell.RED );
		game.setRowWithColor(4, BoardCell.EMPTY);

		game.processCell(3,0);

		assertTrue(game.getScore()==8); 
	}

	@Test
	public void testScore3() {
		Random random = new Random();
		Game game = new ClearCellGame(30,60,random,1);

		game.setBoardWithColor(BoardCell.YELLOW);
		game.setRowWithColor(29, BoardCell.EMPTY);
		game.setRowWithColor(28, BoardCell.RED);
		game.setBoardCell(27,20, BoardCell.RED); //adjacent cell
		game.setBoardCell(26,21, BoardCell.RED); //diagonal cell
		game.setBoardCell(25,22, BoardCell.RED); //diagonal cell
		game.setBoardCell(24,23, BoardCell.RED);
		game.setBoardCell(24,24, BoardCell.RED);
		game.setRowWithColor(23, BoardCell.RED);

		game.processCell(27,20);

		assertTrue(game.getScore()==125);
	}


	@Test 
	public void testGetAndSetCell() {
		Random random = new Random();
		Game game = new ClearCellGame(40,65,random,1);
		assertTrue(game.getBoardCell(10,35)== BoardCell.EMPTY);

		game.setBoardWithColor(BoardCell.YELLOW);
		for(int r=0; r< game.getMaxRows(); r++) {
			for(int c=0; c< game.getMaxCols();c++) {
				assertTrue(game.getBoardCell(r,c)==BoardCell.YELLOW);
			}
		}
		assertTrue(game.getBoardCell(39,64)==BoardCell.YELLOW);

		game.setBoardWithColor(BoardCell.GREEN);
		for(int r=0; r< game.getMaxRows(); r++) {
			for(int c=0; c< game.getMaxCols();c++) {
				assertTrue(game.getBoardCell(r,c)==BoardCell.GREEN);
			}
		}
		assertTrue(game.getBoardCell(39,64)==BoardCell.GREEN);

		game.setBoardWithColor(BoardCell.RED);
		for(int r=0; r< game.getMaxRows(); r++) {
			for(int c=0; c< game.getMaxCols();c++) {
				assertTrue(game.getBoardCell(r,c)==BoardCell.RED);
			}
		}
		assertTrue(game.getBoardCell(39,64)==BoardCell.RED);

		game.setBoardWithColor(BoardCell.BLUE);
		for(int r=0; r< game.getMaxRows(); r++) {
			for(int c=0; c< game.getMaxCols();c++) {
				assertTrue(game.getBoardCell(r,c)==BoardCell.BLUE);
			}
		}
		assertTrue(game.getBoardCell(39,64)==BoardCell.BLUE);
	}


	@Test
	public void testsetColWithColor() {
		Random random = new Random();
		Game game = new ClearCellGame(10,10,random,1);

		game.setBoardWithColor(BoardCell.YELLOW);
		for(int r=0; r<game.getMaxRows();r++) {
			for(int c=0; c< game.getMaxCols(); c++) {
				game.setColWithColor(c, BoardCell.BLUE);
				assertTrue(game.getBoardCell(r, c)==BoardCell.BLUE);
			}
		}
	}

	@Test
	public void testsetRowWithColor() {
		Random random = new Random();
		Game game = new ClearCellGame(10,10,random,1);

		game.setBoardWithColor(BoardCell.GREEN);
		assertTrue(game.getBoardCell(2,7)==BoardCell.GREEN);
		for(int r=0; r<game.getMaxRows();r++) {
			for(int c=0; c< game.getMaxCols(); c++) {
				game.setRowWithColor(r,BoardCell.RED);
				assertTrue(game.getBoardCell(r,c)==BoardCell.RED);
			}
		}
	}

	@Test
	public void testProcessCellAndCollapseMech() {
		//test if it can shift up one row
		Random random = new Random();
		Game game = new ClearCellGame(10,10,random,1);

		game.setBoardWithColor(BoardCell.BLUE);
		//set last two rows empty so it's not game over
		game.setRowWithColor(8, BoardCell.EMPTY); 
		game.setRowWithColor(9, BoardCell.EMPTY); 
		assertFalse(game.isGameOver()); 
		game.setRowWithColor(6, BoardCell.RED);
		game.setRowWithColor(7, BoardCell.GREEN);

		game.processCell(6,0);
		//row 7 should shift up 
		for(int c=0; c<game.getMaxCols(); c++) {
			assertTrue(game.getBoardCell(6,c)==BoardCell.GREEN);
		}

	}

	@Test
	public void testProcessCellAndCollapseMultiple() {
		//test if it can collapse multiple rows
		Random random = new Random();
		Game game = new ClearCellGame(10,10,random,1);

		game.setBoardWithColor(BoardCell.BLUE);
		//set last two rows empty so it's not game over
		game.setRowWithColor(8, BoardCell.EMPTY); 
		game.setRowWithColor(9, BoardCell.EMPTY); 
		assertFalse(game.isGameOver()); 
		game.setRowWithColor(4, BoardCell.YELLOW);
		game.setRowWithColor(5, BoardCell.RED);
		game.setRowWithColor(6, BoardCell.RED);
		game.setRowWithColor(7, BoardCell.GREEN);

		String output = "Before processCell\n\n";
		output += getBoardStr(game);
		game.processCell(6,0);
		output += "\nAfter processCell\n";
		output += getBoardStr(game);
		System.out.print(output);

		for(int c=0; c<game.getMaxCols(); c++) {
			assertTrue(game.getBoardCell(4,c)==BoardCell.YELLOW);
			assertTrue(game.getBoardCell(5,c)==BoardCell.GREEN);
			assertTrue(game.getBoardCell(6,c) ==BoardCell.EMPTY);

		}
		assertTrue(game.getScore()==20); 
	}

	@Test
	public void testProcessCellAfterGameIsOver() {
		Random random = new Random();
		Game game = new ClearCellGame(10,10,random,1);

		game.setBoardWithColor(BoardCell.BLUE);
		game.setRowWithColor(8, BoardCell.RED);
		game.setRowWithColor(9, BoardCell.GREEN);
		assertTrue(game.isGameOver()); //game should be over
		//the boards should match before and after processing
		String output = "Before processCell\n\n";
		output += getBoardStr(game);
		game.processCell(8,0);
		game.processCell(9,6);
		output += "\nAfter processCell\n";
		output += getBoardStr(game);
		//you should not be able to clear any cells after game is over
		for(int c=0; c<game.getMaxCols(); c++) {
			assertTrue(game.getBoardCell(7,c)==BoardCell.BLUE);
			assertTrue(game.getBoardCell(8,c)==BoardCell.RED);
			assertTrue(game.getBoardCell(9,c) ==BoardCell.GREEN);

		}
		assertTrue(game.getScore()==0);
		System.out.println(output);
	}

	@Test
	public void testProcessCellOutofBounds() {
		Random random = new Random();
		Game game = new ClearCellGame(10,10,random,1);

		game.setBoardWithColor(BoardCell.BLUE);
		game.setRowWithColor(7, BoardCell.YELLOW);
		game.setRowWithColor(8, BoardCell.RED);
		game.setRowWithColor(9, BoardCell.EMPTY);
		assertFalse(game.isGameOver()); //game should not be over

		//before and after processing boards should match
		String output = "Before processCell\n\n";
		output += getBoardStr(game);
		game.processCell(8,11); //col index greater than maxCols
		game.processCell(7,-1); //colIndex less than 0
		game.processCell(-6,6); //rowIndex less than 0
		game.processCell(9,3);  //if it is an empty row
		game.processCell(10,0); //if row index is equal to maxRows
		game.processCell(11,9); //if row index is greater than maxRows
		output += "\nAfter processCell\n";
		output += getBoardStr(game);

		assertTrue(game.getBoardCell(8,11)==null);
		assertTrue(game.getBoardCell(7,-1)==null);
		assertTrue(game.getBoardCell(-6,6)== null);
		assertTrue(game.getBoardCell(10,0)==null);
		assertTrue(game.getBoardCell(11,9)==null);

		//all of the above calls to processCell should not have any effect 
		for(int c=0; c<game.getMaxCols(); c++) {
			assertTrue(game.getBoardCell(8,c) ==BoardCell.RED);
			assertTrue(game.getBoardCell(7,c) ==BoardCell.YELLOW);
			assertTrue(game.getBoardCell(6,c)==BoardCell.BLUE);
			assertTrue(game.getBoardCell(9,c)==BoardCell.EMPTY);
			assertTrue(game.getBoardCell(11,c)==null);
			assertTrue(game.getBoardCell(10,c)==null);
		}
		assertTrue(game.getScore()==0);
	}

	/* Support methods */
	private static String getBoardStr(Game game) {
		int maxRows = game.getMaxRows(), maxCols = game.getMaxCols();
		String output = "Board(Rows: " + maxRows + ", Columns: " + maxCols + ")\n";

		for (int row = 0; row < maxRows; row++) {
			for (int col = 0; col < maxCols; col++) {
				output += game.getBoardCell(row, col).getName();
			}
			output += "\n";
		}

		return output;
	}
}

