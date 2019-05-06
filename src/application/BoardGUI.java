package application;

import java.util.ArrayList;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
//import javafx.scene.control.Alert.AlertType;
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
// TODO: COMMENT ALL FILES
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
	
	private Label aiStatus;
	
	private ArrayList<ArrayList<Pane>> paneGrid;
	
	private GameModel model;
	
	private boolean ai;
	
	private AIPlayer aiPlayer;
	
	public BoardGUI() {
		// Create objects the game is dependent on.
		this.model = new GameModel(6, 7);
		this.ai = true;
		this.aiPlayer = new AIPlayer(9);
		
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
				updateGUI();
				aiMove();
			} catch (Exception e) {
				// Add alert?
			}
		});
		this.currentPlayer = new Label("Current Turn: " + model.getTurn() + ";");
		this.aiStatus = new Label("AI Status:");
		tb.getItems().addAll(reset,currentPlayer,aiStatus);
		
		// Adds items this.BoardGUI which is a BorderPane
		this.setTop(tb);
		this.setCenter(gp);
		
		// If AI is true, start moves
		aiMove();
	}
	
	private void aiMove() {
		if (ai) {
			int value = aiPlayer.getValueOfNextMove(model.getGrid());
			this.aiStatus.setText("AI Status: " + value);
			int move = aiPlayer.getNextColumn();
			try {
				model.makeMove(move, model.getTurn());
			} catch (Exception e) {
				Alert badAlert = new Alert(AlertType.WARNING);
				badAlert.setTitle("AI ERROR");
				e.printStackTrace();
				badAlert.setContentText("The AI messed up?: " + e.getMessage());
				badAlert.showAndWait();
			}
			updateGUI();
		}
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
		
		// Creates paneGrid with all the Pane(s)
		paneGrid = new ArrayList<ArrayList<Pane>>();
		for (int i = 0; i < grid[0].length; i++) {
			paneGrid.add(new ArrayList<Pane>());
			for (int j = 0; j < grid.length; j++) {
				Pane pane = new Pane();
				// Handler
				int effectivelyFinalI = i; 
				pane.setOnMouseReleased(e -> {
					// Player 2 Makes a move
					try {
						model.makeMove(effectivelyFinalI, model.getTurn());
					} catch (Exception err) {
						Alert badAlert = new Alert(AlertType.WARNING);
						badAlert.setTitle("Bad Move!");
						badAlert.setContentText(err.getMessage());
						badAlert.showAndWait();
					}
					updateGUI();
					// Follow up move from the AI
					aiMove();
				});
				pane.setMinSize(25,25);
				pane.setPrefSize(300, 300);
				pane.getStyleClass().add("game-cell");
				paneGrid.get(i).add(pane);
				GridPane.setHgrow(pane, Priority.ALWAYS);
				GridPane.setVgrow(pane, Priority.ALWAYS);
			}
		}
		
		// add each Pane from paneGrid, to GridPane gp
		for (int i = 0; i < grid[0].length; i++) {
			for (int j = 0; j < grid.length; j++) {
				gp.add(paneGrid.get(i).get(j), i, j);
			}
		}
	}
	
	private void updateGUI() {
		// Update Turn Text
		currentPlayer.setText("Current Turn: " + model.getTurn() + ";");
		int[][] grid = model.getGrid();
		for (int i = 0; i < grid[0].length; i++) {
			for (int j = 0; j < grid.length; j++) {
				if (grid[j][i] == 1) {
					paneGrid.get(i).get(j).setBackground(new Background(new BackgroundFill(Color.ALICEBLUE, null, null)));
				} else if (grid[j][i] == 2) {
					paneGrid.get(i).get(j).setBackground(new Background(new BackgroundFill(Color.DARKRED, null, null)));
				} else {
					paneGrid.get(i).get(j).setBackground(null);
				}
			}
		}
		if (model.hasWon(model.getTurn())) {
			Alert winnerAlert = new Alert(AlertType.INFORMATION);
			winnerAlert.setTitle("Game Over!");
			winnerAlert.setContentText("Player " + model.getTurn() + " won!");
			winnerAlert.showAndWait();
		} else if (model.boardFull()) {
			Alert winnerAlert = new Alert(AlertType.INFORMATION);
			winnerAlert.setTitle("Game Over!");
			winnerAlert.setContentText("NO ONE WON");
			winnerAlert.showAndWait();
		}
 	}

}
