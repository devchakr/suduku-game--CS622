/*package application;

public class GameBoard {

}
*/

package sudokuapp;

import java.util.Arrays;

/***
 * The SudokuArena class contains three arrays, representing solution state, initial state, and player's state.
 */
public class SudokuArena {
	/* 9x9 Matrix that stores the numbers that are initially present on the Sudoku board (user cannot edit these numbers) */
	private int[][] init_state;
	/* 9x9 Matrix that stores the present state of the Sudoku board */
	private int[][] user_state;
	/* 9x9 Matrix that stores the final solution to the Sudoku board */
	private int[][] sol_state;
	
	// Constructor method
	public SudokuArena(int emptyCells) {
        SudokuGenerator sudoku = new SudokuGenerator(emptyCells);
        sudoku.fillValues();
		sol_state = sudoku.returnSudokuSolution();
		//printSudoku(sol_state);
		printSudokuArenaUsingJavaStreams(sol_state);
		init_state = sudoku.returnSudokuMatrix();
		user_state = new int[9][9];
	}

	// Will print the whole sudoku board on console using Java Streams instead of nested loops.
	public void printSudokuArenaUsingJavaStreams(int mat[][])
    {
		System.out.println();
		// For the matrix mat, we take out each row and for each row we take out each element and add space and print it.
		// We used Java streams to achieve that instead of nested loops.
		Arrays.stream(mat).forEach((row) -> {
            Arrays.stream(row).forEach((col) -> System.out.print(col + " "));
            System.out.println();
        });
		// after printing each row, we are adding an empty line.
		System.out.println();
    }

	// Getter method for sol_state.
	public int[][] getSolutionState() {
		return sol_state;
	}

	// Getter method for init_state.
	public int[][] getInitialState() {
		return init_state;
	}

	// Getter method for user_state.
	public int[][] getUserState() {
		return user_state;
	}

	// This method will change the value in the row, col position with the given value val.
	public void modifyUser(int val, int row, int col) {
		// We will only put the value if it wasn't pre-populated in the initial state.
		if (init_state[row][col] == 0) {
			user_state[row][col] = val;
		}
	}
	
	// This method returns true, only if the current_state matches with sol_state.
	public boolean checkSuccess() {
		// Create a combined matrix using initial_state and user_state.
		int[][] merged_state = new int[9][9];
		for(int row = 0; row < 9; row++) {
			for(int col = 0; col <9; col++) {
				if(init_state[row][col]!=0) {
					merged_state[row][col] = init_state[row][col];
				} else {
					merged_state[row][col] = user_state[row][col];
				}
			}
		}
		
		// We will compare the sol_state and current state of the board cell by cell. 
		for(int row=0; row<9 ;row++) {
			for(int col=0; col<9 ; col++) {
				if(merged_state[row][col] != sol_state[row][col]) {
					return false;                 // returns false, if at any cell the values don't match.
				}
			}
		}
		
		return true;
	}
}