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
		// Start new game
		GameModel game = new GameModel(6, 7);
		// Print the game
		System.out.println(game);
		
		// Create Scanner for user input
		Scanner input = new Scanner(System.in);
		int choice = -1;
		while(!game.boardFull()) {
			System.out.println("It is player " + game.getTurn() + "'s turn:");
			System.out.println("Enter the number of the column which you want to drop your piece (0-6): ");
			choice = input.nextInt();
			try {
				game.makeMove(choice, game.getTurn());
			} catch (Exception e) {
				System.out.println("ERRRRRRRROR: " + e.getMessage());
			}
			System.out.println(game);
			if (game.hasWon(1)) {
				System.out.println("Player 1 won!");
				break;
			} else if (game.hasWon(2)) {
				System.out.println("Player 2 won!");
				break;
			}
		}
		input.close();
	}
}
