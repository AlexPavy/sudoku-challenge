package jysco.exercises;

import java.util.Arrays;

public class SudokuBoard {

    public final static int SUDOKU_SIZE = 9;
    public final static int SUDOKU_PART_SIZE = 3;
    public final static int[] POSSIBLE_NBS = {1, 2, 3, 4, 5, 6, 7, 8, 9};

    private final int[][] board;

    public int[][] getBoard() {
        return board;
    }

    public SudokuBoard() {
        board = new int[SUDOKU_SIZE][SUDOKU_SIZE];
    }

    public SudokuBoard(int[][] sudoku) {
        this.board = sudoku;
    }

    public SudokuBoard getSudokuCopy() {
        return new SudokuBoard(Arrays.stream(board).map(int[]::clone).toArray(int[][]::new));
    }

}
