package jogo;

import java.util.ArrayList;
import java.util.Random;

public class Game {
	private int[][] gameBoard;
	private Random r = new Random();
	// recebe os valores "Continue", "Lose" e "Win"
	private String gameState;
	private int score = 0;
	private int highScore;
	
	public Game(int highScore) {
		gameBoard = new int[4][4];
		gameState = "Continue";
//		auxTesting();
		addNewNumber();
		addNewNumber();
		
		this.highScore = highScore; 
	}
	
	public int[][] getGameBoard() {
		return gameBoard;
	}
	
	public String getGameState() {
		return gameState;
	}
	
	public int getScore() {
		return score;
	}
	
	public int getHighScore() {
		return highScore;
	}
	
	public void setHighScore(int highScore) {
		this.highScore = highScore;
	}
	
	// Funcao usada para testar a Win State
	public void auxTesting() {
		gameBoard[0][0] = 1024;
		gameBoard[0][1] = 1024;
	}
	
	public void addNewNumber() {
		ArrayList<Integer> emptySpacesX = new ArrayList<Integer>();
		ArrayList<Integer> emptySpacesY = new ArrayList<Integer>();
		for(int x = 0; x < 4; x++) {
			for(int y= 0; y < 4; y++) {
				if(gameBoard[x][y] == 0) {
					emptySpacesX.add(x);
					emptySpacesY.add(y);
				}
			}
		}
		int place = r.nextInt(emptySpacesX.size());
		int numberChooser = r.nextInt(10); // value 0-9
		int newNumber = 2;
		// Deve gerar um 4 10% do tempo
		if (numberChooser == 0) {
			newNumber = 4;
		}
		int X = emptySpacesX.get(place);
		int Y = emptySpacesY.get(place);
		gameBoard[X][Y] = newNumber;
	}
	
	
	public void pushUp() {
		boolean moveu = false;
		for (int y = 0; y < 4; y++) {
			boolean[] alreadyCombined = { false, false, false, false };
			//a primeira fila nunca faz nada
			for(int x = 1; x < 4; x++) {			
				if(gameBoard[x][y] != 0) {
					int value = gameBoard[x][y];
					int X = x-1;
					while ( (X >= 0) && (gameBoard[X][y] == 0)) {
						X--;
					}
					if (X == -1) {
						gameBoard[x][y] = 0;
						gameBoard[0][y] = value;
						moveu = true;
					}
					else if ((gameBoard[X][y] != value) && (x-X != 1)) {
						gameBoard[x][y] = 0;
						gameBoard[X+1][y] = value;
						moveu = true;
					}
					else if(gameBoard[X][y] == value) {
						moveu = true;
						if(alreadyCombined[X]) {
							gameBoard[x][y] = 0;							
							gameBoard[X+1][y] = value;
						}
						else {
							gameBoard[x][y] = 0;
							gameBoard[X][y] *= 2;
							score += gameBoard[X][y];
							alreadyCombined[X] = true;
						}
					}
				}
			}
		}
		if(moveu) {
			addNewNumber();
		}
	}
	
	public void pushDown() {
		boolean moveu = false;
		for (int y = 0; y < 4; y++) {
			boolean[] alreadyCombined = { false, false, false, false };
			//a ultima fila nunca faz nada
			for(int x = 2; x > -1; x--) {
				if(gameBoard[x][y] != 0) {
					int value = gameBoard[x][y];
					int X = x+1;
					while ( (X <= 3) && (gameBoard[X][y] == 0)) {
						X++;
					}
					if (X == 4) {
						moveu = true;
						gameBoard[x][y] = 0;
						gameBoard[3][y] = value;
					}
					else if (gameBoard[X][y] != value && X-x != 1) {
						moveu = true;
						gameBoard[x][y] = 0;
						gameBoard[X-1][y] = value;
					}
					else if (gameBoard[X][y] == value) {
						moveu = true;
						if(alreadyCombined[X]) {
							gameBoard[x][y] = 0;							
							gameBoard[X-1][y] = value;
						}
						else {
							gameBoard[x][y] = 0;
							gameBoard[X][y] *= 2;
							score += gameBoard[X][y];
							alreadyCombined[X] = true;
						}
					}
				}
			}
		}
		if(moveu) {
			addNewNumber();
		}
	}
	
