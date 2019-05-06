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

/**
 * Connect-4 View
 * @author grantgapinski
 * @author baileymiddendorf
 */

public class BoardGUI extends BorderPane {
	
	private GridPane gp;
	
	private ToolBar tb;
	
	private Button reset;
	
	private Label currentPlayer;
	
	private GameModel model;
	
	private boolean ai;
	
	public BoardGUI() {
		// Create objects the game is dependent on.
		this.model = new GameModel(6, 7);
		this.ai = true;
		
		// Configure GridPane
		this.gp = new GridPane();
		gp.getStyleClass().add("game-board");
		initGridPane();
		
		// Configure ToolBar
		this.tb = new ToolBar();
		this.reset = new Button("Reset");
		reset.setOnAction((event) -> {
			try {
				model = new GameModel(6, 7);
			} catch (Exception e) {
				// Add alert?
			}
		});
		this.currentPlayer = new Label("Current Turn: " + model.getTurn());
		tb.getItems().addAll(reset,currentPlayer);
		
		// Adds items this.BoardGUI which is a BorderPane
		this.setTop(tb);
		this.setCenter(gp);	
	}
	
	private void initGridPane() {
		int[][] grid = model.getGrid();
		for (int i = 0; i < grid[0].length; i ++) {
			ColumnConstraints column = new ColumnConstraints();
			gp.getColumnConstraints().add(column);
		}
		for (int i = 0; i < grid.length; i ++) {
			RowConstraints row = new RowConstraints();
			gp.getRowConstraints().add(row);
		}
		
		for (int i = 0; i < grid[0].length; i ++) {
			for (int j = 0; j < grid.length; j ++) {
				Pane pane = new Pane();
				pane.setMinSize(25,25);
				pane.setPrefSize(300, 300);
				
				pane.getStyleClass().add("game-cell");
				gp.add(pane, i, j);
				GridPane.setHgrow(pane, Priority.ALWAYS);
				GridPane.setVgrow(pane, Priority.ALWAYS);
			}
		}
//		if (turn == 1) {
//			pane.setBackground(new Background(new BackgroundFill(Color.ALICEBLUE, null, null)));
//			turn = 2;
//		} else {
//			pane.setBackground(new Background(new BackgroundFill(Color.DARKRED, null, null)));
//			turn = 1;
//			
//		}
//		pane.setOnMouseReleased(e -> {
//			// 
//			
//			
//			
//			// check for a winner
//			// if winner then display
////			Alert winnerAlert = new Alert(AlertType.INFORMATION);
////			winnerAlert.setTitle("Game Over!");
////			winnerAlert.setContentText("Player" + "INSERT VALUE" + " won!");
////			winnerAlert.showAndWait();
//		});
	}
	
	private void updateGUI() {
		// Update Turn Text
		currentPlayer.setText("Current turn: " + model.getTurn());
	}

}
