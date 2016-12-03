package jysco.exercises;

public class SudokuBoard {

    public final static int SUDOKU_SIZE = 9;
    public final static int SUDOKU_PART_SIZE = 3;
    public final static int SUDOKU_SUB_PART_NB = 9;

    private final int[][] board;

    public int[][] getBoard() {
        return board;
    }

    public SudokuBoard() {
        board = new int[SUDOKU_SIZE][SUDOKU_SIZE];
    }

}