	public void pushLeft() {
		boolean moveu = false;
		for (int x = 0; x < 4; x++) {
			boolean[] alreadyCombined = { false, false, false, false };
			//a primeira coluna nunca faz nada
			for(int y = 1; y < 4; y++) {
				if(gameBoard[x][y] != 0) {
					int value = gameBoard[x][y];
					int Y = y-1;
					while ( (Y >= 0) && (gameBoard[x][Y] == 0)) {
						Y--;
					}
					if (Y == -1) {
						moveu = true;
						gameBoard[x][y] = 0;
						gameBoard[x][0] = value;
					}
					else if (gameBoard[x][Y] != value && y-Y != 1) {
						moveu = true;
						gameBoard[x][y] = 0;
						gameBoard[x][Y+1] = value;
					}
					else if (gameBoard[x][Y] == value){
						moveu = true;
						if(alreadyCombined[Y]) {
							gameBoard[x][y] = 0;							
							gameBoard[x][Y+1] = value;
						}
						else {
							gameBoard[x][y] = 0;
							gameBoard[x][Y] *= 2;
							score += gameBoard[x][Y];
							alreadyCombined[Y] = true;
						}
					}
				}
			}
		}
		if(moveu) {
			addNewNumber();
		}
	}

	public void pushRight() {
		boolean moveu = false;
		for (int x = 0; x < 4; x++) {
			boolean[] alreadyCombined = { false, false, false, false };
			//a ultima coluna nunca faz nada
			for(int y = 2; y > -1; y--) {
				if(gameBoard[x][y] != 0) {
					int value = gameBoard[x][y];
					int Y = y+1;
					while ( (Y <= 3) && (gameBoard[x][Y] == 0)) {
						Y++;
					}
					if (Y == 4) {
						moveu = true;
						gameBoard[x][y] = 0;
						gameBoard[x][3] = value;
					}
					else if (gameBoard[x][Y] != value && Y-y != 1) {
						moveu = true;
						gameBoard[x][y] = 0;
						gameBoard[x][Y-1] = value;
					}
					else if (gameBoard[x][Y] == value){
						moveu = true;
						if(alreadyCombined[Y]) {
							gameBoard[x][y] = 0;							
							gameBoard[x][Y-1] = value;
						}
						else {
							gameBoard[x][y] = 0;
							gameBoard[x][Y] *= 2;
							score += gameBoard[x][Y];
							alreadyCombined[Y] = true;
						}
					}
				}
			}
		}
		if(moveu) {
			addNewNumber();
		}
	}
	
	public boolean checkFor2048() {
		for(int x = 0; x < 4; x++ ) {
			for(int y = 0; y < 4; y++ ) {
				if(gameBoard[x][y] == 2048) {
					return true;
				}
			}	
		}
		return false;
	}
	
	public boolean isBoardFull() {
		for(int x = 0; x < 4; x++ ) {
			for(int y = 0; y < 4; y++ ) {
				if(gameBoard[x][y] == 0) {
					return false;
				}
			}	
		}
		return true;
	}
	
	//Chama somente depois de isBoardFull() 
	public boolean hasMoves() {
		for(int x = 0; x < 4; x++ ) {
			for(int y = 0; y < 4; y++ ) {
				if(x == 0) {
					if(y != 0) {
						if(gameBoard[x][y] == gameBoard[x][y-1]) {
							return true;
						}
					}
				} 
				else {
					if(y != 0) {
						if(gameBoard[x][y] == gameBoard[x][y-1]) {
							return true;
						}
					}
					if(gameBoard[x][y] == gameBoard[x-1][y]) {
						return true;
					}
				}
			}	
		}
		return false;
	}
	
	public void checkState() {
		if(checkFor2048()) {
			gameState = "Win";
		}
		else if (isBoardFull()){
			if (hasMoves()) {
				gameState = "Continue";
			}
			else {
				gameState = "Lose";
			}
		}
		else {
			gameState = "Continue";			
		}
	}
	
}