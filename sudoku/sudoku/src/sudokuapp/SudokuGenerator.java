package sudokuapp;
/*
 * This class will take an input of K and generate a 9x9 Sudoku matrix with random pattern and with K empty cells.
 */

public class SudokuGenerator
{
    private int[] solution[];       // solved Sudoku pattern
	private int[] matrix[];         // unsolved Sudoku pattern that will be returned
    private int K;                  // number of missing cells on the Sudoku board
 
    // Constructor method
    SudokuGenerator(int K)
    {
        this.K = K;
        matrix = new int[9][9];             // initialise the sudoku matrix 
        solution = new int[9][9];
    }
    
    // This method creates the random unsolved Sudoku pattern.
    public void fillValues()
    {
        // Fill the diagonal 3x3 clusters randomly.
        initialDiagonalEntry();
 
        // Solve the Sudoku using a recursive method.
        solveTheBoard(0, 3);
        
        for (int row=0; row<9 ; row++)
        {
        	for(int col=0; col<9; col++)
        	{
        		solution[row][col] = matrix[row][col];
        	}
        }
        
        // Emptying K cells randomly in the Sudoku board.
        randomlyRemoveKCells();
        
        // After these steps we will have an unsolved Sudoku board pattern to be solved by the user.
    }
 
    // Fill 3x3 matrices cluster across the diagonal of the bigger 9x9 matrix
    protected void initialDiagonalEntry()
    {
        for (int row = 0; row<9; row=row+3)     // i will take value of 0,3,6.
            fillCluster(row, row);
    }
 
    // It fills the 3x3 cluster of cells with (row, col) as top-left coordinate.
    private void fillCluster(int row, int col)
    {
        int cellValue;
        for (int i=0; i<3; i++)
        {
            for (int j=0; j<3; j++)
            {
                do
                {
                    cellValue = randomNumberGenerator(9);
                }
                while (!isSafeInCluster(row, col, cellValue));
                matrix[row+i][col+j] = cellValue;
            }
        }
    }
 
    // Returns true only when the given value is not present in the given row, else false.
    private boolean isSafeInRow(int row, int val)
    {
        for (int col = 0; col<9; col++)
           if (matrix[row][col] == val)
                return false;
        return true;
    }
 
    // Returns true only when the given value is not present in the given column, else false.
    private boolean isSafeInCol(int col, int val)
    {
        for (int row = 0; row<9; row++)
            if (matrix[row][col] == val)
                return false;
        return true;
    }
    
    // Returns true only when the given value is not present in the 3x3 cluster starts with rowZero and colZero, else false.
    private boolean isSafeInCluster(int rowZero, int colZero, int val)
    {
        for (int row = 0; row<3; row++)
            for (int col = 0; col<3; col++)
                if (matrix[rowZero+row][colZero+col]==val)
                    return false;
 
        return true;
    }
    
    // Returns true only when it's safe to put the number in the row, column, and also in the 3x3 cluster.
    private boolean isSafeInCell(int i,int j,int num)
    {
        boolean safeInRow = isSafeInRow(i, num);
        boolean safeInCol = isSafeInCol(j, num);
        boolean safeInCluster = isSafeInCluster(i-i%3, j-j%3, num);
    	return (safeInRow && safeInCol && safeInCluster);
    }
    
    // This function will generate integer random numbers between 1 to n (both inclusive). 
    private int randomNumberGenerator(int n)
    {
        double tmp = Math.random()*n+1;
    	return (int) Math.floor(tmp);
    }
 
    // This is a recursive method that will solve the sudoku problem.
    protected boolean solveTheBoard(int row, int col)
    {
        if (col>=9 && row<8)
        {
            row = row + 1;
            col = 0;
        }
        if (row>=9 && col>=9)
            return true;
 
        if (row < 3)
        {
            if (col < 3)
                col = 3;
        }
        else if (row < 6)
        {
            if (col==(int)(row/3)*3)
                col =  col + 3;
        }
        else
        {
            if (col == 6)
            {
                row = row + 1;
                col = 0;
                if (row>=9)
                    return true;
            }
        }
 
        for (int value=1; value<=9; value++)
        {
            if (isSafeInCell(row, col, value))           // check all the conditions that make a value legit in a cell.
            {
                matrix[row][col] = value;
                if (solveTheBoard(row, col+1))         // recursive call to itself
                    return true;
 
                matrix[row][col] = 0;
            }
        }
        return false;
    }
 
    // Chooses K cells at random, and sets it to zero in the Sudoku matrix
    protected void randomlyRemoveKCells()
    {
        int counter = K;
        
        // This loop will continue to run till we delete all the K cells.
        while (counter != 0)
        {
            int gridID = randomNumberGenerator(9*9)-1;   // gridID is the number associated with each cell in row-major form.
            
            // Computing coordinates for the gridID.
            int row = (gridID/9);
            int col = gridID%9;
            
            if (col != 0)
                col = col - 1;
 
            if (matrix[row][col] != 0)                 // if the cell is empty then we won't reduce the counter and we'll continue.
            {
                counter--;
                matrix[row][col] = 0;
            }
        }
    }
    
    //returns the unsolved Sudoku pattern 
    public int[][] returnSudokuMatrix()
    {
    	return matrix;
    }
   
    //Returns the solution of the sudoku pattern 
    public int[][] returnSudokuSolution()
    {
    	return solution;
    }
}

