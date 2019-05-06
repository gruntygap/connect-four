package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		try {
			BoardGUI root = new BoardGUI();
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
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
