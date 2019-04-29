package application;

// TODO: Make security better, change things to private 
public class GameModel {
	
	public int[][] grid;
	
	public int maxLengthOfWin;
	
	public GameModel(int numRows, int numCols) {
		configureGrid(numRows, numCols);
		maxLengthOfWin = 4;
	}
	
	public void configureGrid(int numRows, int numCols) {
		this.grid = new int[numRows][numCols];
		for(int i = 0; i < this.grid.length; i++) {
			for(int j = 0; j < this.grid[i].length; j++) {
				this.grid[i][j] = 0;
			}
		}
	}
	
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
	
	public boolean hasWon(int playerToken) {
		// Checking for Horizontal wins
		for(int i = 0; i < this.grid.length; i++) {
			int inARow = 0;
			for(int j = 0; j < this.grid[i].length; j++) {
				if (this.grid[i][j] == playerToken) {
					inARow++;
					if (inARow == this.maxLengthOfWin) {
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
					if (inARow == this.maxLengthOfWin) {
						return true;
					}
				} else {
					inARow = 0;
				}
			}
		}
		// TODO: Checking for Diagonal (from down to up) wins
		// TODO: Checking for Diagonal (from up to down) wins
		return false;
	}
	
	public void makeMove(int row, int playerToken) {
		int position = -1;
		for (int i = 0; i < this.grid.length; i++) {
			if (this.grid[i][row] == 0) {
				position = i;
			}
		}
		if (position != -1) {
			this.grid[position][row] = playerToken;
		}
	}
	
	public String toString() {
		String ret = "";
		for (int i = 0; i < this.grid.length; i++) {
			for (int j = 0; j < this.grid[i].length; j++) {
				ret = ret + this.grid[i][j] + "\t";
			}
			ret = ret + "\n";
		}
		return ret;
	}
}
