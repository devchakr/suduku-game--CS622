# Suduku-Game--CS622
suduku game 
Rules of suduku game 
81 grids 1 to 9 
Each row have to fill in any order and no number should be repeated
Layout.fxml provides the skeletal structure of our application, here we define the components of our app like different buttons and grid and postitions it will take.

application.css file is responsible for beautification of the components we have defined in the layout.fxml.
For eg, We have added background color in this file.
MainApp.java is the entrypoint from where we launch our application, it is a standard code that is present in general in all JavaFX applications.

Controller.java is the class that is used for event handling on all the components we defined in layout.fxml. whenever a user will interact with the button or grid, an event is emitted, and logic for handling those events are written here. 
It also calls SudokuArena.java class, which is responsible for handling the backend of our Sudoku board, like in backend the board is represented as a 9x9 integer matrix.

Finally, SudokuGenerator.java is the class that will return random sudoku patterns of different difficulty on demand, and also it has a recursive method that can find the solution of a given Sudoku board, that we are printing on the console.
