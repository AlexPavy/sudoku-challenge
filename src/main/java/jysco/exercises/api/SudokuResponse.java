package jysco.exercises.api;

import static jysco.exercises.SudokuBoard.SUDOKU_PART_SIZE;

public class SudokuResponse {

    int[][][][] sudoku;
    boolean isValid;

    public SudokuResponse() {
        this.sudoku = new int[SUDOKU_PART_SIZE][SUDOKU_PART_SIZE]
                [SUDOKU_PART_SIZE][SUDOKU_PART_SIZE];
    }

    public int[][][][] getSudoku() {
        return sudoku;
    }

    public void setSudoku(int[][][][] sudoku) {
        this.sudoku = sudoku;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }
}
