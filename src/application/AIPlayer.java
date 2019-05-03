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
		return firstNextMoveRecurse(grid);
	}
	
	public int firstNextMoveRecurse(int[][] grid) {
		int bestVal = -5;
		int [] pos = getPossibleMoves(grid);
		
		for (int i = 0; i < pos.length; i++) {
			int[][] newGrid = makeMove(grid, pos[i], 1);
//			System.out.println("TEST POSITION: " + pos[i]);
//			for (int j = 0; j < newGrid.length; j++) {
//				for (int k = 0; k < newGrid[0].length; k++) {
//					System.out.print(newGrid[j][k] + "\t");
//				}
//				System.out.print("\n");
//			}
//			System.out.println("--------------------------------------");
//			int test = inARow(newGrid, 1);
//			System.out.println("Most: " + test);
			int moveVal = nextMoveRecurse(newGrid, false, -4, 4);
			
			if (moveVal > bestVal) {
				this.nextColumn = pos[i];
				bestVal = moveVal;
			}
		}
		return bestVal;
	}
	
	public int nextMoveRecurse(int[][] grid, boolean isMax, int alpha, int beta) {
		// Evaluations of the current board, and break statements.
		int playerMoving = isMax ? 1 : 2;
		if (beta <= alpha) {
			return playerMoving == 1 ? 4 : -4;
		}
		int mostInARow = inARow(grid, playerMoving);
		boolean someoneWon = hasWon(grid, playerMoving, 4);
		boolean gameOver = boardFull(grid);
		
		if (playerMoving == 1 && someoneWon) {
			return 4;
		} else if (playerMoving == 2 && someoneWon) {
			return -4;
		} else if (gameOver && !someoneWon) {
			return 0;
		} else if (mostInARow > 1) {
			if (playerMoving == 1) {
				return mostInARow;
			} else {
				return -(mostInARow);
			}
		}
		
		// Start the recursive elements
		// Doing a MiniMax Search
		if (isMax) {
			int bestMax = -4;
			int[] pos = getPossibleMoves(grid);
			for (int i = 0; i < pos.length; i++) {
				int[][] newGrid = makeMove(grid, pos[i], 1);
				int nextMax = nextMoveRecurse(newGrid, !isMax, alpha, beta);
				
				if (nextMax > bestMax) {
					bestMax = nextMax;
				}
				alpha = alpha > bestMax ? alpha : bestMax;
			}
			return bestMax;
		} else {
			int bestMin = 4;
			int[] pos = getPossibleMoves(grid);
			for (int i = 0; i < pos.length; i++) {
				int[][] newGrid = makeMove(grid, pos[i], 2);
				int nextMin = nextMoveRecurse(newGrid, !isMax, alpha, beta);
				
				if (bestMin > nextMin) {
					bestMin = nextMin;
				}
				beta = beta < bestMin ? beta : bestMin;
			}
			return bestMin;
		}
		
		
	}
	
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
	
	public int inARow(int[][] grid, int playerToken) {
		int mostInARow = 0;
		// Checking for Horizontal wins
		for(int i = 0; i < grid.length; i++) {
			int inARow = 0;
			int mostInARowForThisGo = 0;
			for(int j = 0; j < grid[i].length; j++) {
				if (grid[i][j] == playerToken) {
					inARow++;
				} else {
					if (inARow > mostInARowForThisGo) {
						mostInARowForThisGo = inARow;
					}
					inARow = 0;
				}
			}
			if (inARow > mostInARowForThisGo) {
				mostInARowForThisGo = inARow;
			}
			
			if (mostInARowForThisGo > mostInARow) {
				mostInARow = mostInARowForThisGo;
			}
		}
		
		// Checking for Vertical wins
		for(int j = 0; j < grid[0].length; j++) {
			int inARow = 0;
			int mostInARowForThisGo = 0;
			for(int i = 0; i < grid.length; i++) {
				if (grid[i][j] == playerToken) {
					inARow++;
				} else {
					if (inARow > mostInARowForThisGo) {
						mostInARowForThisGo = inARow;
					}
					inARow = 0;
				}
			}
			if (inARow > mostInARowForThisGo) {
				mostInARowForThisGo = inARow;
			}
			
			if (mostInARowForThisGo > mostInARow) {
				mostInARow = mostInARowForThisGo;
			}
		}
		// Checking for Diagonal (from down to up) wins
		for (int i = 2 - 1; i < grid.length; i++) {
			int inARow = 0;
			int mostInARowForThisGo = 0;
			for (int j = 0; j < i+1; j++) {
				if (grid[i-j][j] == playerToken) {
					inARow++;
				} else {
					if (inARow > mostInARowForThisGo) {
						mostInARowForThisGo = inARow;
					}
					inARow = 0;
				}
			}
			if (inARow > mostInARowForThisGo) {
				mostInARowForThisGo = inARow;
			}
			
			if (mostInARowForThisGo > mostInARow) {
				mostInARow = mostInARowForThisGo;
			}
		}
		int bottomRowCoord = grid.length - 1;
		for (int i = 1; i < grid[0].length - (2 - 1); i++) {
			int inARow = 0;
			int mostInARowForThisGo = 0;
			for (int j = i; j < grid[0].length; j++) {
				if (grid[bottomRowCoord-(j-i)][j] == playerToken) {
					inARow++;
				} else {
					if (inARow > mostInARowForThisGo) {
						mostInARowForThisGo = inARow;
					}
					inARow = 0;
				}
			}
			if (inARow > mostInARowForThisGo) {
				mostInARowForThisGo = inARow;
			}
			
			if (mostInARowForThisGo > mostInARow) {
				mostInARow = mostInARowForThisGo;
			}
		}
		// Checking for Diagonal (from up to down) wins
		for (int i = grid[0].length - 2; i > 0 ; i--) {
			int inARow = 0;
			int mostInARowForThisGo = 0;
			for (int j = i; j < grid[0].length; j++) {
				if (grid[j-i][j] == playerToken) {
					inARow++;
				} else {
					if (inARow > mostInARowForThisGo) {
						mostInARowForThisGo = inARow;
					}
					inARow = 0;
				}
			}
			if (inARow > mostInARowForThisGo) {
				mostInARowForThisGo = inARow;
			}
			
			if (mostInARowForThisGo > mostInARow) {
				mostInARow = mostInARowForThisGo;
			}
		}
		for (int i = 0; i < grid.length - (2 - 1); i++) {
			int inARow = 0;
			int mostInARowForThisGo = 0;
			for (int j = 0; j < grid.length - i; j++) {
				if (grid[i+j][j] == playerToken) {
					inARow++;
				} else {
					if (inARow > mostInARowForThisGo) {
						mostInARowForThisGo = inARow;
					}
					inARow = 0;
				}
			}
			if (inARow > mostInARowForThisGo) {
				mostInARowForThisGo = inARow;
			}
			
			if (mostInARowForThisGo > mostInARow) {
				mostInARow = mostInARowForThisGo;
			}
		}
		// If it's not true by now, it's false.
		return mostInARow;
	}
}
