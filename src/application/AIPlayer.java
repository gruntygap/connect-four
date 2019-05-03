package application;

public class AIPlayer {
	
	/** The next column that the AI should move to, given that getValueOfNextMove method has been called */
	private int nextColumn;
	
	/**
	 * Gets the value of the move for the AI
	 * @return - this.nextColumn -- See doc above
	 */
	public int getNextColumn() {
		return this.nextColumn;
	}

	/**
	 * Gets the value of the next move for the AI using MAXmin
	 * @param grid - The game board at the current check for a move
	 * @return - The value of the AI evaluation.
	 */
	public int getValueOfNextMove(int[][] grid) {
		this.nextColumn = 1;
		return 0;
	}
}
