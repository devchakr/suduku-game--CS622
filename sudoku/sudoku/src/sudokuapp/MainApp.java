package sudokuapp;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;

/***
  This is the entry point of our application, here we will launch the JavaFX Application.
 */
public class MainApp extends Application {
	@Override
	public void start(Stage stage) throws Exception {

		/* Create the root element in accordance with layout.fxml file */
		Parent root = FXMLLoader.load(getClass().getResource("layout.fxml"));
		Scene scene = new Scene(root, 390, 720);
		stage.setTitle("SUDOKU");       // Title of the Window is set as "SUDOKU"
		stage.setScene(scene);
		stage.show();                   // print the stage
	}

	public static void main(String[] args) {
		launch(args);                         // It will call the start method that we have overridden
	}
}