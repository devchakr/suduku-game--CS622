package sudokuapp;

/***
 * This Controller class is where the events bound to the elements placed in our layout.fxml file is implemented.
 * Controller class will execute the overridden "initialize" method that comes with Initializable class after the elements in Controller class is loaded.
 */

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.IntStream;

import javafx.event.EventHandler;

import javafx.scene.text.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class Controller implements Initializable {

	@FXML 
	private Canvas canvas;
	
	
	// Variable name should be same as Button ID mentioned in FXML file.
	// @FXML Annotation will link this button object with the button element in FXML class.
	@FXML private Button one;     @FXML private Button two;     @FXML private Button three;    
	@FXML private Button four;    @FXML private Button five;    @FXML private Button six;
	@FXML private Button seven;   @FXML private Button eight;   @FXML private Button nine;
	
	@FXML private Button easy; @FXML private Button medium; @FXML private Button hard;

	@FXML 
	private Button reset;

	private SudokuArena sudokuarena;       // This variable will store the SudokuArena backend matrix
	
	// when it's easy, we will only keep 10 cells as empty, for medium 30 cells and for hard 50 cells as empty.
	private int difficulty = 0;            // For easy setup -- difficulty=10, medium -- difficulty=30, hard -- difficulty=50 
	
	// Coordinates where player will fill the value
	private int user_selected_row;
	private int user_selected_col;

	/***
	 * After the elements in layout.fxml is loaded in the Stage, the initialize method of Controller class is called.
	 */
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		if(difficulty == 0)
		{
			resetAsMedium();                 // When we start the application, the Sudoku is by default set as Medium.
		}
		sudokuarena = new SudokuArena(difficulty);              // A Sudoku board is created.
		
		GraphicsContext ctx = canvas.getGraphicsContext2D();    // Graphics context from canvas
		
		user_selected_row = 0;
		user_selected_col = 0;                 // default player selected cell integers to 0
		
		drawOnCanvas(ctx);                  // Canvas is like a drawing board, we will draw on that using this function.
	}

	// This method is used to draw on canvas as a drawing board.
	public void drawOnCanvas(GraphicsContext ctx) {

		ctx.clearRect(0, 0, 360, 360);
		
		ctx.setFill(Color.WHITE);
		ctx.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
		ctx.fillText("Sudoku For All", 100, 380);
		
		// Used Java Integer Streams to loop over two dimensions instead of nested loops. 
		IntStream.range(0,9).forEach((row) -> {
			IntStream.range(0,9).forEach((col) -> {
				int position_x = col * 40 + 2;
				int position_y = row * 40 + 2;
				
				// Width is now 36 pixels, given we have an offset of 2 pixels on both sides.
				int width = 36;
				ctx.setFill(Color.WHITE);
				
				// Grid cells are drawn of width 36x36 pixels.
				ctx.fillRoundRect(position_x, position_y, width, width, 1, 1);
			});
        });
		
		

		// When we select a cell, that cell will be highlighted by Blue.
		ctx.setStroke(Color.BLUE);
		ctx.setLineWidth(3);
		ctx.strokeRoundRect(user_selected_col * 40 + 2, user_selected_row * 40 + 2, 36, 36, 8, 8);

		int[][] initial = sudokuarena.getInitialState();          // Get the initial state of our Sudoku board.
		// Used Java Integer Streams to loop over two dimensions instead of nested loops. 
		IntStream.range(0,9).forEach((row) -> {
			IntStream.range(0,9).forEach((col) -> {
				// Computing coordinates of the box digit similar to the grid, with padding set as 15 and 25.
				int position_x = col * 40 + 15;
				int position_y = row * 40 + 25;
			
				// Draw all the non-zero entries from our initial Sudoku matrix on our canvas.
				if(initial[row][col]!=0) {
					ctx.setFill(Color.BLACK);
					ctx.setFont(new Font(20));
					ctx.fillText(initial[row][col] + "", position_x, position_y);
				}
			});
        });
		
		// Draw the current state in the canvas.
		int[][] user = sudokuarena.getUserState();
		// Used Java Integer Streams to loop over two dimensions instead of nested loops. 
		IntStream.range(0,9).forEach((row) -> {
			IntStream.range(0,9).forEach((col) -> {
				int position_y = row * 40 + 25;
				int position_x = col * 40 + 15;
				if(user[row][col]!=0) {
					ctx.setFill(Color.PURPLE);
					ctx.setFont(new Font(20));
					ctx.fillText(user[row][col] + "", position_x, position_y);
				}
			});
        });


		// Everytime when a player enters the number, we check for the Success of the sudokuboard, only when it is solved we return a congratulatory message. 
		if(sudokuarena.checkSuccess() == true) {
			ctx.clearRect(0, 0, 450, 450);       // clear the canvas
			ctx.setFill(Color.WHITE);
			ctx.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
			ctx.fillText("!!! Congratulations !!!", 60, 250);
		}
	}

	public void whenCanvasIsClicked() {
		canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				int coordinate_x = (int) event.getX();
				int coordinate_y = (int) event.getY();

				// we will convert the coordinates into (row, col).
				user_selected_row = (int) (coordinate_y / 40);
				user_selected_col = (int) (coordinate_x / 40);

				// We will redraw the canvas with the Player's entry
				drawOnCanvas(canvas.getGraphicsContext2D());
			}
		});
	}

	/***
	 * Similar methods to handle the events transmitted via the buttons.
	 */
	public void whenOneIsPressed() {
		
		// In the backend sudoku matrix, we put the value 1 at (player_selected_row, player_selected_col)
		sudokuarena.modifyUser(1, user_selected_row, user_selected_col);
		// redraw the canvas
		drawOnCanvas(canvas.getGraphicsContext2D()); 
	} 
	
	public void whenTwoIsPressed() {
		// In the backend sudoku matrix, we put the value 2 at (player_selected_row, player_selected_col)
		sudokuarena.modifyUser(2, user_selected_row, user_selected_col);
		// redraw the canvas
		drawOnCanvas(canvas.getGraphicsContext2D());
	}
	
	public void whenThreeIsPressed() {
		// In the backend sudoku matrix, we put the value 3 at (player_selected_row, player_selected_col)
		sudokuarena.modifyUser(3, user_selected_row, user_selected_col);
		// redraw the canvas
		drawOnCanvas(canvas.getGraphicsContext2D());
	}

	public void whenFourIsPressed() {
		// In the backend sudoku matrix, we put the value 4 at (player_selected_row, player_selected_col)
		sudokuarena.modifyUser(4, user_selected_row, user_selected_col);
		// redraw the canvas
		drawOnCanvas(canvas.getGraphicsContext2D());
	}

	public void whenFiveIsPressed() {
		// In the backend sudoku matrix, we put the value 5 at (player_selected_row, player_selected_col)
		sudokuarena.modifyUser(5, user_selected_row, user_selected_col);
		// redraw the canvas
		drawOnCanvas(canvas.getGraphicsContext2D());
	}

	public void whenSixIsPressed() {
		// In the backend sudoku matrix, we put the value 6 at (player_selected_row, player_selected_col)
		sudokuarena.modifyUser(6, user_selected_row, user_selected_col);
		// redraw the canvas
		drawOnCanvas(canvas.getGraphicsContext2D());
	}

	public void whenSevenIsPressed() {
		// In the backend sudoku matrix, we put the value 7 at (player_selected_row, player_selected_col)
		sudokuarena.modifyUser(7, user_selected_row, user_selected_col);
		// redraw the canvas
		drawOnCanvas(canvas.getGraphicsContext2D());
	}

	public void whenEightIsPressed() {
		// In the backend sudoku matrix, we put the value 8 at (player_selected_row, player_selected_col)
		sudokuarena.modifyUser(8, user_selected_row, user_selected_col);
		// redraw the canvas
		drawOnCanvas(canvas.getGraphicsContext2D());
	}

	public void whenNineIsPressed() {
		// In the backend sudoku matrix, we put the value 9 at (player_selected_row, player_selected_col)
		sudokuarena.modifyUser(9, user_selected_row, user_selected_col);
		// redraw the canvas
		drawOnCanvas(canvas.getGraphicsContext2D());
	}
	
	// Based on the difficulty, one of the following function is called.
	public void resetAsEasy() {
		//If current difficulty is medium. Then change the color of medium button to white.
		if(difficulty == 30) {
			medium.setStyle("-fx-background-color: #FFFFFF;");
		}
		//If current difficulty is hard. Then change the color of hard button to white. 
		else if(difficulty == 50) {
			hard.setStyle("-fx-background-color: #FFFFFF;");
		}//Set difficulty to easy and the color of easy button is set to green. 
		difficulty=10;
		easy.setStyle("-fx-background-color: #00FF00;");
		this.initialize(null, null);
	}
	
	
	public void resetAsMedium() { 
		//If current difficulty is easy. Then change the color of easy button to white. 
		if(difficulty == 10) {
			easy.setStyle("-fx-background-color: #FFFFFF;");
		}//If current difficulty is Medium. Then change the color of Medium button to white. 
		else if(difficulty == 50) {
			hard.setStyle("-fx-background-color: #FFFFFF;");
		}//Set difficulty to medium and the color of medium button is set to orange. 
		difficulty=30;
		medium.setStyle("-fx-background-color: #FFA500;");
		this.initialize(null, null);
	}
	
	public void resetAsHard() {
		//If current difficulty is easy. Then change the color of easy button to white.
		if(difficulty == 10) {
			easy.setStyle("-fx-background-color: #FFFFFF;");
		}//If current difficulty is Medium. Then change the color of Medium button to white.
		else if(difficulty ==30) { 
			medium.setStyle("-fx-background-color: #FFFFFF;");
		}//Set difficulty to hard and the color of hard button is set to Red.
		difficulty=50;
		hard.setStyle("-fx-background-color: #FF0000;");
		this.initialize(null, null);
	}
	
	// It will reset the board and generate a new pattern with the same difficulty.
	public void reset() {
		if(difficulty == 10) {
			resetAsEasy();
		}
		else if(difficulty == 30) {
			resetAsMedium();
		}
		else if(difficulty == 50) {
			resetAsHard();
		}
	}
}
