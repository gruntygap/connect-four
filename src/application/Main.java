package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

/**
 * Main Class
 * @author grantGapinski
 * @author baileyMiddendorf
 */

public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		try {
			BoardGUI root = new BoardGUI();
			Scene scene = new Scene(root,800,800);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
