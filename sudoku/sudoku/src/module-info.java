module sudoku {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	
	opens sudokuapp to javafx.graphics, javafx.fxml;
}
