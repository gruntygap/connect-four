package application;
/**
 * Connect-4 testing Driver
 * @author grantgapinski
 * @author baileymiddendorf
 * @version 05/01/19
 */
import java.util.Scanner;

public class ModelDriver {

	public static void main(String[] args) {
		// Decide if you want the AI to play with you ;)
		// AI will be player 1 with the default configuration
		boolean ai = true;
		AIPlayer aiPlayer = new AIPlayer();
		
		// Start new game
		GameModel game = new GameModel(6, 7);
		// Print the game
		System.out.println(game);
		
		// Create Scanner for user input
		Scanner input = new Scanner(System.in);
		int choice = -1;
		while(!game.boardFull()) {
			if (ai && game.getTurn() == 1) {
				System.out.println("It is player " + game.getTurn() + "'s turn (THE AI)");
				int value = aiPlayer.getValueOfNextMove(game.getGrid());
				int move = aiPlayer.getNextColumn();
				try {
					game.makeMove(move, game.getTurn());
					System.out.println("---------------------------------------");
					System.out.println("AI MOVE: " + move + "; Value: " + value);
					System.out.println("---------------------------------------");
				} catch (Exception e) {
					System.out.println("AI MADE AN ERROR: " + e.getMessage());
				}
			} else {
				System.out.println("It is player " + game.getTurn() + "'s turn:");
				System.out.println("Enter the number of the column which you want to drop your piece (0-6): ");
				choice = input.nextInt();
				try {
					game.makeMove(choice, game.getTurn());
				} catch (Exception e) {
					System.out.println("ERRRRRRRROR: " + e.getMessage());
				}
			}
			System.out.println(game);
			if (game.hasWon(1)) {
				if (ai) {
					System.out.println("THE AI BEAT YOU, FEEL BAD!");
				} else {
					System.out.println("Player 1 won!");
				}
				break;
			} else if (game.hasWon(2)) {
				System.out.println("Player 2 won!");
				break;
			}
		}
		input.close();
	}
}
