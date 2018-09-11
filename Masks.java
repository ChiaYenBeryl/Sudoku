package Sudoku;

import java.util.Random;

public class Masks {
	private boolean[][] masksEasy =  { 
			{ true, true, false, false, false, true, false, true, true },
			{ false, false, false, true, true, true, false, false, true },
			{ false, true, true, true, true, false, false, false, false },
			{ false, false, true, true, true, false, true, false, true },
			{ true, true, true, false, false, false, false, false, true },
			{ true, true, false, true, false, true, true, false, false },
			{ false, true, true, false, true, true, true, false, false },
			{ true, true, false, true, true, false, true, true, false },
			{ true, false, true, true, false, true, false, true, false } };
	private boolean[][] masksStandard =  { 
			{ false, true, true, true, false, true, true, true, false },
			{ true, true, true, true, true, true, true, false, false },
			{ false, false, false, true, true, true, true, true, true },
			{ false, false, false, true, true, true, true, true, true },
			{ true, true, true, false, true, false, true, true, true },
			{ true, false, true, false, true, true, false, true, true },
			{ true, true, true, true, true, true, false, true, false },
			{ true, true, true, false, false, false, true, true, true },
			{ false, false, true, true, false, true, false, true, true } };
	private boolean[][] masksHell =  { 
			{ true, true, false, true, true, true, true, false, false },
			{ true, true, false, false, true, false, true, true, true },
			{ true, true, true, true, true, false, true, false, true },
			{ true, true, false, false, true, true, false, true, false },
			{ false, true, true, true, true, false, true, false, true },
			{ true, false, true, true, true, true, true, true, true },
			{ false, true, true, true, true, true, true, true, true },
			{ true, false, true, true, true, false, true, false, true },
			{ true, true, false, false, false, true, true, true, false } };
	public Masks(String difficulty){
		for (int row = 0; row < 9; ++row) {
			for (int col = 0; col < 9; ++col) {
				Sudoku.masks[row][col]=false;
			}
		}
		if (difficulty=="Super Easy"){
			for (int row = 0; row < 9; ++row) {
				for (int col = 0; col < 9; ++col) {
					Sudoku.masks[row][col]=masksEasy[row][col];
				}
			}
		}else if (difficulty=="Standard"){
		for (int row = 0; row < 9; ++row) {
			for (int col = 0; col < 9; ++col) {
				Sudoku.masks[row][col]=masksStandard[row][col];
				}
			}
		}else if (difficulty=="Hell"){
				for (int row = 0; row < 9; ++row) {
						for (int col = 0; col < 9; ++col) {
								Sudoku.masks[row][col]=masksHell[row][col];
						}
				}
		}
	}
	public Masks(int LEVEL){
		for (int row = 0; row < 9; ++row) {
			for (int col = 0; col < 9; ++col) {
				Sudoku.masks[row][col]=false;
			}
		}
		Random r = new Random();
			while(LEVEL>0){
					int ranNum = r.nextInt(9)%9;
					int ranNum2 = r.nextInt(9)%9;
					Sudoku.masks[ranNum][ranNum2]=true;
					LEVEL--;
			}
	}
}
	

