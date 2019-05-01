package application;
/**
 * Connect-4 Model
 * @author grantgapinski
 * @author baileymiddendorf
 * @version 05/01/19
 */
public class GameModel {
	
	/** The 'board' which stores all the player moves */
	private int[][] grid;
	
	/** The length of consecutive pieces for a win, by default this will be 4 */
	private int lengthOfWin;
	
	/** The current turn. It will be either 1 or 2 */
	private int turn;
	
	/**
	 * The GameModel Constructor
	 * @param numRows - The number of rows for the grid
	 * @param numCols - The number of columns for the grid
	 */
	public GameModel(int numRows, int numCols) {
		configureGrid(numRows, numCols);
		turn = 1;
		this.lengthOfWin = 4;
	}
	
	/**
	 * Method which handles starting our grid, with all 0's 
	 * @param numRows - Number of rows for the grid
	 * @param numCols - Number of columns for the grid
	 */
	public void configureGrid(int numRows, int numCols) {
		this.grid = new int[numRows][numCols];
		for(int i = 0; i < this.grid.length; i++) {
			for(int j = 0; j < this.grid[i].length; j++) {
				this.grid[i][j] = 0;
			}
		}
	}
	
	/**
	 * Will let you know if the grid is full of something other than 0
	 * @return - a boolean.
	 */
	public boolean boardFull() {
		for(int i = 0; i < this.grid.length; i++) {
			for(int j = 0; j < this.grid[i].length; j++) {
				if (this.grid[i][j] == 0) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * A method to return boolean responding to the question: Did X win?
	 * @param playerToken - The player you want to check for
	 * @return - a boolean to say if that player got lengthOfWin in a row.
	 */
	public boolean hasWon(int playerToken) {
		// Checking for Horizontal wins
		for(int i = 0; i < this.grid.length; i++) {
			int inARow = 0;
			for(int j = 0; j < this.grid[i].length; j++) {
				if (this.grid[i][j] == playerToken) {
					inARow++;
					if (inARow == this.lengthOfWin) {
						return true;
					}
				} else {
					inARow = 0;
				}
			}
		}
		// Checking for Vertical wins
		for(int j = 0; j < this.grid[0].length; j++) {
			int inARow = 0;
			for(int i = 0; i < this.grid.length; i++) {
				if (this.grid[i][j] == playerToken) {
					inARow++;
					if (inARow == this.lengthOfWin) {
						return true;
					}
				} else {
					inARow = 0;
				}
			}
		}
		// Checking for Diagonal (from down to up) wins
		for (int i = this.lengthOfWin - 1; i < this.grid.length; i++) {
			int inARow = 0;
			for (int j = 0; j < i+1; j++) {
				if (this.grid[i-j][j] == playerToken) {
					inARow++;
					if (inARow == this.lengthOfWin) {
						return true;
					}
				} else {
					inARow = 0;
				}
			}
		}
		int bottomRowCoord = this.grid.length - 1;
		for (int i = 1; i < this.grid[0].length - (this.lengthOfWin - 1); i++) {
			int inARow = 0;
			for (int j = i; j < this.grid[0].length; j++) {
				if (this.grid[bottomRowCoord-(j-i)][j] == playerToken) {
					inARow++;
					if (inARow == this.lengthOfWin) {
						return true;
					}
				} else {
					inARow = 0;
				}
			}
		}
		// Checking for Diagonal (from up to down) wins
		for (int i = this.grid[0].length - this.lengthOfWin; i > 0 ; i--) {
			int inARow = 0;
			for (int j = i; j < this.grid[0].length; j++) {
				if (this.grid[j-i][j] == playerToken) {
					inARow++;
					if (inARow == this.lengthOfWin) {
						return true;
					}
				} else {
					inARow = 0;
				}
			}
		}
		for (int i = 0; i < this.grid.length - (this.lengthOfWin - 1); i++) {
			int inARow = 0;
			for (int j = 0; j < this.grid.length - i; j++) {
				if (this.grid[i+j][j] == playerToken) {
					inARow++;
					if (inARow == this.lengthOfWin) {
						return true;
					}
				} else {
					inARow = 0;
				}
			}
		}
		// If it's not true by now, it's false.
		return false;
	}
	
	/**
	 * THE ALL IMPORTANT makeMove method, which will make a move for a given player
	 * @param col - The column you want to drop your piece
	 * @param playerToken - The token for the given player (probably 1 or 2)
	 * @throws Exception - If the col is out of bounds, or if they try dropping in a column that is full
	 */
	public void makeMove(int col, int playerToken) throws Exception {
		// Out of bounds error
		if (col < 0 || col > this.grid.length) {
			throw new Exception("The row of your desired placement is out of bounds!");
		}
		int position = -1;
		for (int i = 0; i < this.grid.length; i++) {
			if (this.grid[i][col] == 0) {
				position = i;
			}
		}
		if (position != -1) {
			this.grid[position][col] = playerToken;
		} else {
			throw new Exception("That Column is full!");
		}
		if (!hasWon(this.turn)) {
			changeTurn();
		}
	}
	
	/**
	 * The change turn method, will change the current turn
	 */
	private void changeTurn() {
		if (this.turn == 1) {
			this.turn = 2;
		} else {
			this.turn = 1;
		}
	}
	
	/**
	 * Gets the current turn
	 * @return - this.turn
	 */
	public int getTurn() {
		return this.turn;
	}
	
	/**
	 * Prints a "board"
	 */
	public String toString() {
		String ret = "";
		for (int i = 0; i < this.grid.length; i++) {
			for (int j = 0; j < this.grid[i].length; j++) {
				if (this.grid[i][j] == 0) {
					ret = ret + "( )" + "\t";
				} else {
					ret = ret + "(" + this.grid[i][j] + ")" + "\t";
				}
			}
			ret = ret + "\n";
		}
		return ret;
	}
}
