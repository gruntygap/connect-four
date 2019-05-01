package application;
/**
 * UNIT TEST WRITTEN BY GRANT
 */
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class MakeMoveTest {
	@Test
	void lilVertWinTest() {
		GameModel game = new GameModel(6, 7);
		try {
			game.makeMove(0, 1);
			game.makeMove(0, 1);
			game.makeMove(0, 1);
			game.makeMove(0, 1);
		} catch(Exception e) {
			// Do nothing?
		}
		assertTrue(game.hasWon(1));
		game = new GameModel(6, 7);
		try {
			game.makeMove(1, 1);
			game.makeMove(1, 1);
			game.makeMove(1, 1);
			game.makeMove(1, 1);
		} catch(Exception e) {
			// Do nothing?
		}
		assertTrue(game.hasWon(1));
		game = new GameModel(6, 7);
		try {
			game.makeMove(2, 1);
			game.makeMove(2, 1);
			game.makeMove(2, 1);
			game.makeMove(2, 1);
		} catch(Exception e) {
			// Do nothing?
		}
		assertTrue(game.hasWon(1));
		game = new GameModel(6, 7);
		try {
			game.makeMove(3, 1);
			game.makeMove(3, 1);
			game.makeMove(3, 1);
			game.makeMove(3, 1);
		} catch(Exception e) {
			// Do nothing?
		}
		assertTrue(game.hasWon(1));
		game = new GameModel(6, 7);
		try {
			game.makeMove(4, 1);
			game.makeMove(4, 1);
			game.makeMove(4, 1);
			game.makeMove(4, 1);
		} catch(Exception e) {
			// Do nothing?
		}
		assertTrue(game.hasWon(1));
		game = new GameModel(6, 7);
		try {
			game.makeMove(5, 1);
			game.makeMove(5, 1);
			game.makeMove(5, 1);
			game.makeMove(5, 1);
		} catch(Exception e) {
			// Do nothing?
		}
		assertTrue(game.hasWon(1));
		game = new GameModel(6, 7);
		try {
			game.makeMove(6, 1);
			game.makeMove(6, 1);
			game.makeMove(6, 1);
			game.makeMove(6, 1);
		} catch(Exception e) {
			// Do nothing?
		}
		assertTrue(game.hasWon(1));
	}
	
	@Test
	void horizontalWinTest() {
		GameModel game = new GameModel(6, 7);
		try {
			game.makeMove(0, 1);
			game.makeMove(1, 1);
			game.makeMove(2, 1);
			game.makeMove(3, 1);
		} catch(Exception e) {
			// Do nothing?
		}
		assertTrue(game.hasWon(1));
	}
	
	@Test
	void diagonalWinTest() {
		GameModel game = new GameModel(6, 7);
		try {
			game.makeMove(1, 2);
			game.makeMove(2, 2);
			game.makeMove(2, 2);
			game.makeMove(3, 2);
			game.makeMove(3, 2);
			game.makeMove(3, 2);
			game.makeMove(0, 1);
			game.makeMove(1, 1);
			game.makeMove(2, 1);
			game.makeMove(3, 1);
		} catch(Exception e) {
			// Do nothing?
		}
		assertTrue(game.hasWon(1));
	}
	
	@Test
	void otherDiagonalWinTest() {
		GameModel game = new GameModel(6, 7);
		try {
			game.makeMove(0, 2);
			game.makeMove(0, 2);
			game.makeMove(0, 2);
			game.makeMove(1, 2);
			game.makeMove(1, 2);
			game.makeMove(2, 2);
			game.makeMove(0, 1);
			game.makeMove(1, 1);
			game.makeMove(2, 1);
			game.makeMove(3, 1);
			System.out.println(game);
		} catch(Exception e) {
			// Do nothing?
		}
		assertTrue(game.hasWon(1));
	}

}
