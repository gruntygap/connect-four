package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;

public class Main extends Application {
	
	private int columns = 6;
	
	private int rows = 5;
	
	
	
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
//			GameModel game = new GameModel(6, 7);
//			System.out.println(game);
//			game.makeMove(0, 2);
//			game.makeMove(0, 2);
//			game.makeMove(0, 2);
//			game.makeMove(1, 2);
//			game.makeMove(1, 2);
//			game.makeMove(2, 2);
//			game.makeMove(0, 1);
//			game.makeMove(1, 1);
//			game.makeMove(2, 1);
//			game.makeMove(3, 1);
//			System.out.println(game);
//			System.out.println(game.hasWon(1));
//			System.out.println(game);
			GridPane gp = new GridPane();
			
			for (int i = 0; i < columns; i ++) {
				ColumnConstraints column = new ColumnConstraints(10);
				gp.getColumnConstraints().add(column);
			}
			
			for (int i = 0; i < rows; i ++) {
				RowConstraints row = new RowConstraints(10);
				gp.getRowConstraints().add(row);
			}
			
			ToolBar tb = new ToolBar();
			
			Button reset = new Button("Reset");
			
			Label currentPlayer = new Label("Current Turn: " + "INSERT VALUE");
			
			tb.getItems().addAll(reset,currentPlayer);
			
			root.setTop(tb);
			
			for (int i = 0; i < columns; i ++) {
				for (int j = 0; j < rows; j ++) {
					Pane pane = new Pane();
					pane.setMinSize(25,25);
					pane.setPrefSize(300, 300);
					
					pane.setOnMouseReleased(e -> {
						// update model
						
						// switches turn
						currentPlayer.setText("Current turn: " + "INSERT VALUE");
						
						// check for a winner
						// if winner then display
//						Alert winnerAlert = new Alert(AlertType.INFORMATION);
//						winnerAlert.setTitle("Game Over!");
//						winnerAlert.setContentText("Player" + "INSERT VALUE" + " won!");
//						winnerAlert.showAndWait();
					});
					
					pane.getStyleClass().add("game-cell");
					gp.add(pane, i, j);
					gp.setHgrow(pane, Priority.ALWAYS);
					gp.setVgrow(pane, Priority.ALWAYS);
				}
			}
			
			
			gp.getStyleClass().add("game-board");
			
			root.setCenter(gp);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
