package application;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;

public class boardGUI extends BorderPane {

	private int columns = 7;
	
	private int rows = 6;
	
	private int turn = 1;
	
	public boardGUI() {
		GridPane gp = new GridPane();
		
		for (int i = 0; i < columns; i ++) {
			ColumnConstraints column = new ColumnConstraints();
			gp.getColumnConstraints().add(column);
		}
		
		for (int i = 0; i < rows; i ++) {
			RowConstraints row = new RowConstraints();
			gp.getRowConstraints().add(row);
		}
		
		ToolBar tb = new ToolBar();
		
		Button reset = new Button("Reset");
		
		Label currentPlayer = new Label("Current Turn: " + turn);
		
		tb.getItems().addAll(reset,currentPlayer);
		
		this.setTop(tb);
		
		for (int i = 0; i < columns; i ++) {
			for (int j = 0; j < rows; j ++) {
				Pane pane = new Pane();
				pane.setMinSize(25,25);
				pane.setPrefSize(300, 300);
				
				pane.setOnMouseReleased(e -> {
					// update model by placing piece in spot
					
					// switches turn
					currentPlayer.setText("Current turn: " + turn);
					if (turn == 1) {
						pane.setBackground(new Background(new BackgroundFill(Color.ALICEBLUE, null, null)));
						turn = 2;
					} else {
						pane.setBackground(new Background(new BackgroundFill(Color.DARKRED, null, null)));
						turn = 1;
						
					}
					
					// check for a winner
					// if winner then display
//					Alert winnerAlert = new Alert(AlertType.INFORMATION);
//					winnerAlert.setTitle("Game Over!");
//					winnerAlert.setContentText("Player" + "INSERT VALUE" + " won!");
//					winnerAlert.showAndWait();
				});
				
				pane.getStyleClass().add("game-cell");
				gp.add(pane, i, j);
				gp.setHgrow(pane, Priority.ALWAYS);
				gp.setVgrow(pane, Priority.ALWAYS);
			}
		}
		
		
		gp.getStyleClass().add("game-board");
		
		this.setCenter(gp);
		
		
	}

}
