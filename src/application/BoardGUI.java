package application;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
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
	
	/** The Grid which is center of the BorderPane, holds the pieces for the game */
	private GridPane gp;
	
	/** The toolbar for reset button and more */
	private ToolBar tb;
	
	/** The reset button */
	private Button reset;
	
	/** A button to control if the AI plays or not */
	private Button aiPlay;
	
	/** The label signifying the current player */
	private Label currentPlayer;
	
	/** The Status of the AI, which tells the estimated value of the future AI moves */
	private Label aiStatus;
	
	/** The ArrayList of panes which symbolize the board */
	private ArrayList<ArrayList<Pane>> paneGrid;
	
	/** The GameModel, which is the game */
	private GameModel model;
	
	/** The bool to signify if the ai is going to play */
	private boolean ai;
	
	/** The actual AI player object */
	private AIPlayer aiPlayer;
	
	/** The AI's/Player 1's color */
	private ColorPicker colorPicker1;
	
	/** Player 2's color */
	private ColorPicker colorPicker2;
	
	/**
	 * The Constructor for the BoardGUI
	 */
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
		
		this.aiPlay = new Button("Disable AI");
		aiPlay.setOnAction((event) -> {
			try {
				model = new GameModel(6, 7);
				updateGUI();
				if (this. ai == true) {
					this.ai = false;
					this.aiPlay.setText("Enable AI");
				} else {
					this.ai = true;
					aiMove();
					this.aiPlay.setText("Disable AI");
				}
			} catch (Exception e) {
				Alert errorAlert = new Alert(AlertType.ERROR);
				errorAlert.setTitle("Error");
				errorAlert.setContentText("Model Creation Failed. Restart Program");
				errorAlert.showAndWait();
			}
		});
		
		this.reset = new Button("Reset");
		reset.setOnAction((event) -> {
			try {
				disablePaneHandlers(false);
				model = new GameModel(6, 7);
				colorPicker1.setValue(Color.RED);
				colorPicker2.setValue(Color.YELLOW);
				updateGUI();
				if (this.ai == true) {
					aiMove();
				}
			} catch (Exception e) {
				Alert errorAlert = new Alert(AlertType.ERROR);
				errorAlert.setTitle("Reset Error");
				errorAlert.setContentText("Reset Failed. Restart Program");
				errorAlert.showAndWait();
			}
		});
		this.currentPlayer = new Label("Current Turn: " + model.getTurn() + ";");
		this.aiStatus = new Label("AI Status:");
		
		// Block of code below adds the color selectors to a ComboBox which is then added to the Toolbar
		colorPicker1 = new ColorPicker(Color.RED);
		colorPicker1.setOnAction((event) -> {
			updateGUI();
		});
	
		colorPicker2 = new ColorPicker(Color.YELLOW);
		colorPicker2.setOnAction((event) -> {
			updateGUI();
		});
		
		ObservableList<ColorPicker> colorOptions = 
				FXCollections.observableArrayList(colorPicker1, colorPicker2);
				
		ComboBox<Object> cb = new ComboBox<Object>();
		cb.getItems().add("Select Player Colors");
		cb.getItems().addAll(colorOptions);
		
		cb.getSelectionModel().selectFirst();
		tb.getItems().addAll(aiPlay, cb, reset,currentPlayer,aiStatus);
		
		// Adds items this.BoardGUI which is a BorderPane
		this.setTop(tb);
		this.setCenter(gp);
		
		// If AI is true, start moves
		if (ai == true) {
			aiMove();
		}
	}
	
	/**
	 * The Method used for the AI to make its move
	 */
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
	
	/**
	 * The method to initialize the GridPane
	 */
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
						pane.setDisable(true);
						model.makeMove(effectivelyFinalI, model.getTurn());
						pane.setDisable(false);
					} catch (Exception err) {
						Alert badAlert = new Alert(AlertType.WARNING);
						badAlert.setTitle("Bad Move!");
						badAlert.setContentText(err.getMessage());
						badAlert.showAndWait();
					}
					updateGUI();
					// Follow up move from the AI
					if (this.ai == true) {
						aiMove();
					}
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
	
	private void disablePaneHandlers(boolean val) {
		for (int i = 0; i < paneGrid.size(); i++) {
			for (int j = 0; j < paneGrid.get(i).size(); j++) {
				paneGrid.get(i).get(j).setDisable(val);
			}
		}
	}
	
	/**
	 * The method that updates the grid, as well as labels.
	 */
	private void updateGUI() {
		// Update Turn Text
		currentPlayer.setText("Current Turn: " + model.getTurn() + ";");
		int[][] grid = model.getGrid();
		for (int i = 0; i < grid[0].length; i++) {
			for (int j = 0; j < grid.length; j++) {
				if (grid[j][i] == 1) {
					paneGrid.get(i).get(j).setBackground(new Background(new BackgroundFill(colorPicker1.getValue(), null, null)));
				} else if (grid[j][i] == 2) {
					paneGrid.get(i).get(j).setBackground(new Background(new BackgroundFill(colorPicker2.getValue(), null, null)));
				} else {
					paneGrid.get(i).get(j).setBackground(null);
				}
			}
		}
		if (model.hasWon(model.getTurn())) {
			disablePaneHandlers(true);
			Alert winnerAlert = new Alert(AlertType.INFORMATION);
			winnerAlert.setTitle("Game Over!");
			winnerAlert.setContentText("Player " + model.getTurn() + " won!");
			winnerAlert.showAndWait();
		} else if (model.boardFull()) {
			disablePaneHandlers(true);
			Alert winnerAlert = new Alert(AlertType.INFORMATION);
			winnerAlert.setTitle("Game Over!");
			winnerAlert.setContentText("NO ONE WON");
			winnerAlert.showAndWait();
		}
 	}

}
