package application;

/**
 * Connect-4 Basic AI
 * @author grantgapinski
 * @author baileymiddendorf
 */

public class AIPlayer {
	
	/** The next column that the AI should move to, given that getValueOfNextMove method has been called */
	private int nextColumn;
	
	/** The depth that the algorithm should travel */
	private int maxDepth;
	
	/**
	 * The constructor for the AI player
	 * @param difficulty - The max depth that the AI player should search.
	 */
	public AIPlayer(int difficulty) {
		this.maxDepth = difficulty;
	}
	
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
		return firstNextMoveRecurse(grid);
	}
	
	public int firstNextMoveRecurse(int[][] grid) {
		int bestVal = -10001;
		int [] pos = getPossibleMoves(grid);
		
		for (int i = 0; i < pos.length; i++) {
			int[][] newGrid = makeMove(grid, pos[i], 1);
			int moveVal = nextMoveRecurse(newGrid, false, 1, -100000, 100000);
			
			if (moveVal > bestVal) {
				this.nextColumn = pos[i];
				bestVal = moveVal;
			}
		}
		return bestVal;
	}
	
	/**
	 * The second step for the resursive method, the main recurse
	 * @param grid - The int[][] which is the current board for the recursive method
	 * @param isMax - The boolean to tell if it is the max
	 * @param depth - The current depth
	 * @param alpha - The current Alpha
	 * @param beta - The current Beta
	 * @return Return the value of the current move
	 */
	public int nextMoveRecurse(int[][] grid, boolean isMax, int depth, int alpha, int beta) {
		// Evaluations of the current board, and break statements.
		int playerMoving = isMax ? 1 : 2;
		boolean someoneWon = hasWon(grid, playerMoving, 4);
		boolean gameOver = boardFull(grid);
		
		if (playerMoving == 1 && someoneWon) {
			return 10000;
		} else if (playerMoving == 2 && someoneWon) {
			return -10000;
		} else if (gameOver && !someoneWon) {
			return 0;
		} else if (depth == this.maxDepth) {
			return this.getScore(grid);
		}
		
		// Start the recursive elements
		// Doing a Min-Max Search
		if (isMax) {
			int bestMax = -100000;
			
			int[] pos = getPossibleMoves(grid);
			for (int i = 0; i < pos.length; i++) {
				int[][] newGrid = makeMove(grid, pos[i], 1);
				int nextMax = nextMoveRecurse(newGrid, !isMax, depth + 1, alpha, beta);
				
				if (nextMax > bestMax) {
					bestMax = nextMax;
				}
				alpha = Math.max(alpha, bestMax);
				
				if (beta <= alpha) {
					return bestMax;
				}
			}
			return bestMax;
		} else {
			int bestMin = 100000;
			
			int[] pos = getPossibleMoves(grid);
			for (int i = 0; i < pos.length; i++) {
				int[][] newGrid = makeMove(grid, pos[i], 2);
				int nextMin = nextMoveRecurse(newGrid, !isMax, depth + 1, alpha, beta);
				
				if (bestMin > nextMin) {
					bestMin = nextMin;
				}
				
				beta = Math.min(beta, bestMin);
				
				if (beta <= alpha) {
					return bestMin;
				}
			}
			return bestMin;
		}
	}
	
	/**
	 * Gets the possible moves given the board
	 * @param grid - The "board" to test
	 * @return an array of possible legit moves
	 */
	public int[] getPossibleMoves(int[][] grid) {
		int count = 0;
		for(int i = 0; i < grid[0].length; i++) {
			if (grid[0][i] == 0) {
				count++;
			}
		}
		int [] possibleMoves = new int[count];
		count = 0;
		for(int i = 0; i < grid[0].length; i++) {
			if (grid[0][i] == 0) {
				possibleMoves[count] = i;
				count++;
			}
		}
		return possibleMoves;
	}
	
	/**
	 * tells the caller if the player has won
	 * @param grid - the 'board' to check
	 * @param playerToken - The player token to check
	 * @param lengthOfWin - The length of the win. Should pretty much stay 4
	 * @return Return true if they won
	 */
	public boolean hasWon(int[][] grid, int playerToken, int lengthOfWin) {
		// Checking for Horizontal wins
		for(int i = 0; i < grid.length; i++) {
			int inARow = 0;
			for(int j = 0; j < grid[i].length; j++) {
				if (grid[i][j] == playerToken) {
					inARow++;
					if (inARow == lengthOfWin) {
						return true;
					}
				} else {
					inARow = 0;
				}
			}
		}
		// Checking for Vertical wins
		for(int j = 0; j < grid[0].length; j++) {
			int inARow = 0;
			for(int i = 0; i < grid.length; i++) {
				if (grid[i][j] == playerToken) {
					inARow++;
					if (inARow == lengthOfWin) {
						return true;
					}
				} else {
					inARow = 0;
				}
			}
		}
		// Checking for Diagonal (from down to up) wins
		for (int i = lengthOfWin - 1; i < grid.length; i++) {
			int inARow = 0;
			for (int j = 0; j < i+1; j++) {
				if (grid[i-j][j] == playerToken) {
					inARow++;
					if (inARow == lengthOfWin) {
						return true;
					}
				} else {
					inARow = 0;
				}
			}
		}
		int bottomRowCoord = grid.length - 1;
		for (int i = 1; i < grid[0].length - (lengthOfWin - 1); i++) {
			int inARow = 0;
			for (int j = i; j < grid[0].length; j++) {
				if (grid[bottomRowCoord-(j-i)][j] == playerToken) {
					inARow++;
					if (inARow == lengthOfWin) {
						return true;
					}
				} else {
					inARow = 0;
				}
			}
		}
		// Checking for Diagonal (from up to down) wins
		for (int i = grid[0].length - lengthOfWin; i > 0 ; i--) {
			int inARow = 0;
			for (int j = i; j < grid[0].length; j++) {
				if (grid[j-i][j] == playerToken) {
					inARow++;
					if (inARow == lengthOfWin) {
						return true;
					}
				} else {
					inARow = 0;
				}
			}
		}
		for (int i = 0; i < grid.length - (lengthOfWin - 1); i++) {
			int inARow = 0;
			for (int j = 0; j < grid.length - i; j++) {
				if (grid[i+j][j] == playerToken) {
					inARow++;
					if (inARow == lengthOfWin) {
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
	 * Makes a move virtually on a int[][]
	 * @param grid - The grid to try making a move on.
	 * @param col - The column to drop a piece
	 * @param playerToken - The token to place the piece at the bottom of the column
	 * @return a new grid int[][]
	 */
	public int[][] makeMove(int[][] grid, int col, int playerToken) {
		int[][] copyOfGrid = new int[grid.length][grid[0].length];
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				copyOfGrid[i][j] = grid[i][j];
			}
		}
		
		int position = -1;
		for (int i = 0; i < copyOfGrid.length; i++) {
			if (copyOfGrid[i][col] == 0) {
				position = i;
			}
		}
		if (position != -1) {
			copyOfGrid[position][col] = playerToken;
		}
		return copyOfGrid;
	}
	
	public boolean boardFull(int[][] grid) {
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[i].length; j++) {
				if (grid[i][j] == 0) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Gets a score from the grid
	 * @param grid - the board given
	 * @return return an integer for the score
	 */
	public int getScore(int[][] grid) {
		return oldPlayerBoardVal(grid,1) + -(oldPlayerBoardVal(grid,2));
	}
	
	/**
	 * An attempt at getting the score for the board, but the old method has been a better AI
	 * @param grid the board to use
	 * @param playerToken - the token to check to see if it beneficial
	 * @return - return a score for that token.
	 */
	public int playerBoardVal(int[][] grid, int playerToken) {
		int twoInARowScore = 50;
		int threeInARowScore = 500;
		int threeCountingAGap = 250;
		int horizontalScore = 0;
		// Checking for Horizontal wins
		for(int i = 0; i < grid.length; i++) {
			int inARow = 0;
			for(int j = 0; j < grid[i].length; j++) {
				boolean aSmallGap = false;
				if (grid[i][j] == playerToken) {
					inARow++;
				} else if (grid[i][j] == 0 && inARow >= 1 && !aSmallGap) {
					inARow++;
					aSmallGap = true;
				} else {
					if (inARow == 2 && !aSmallGap) {
						horizontalScore += twoInARowScore;
					}
					else if (inARow == 3 && !aSmallGap) {
						horizontalScore += threeInARowScore;
					}
					else if (inARow == 3 && aSmallGap) {
						horizontalScore += threeCountingAGap;
					}
					inARow = 0;
					aSmallGap = false;
				}
			}
		}
		
		int verticalScore = 0;
		// Checking for Vertical wins
		for(int j = 0; j < grid[0].length; j++) {
			int inARow = 0;
			for(int i = 0; i < grid.length; i++) {
				boolean aSmallGap = false;
				if (grid[i][j] == playerToken) {
					inARow++;
				} else if (grid[i][j] == 0 && inARow >= 1 && !aSmallGap) {
					inARow++;
					aSmallGap = true;
				} else {
					if (inARow == 2 && !aSmallGap) {
						horizontalScore += twoInARowScore;
					}
					else if (inARow == 3 && !aSmallGap) {
						horizontalScore += threeInARowScore;
					}
					else if (inARow == 3 && aSmallGap) {
						horizontalScore += threeCountingAGap;
					}
					inARow = 0;
					aSmallGap = false;
				}
			}
		}
		
		int diagonalScore1 = 0;
		// Checking for Diagonal (from down to up) wins
		for (int i = 2 - 1; i < grid.length; i++) {
			int inARow = 0;
			for (int j = 0; j < i+1; j++) {
				boolean aSmallGap = false;
				if (grid[i][j] == playerToken) {
					inARow++;
				} else if (grid[i][j] == 0 && inARow >= 1 && !aSmallGap) {
					inARow++;
					aSmallGap = true;
				} else {
					if (inARow == 2 && !aSmallGap) {
						horizontalScore += twoInARowScore;
					}
					else if (inARow == 3 && !aSmallGap) {
						horizontalScore += threeInARowScore;
					}
					else if (inARow == 3 && aSmallGap) {
						horizontalScore += threeCountingAGap;
					}
					inARow = 0;
					aSmallGap = false;
				}
			}
		}
		for (int i = 1; i < grid[0].length - (2 - 1); i++) {
			int inARow = 0;
			for (int j = i; j < grid[0].length; j++) {
				boolean aSmallGap = false;
				if (grid[i][j] == playerToken) {
					inARow++;
				} else if (grid[i][j] == 0 && inARow >= 1 && !aSmallGap) {
					inARow++;
					aSmallGap = true;
				} else {
					if (inARow == 2 && !aSmallGap) {
						horizontalScore += twoInARowScore;
					}
					else if (inARow == 3 && !aSmallGap) {
						horizontalScore += threeInARowScore;
					}
					else if (inARow == 3 && aSmallGap) {
						horizontalScore += threeCountingAGap;
					}
					inARow = 0;
					aSmallGap = false;
				}
			}
		}
		
		int diagonalScore2 = 0;
		// Checking for Diagonal (from up to down) wins
		for (int i = grid[0].length - 2; i > 0 ; i--) {
			int inARow = 0;
			for (int j = i; j < grid[0].length; j++) {
				boolean aSmallGap = false;
				if (grid[i][j] == playerToken) {
					inARow++;
				} else if (grid[i][j] == 0 && inARow >= 1 && !aSmallGap) {
					inARow++;
					aSmallGap = true;
				} else {
					if (inARow == 2 && !aSmallGap) {
						horizontalScore += twoInARowScore;
					}
					else if (inARow == 3 && !aSmallGap) {
						horizontalScore += threeInARowScore;
					}
					else if (inARow == 3 && aSmallGap) {
						horizontalScore += threeCountingAGap;
					}
					inARow = 0;
					aSmallGap = false;
				}
			}
		}
		for (int i = 0; i < grid.length - (2 - 1); i++) {
			int inARow = 0;
			for (int j = 0; j < grid.length - i; j++) {
				boolean aSmallGap = false;
				if (grid[i][j] == playerToken) {
					inARow++;
				} else if (grid[i][j] == 0 && inARow >= 1 && !aSmallGap) {
					inARow++;
					aSmallGap = true;
				} else {
					if (inARow == 2 && !aSmallGap) {
						horizontalScore += twoInARowScore;
					}
					else if (inARow == 3 && !aSmallGap) {
						horizontalScore += threeInARowScore;
					}
					else if (inARow == 3 && aSmallGap) {
						horizontalScore += threeCountingAGap;
					}
					inARow = 0;
					aSmallGap = false;
				}
			}
		}
		return horizontalScore + verticalScore + diagonalScore1 + diagonalScore2;
	}
	
	/**
	 * The first method to use for the AI.
	 * @param grid the board to use
	 * @param playerToken - the token to check to see if it beneficial
	 * @return - return a score for that token.
	 */
	public int oldPlayerBoardVal(int[][] grid, int playerToken) {
		int twoInARowScore = 50;
		int threeInARowScore = 500;
		int horizontalScore = 0;
		// Checking for Horizontal wins
		for(int i = 0; i < grid.length; i++) {
			int inARow = 0;
			for(int j = 0; j < grid[i].length; j++) {
				if (grid[i][j] == playerToken) {
					inARow++;
				} else {
					if (inARow == 2) {
						horizontalScore += twoInARowScore;
					}
					else if (inARow == 3) {
						horizontalScore += threeInARowScore;
					}
					inARow = 0;
				}
			}
		}

 		int verticalScore = 0;
		// Checking for Vertical wins
		for(int j = 0; j < grid[0].length; j++) {
			int inARow = 0;
			for(int i = 0; i < grid.length; i++) {
				if (grid[i][j] == playerToken) {
					inARow++;
				} else {
					if (inARow == 2) {
						verticalScore += twoInARowScore;
					}
					else if (inARow == 3) {
						verticalScore += threeInARowScore;
					}
					inARow = 0;
				}
			}
		}

 		int diagonalScore1 = 0;
		// Checking for Diagonal (from down to up) wins
		for (int i = 2 - 1; i < grid.length; i++) {
			int inARow = 0;
			for (int j = 0; j < i+1; j++) {
				if (grid[i-j][j] == playerToken) {
					inARow++;
				} else {
					if (inARow == 2) {
						diagonalScore1 += twoInARowScore;
					}
					else if (inARow == 3) {
						diagonalScore1 += threeInARowScore;
					}
					inARow = 0;
				}
			}
		}
		int bottomRowCoord = grid.length - 1;
		for (int i = 1; i < grid[0].length - (2 - 1); i++) {
			int inARow = 0;
			for (int j = i; j < grid[0].length; j++) {
				if (grid[bottomRowCoord-(j-i)][j] == playerToken) {
					inARow++;
				} else {
					if (inARow == 2) {
						diagonalScore1 += twoInARowScore;
					}
					else if (inARow == 3) {
						diagonalScore1 += threeInARowScore;
					}
					inARow = 0;
				}
			}
		}

 		int diagonalScore2 = 0;
		// Checking for Diagonal (from up to down) wins
		for (int i = grid[0].length - 2; i > 0 ; i--) {
			int inARow = 0;
			for (int j = i; j < grid[0].length; j++) {
				if (grid[j-i][j] == playerToken) {
					inARow++;
				} else {
					if (inARow == 2) {
						diagonalScore2 += twoInARowScore;
					}
					else if (inARow == 3) {
						diagonalScore2 += threeInARowScore;
					}
					inARow = 0;
				}
			}
		}
		for (int i = 0; i < grid.length - (2 - 1); i++) {
			int inARow = 0;
			for (int j = 0; j < grid.length - i; j++) {
				if (grid[i+j][j] == playerToken) {
					inARow++;
				} else {
					if (inARow == 2) {
						diagonalScore2 += twoInARowScore;
					}
					else if (inARow == 3) {
						diagonalScore2 += threeInARowScore;
					}
					inARow = 0;
				}
			}
		}
		return horizontalScore + verticalScore + diagonalScore1 + diagonalScore2;
	}
}
