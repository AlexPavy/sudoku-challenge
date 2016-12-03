package jysco.exercises.generator;

import jysco.exercises.SudokuBoard;
import jysco.exercises.constraint.UsedNumbersBuilder;

import java.util.Random;
import java.util.SortedSet;
import java.util.concurrent.atomic.AtomicBoolean;

import static jysco.exercises.SudokuBoard.SUDOKU_SIZE;

public class SudokuFullGenerator {

    private final SudokuBoard sudokuBoard;
    private final Random random;
    private final SudokuConflictResolver conflictResolver;
    private final AtomicBoolean generated;

    public SudokuFullGenerator(Random random) {
        generated = new AtomicBoolean(false);
        this.random = random;
        this.sudokuBoard = new SudokuBoard();
        this.conflictResolver = new SudokuConflictResolver(random, sudokuBoard.getBoard());
    }

    public SudokuBoard getSudokuBoard() {
        return sudokuBoard;
    }

    public boolean generate() {
        if (generated.get()) {
            throw new IllegalStateException(getClass() + " already generated");
        }
        generated.set(true);
        for (int i = 0; i < SUDOKU_SIZE; i++) {
            for (int j = 0; j < SUDOKU_SIZE; j++) {
                int value = setRandomValue(i, j);
                if (value <= 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private int setRandomValue(int i, int j) {
        SortedSet<Integer> usedNumbers
                = new UsedNumbersBuilder(sudokuBoard.getBoard(), i, j, false, false)
                .getUsedNumbers();
        int val = generateRandomValidValue(usedNumbers);
        if (val <= 0) {
            val = conflictResolver.shakeSudoku(i, j);
        }
        sudokuBoard.getBoard()[i][j] = val;
        return val;
    }

    private int generateRandomValidValue(SortedSet<Integer> usedNumbers) {
        if (usedNumbers.size() >= 9) {
            return SUDOKU_SIZE - usedNumbers.size();
        }
        int val = random.nextInt(SUDOKU_SIZE - usedNumbers.size()) + 1;
        for (Integer usedNumber : usedNumbers) {
            if (usedNumber <= val) {
                val++;
            }
        }
        return val;
    }

}
