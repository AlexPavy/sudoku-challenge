package jysco.exercises.gamify;

import jysco.exercises.SudokuBoard;

import java.util.*;

import static com.google.common.collect.Lists.newArrayList;
import static jysco.exercises.SudokuBoard.SUDOKU_SIZE;

/**
 * Adds holes to gamify the full Sudoku
 */
public class Gamifier {

    private Random random;
    private SudokuBoard sudokuBoard;

    /**
     * Max failed attempts to add a hole
     */
    private static final int MAX_ATTEMPTS = 10;

    /**
     * Changes the difficulty
     */
    private static final int MAX_HOLES = 36;

    public Gamifier(Random random, SudokuBoard sudokuBoard) {
        this.random = random;
        this.sudokuBoard = sudokuBoard;
    }

    public void gamify() {
        final List<SudokuPosition> possibleHoles = buildPossibleHoles();
        final List<SudokuPosition> addedHoles = newArrayList();
        int a = 0, holes = 0;

        while (possibleHoles.size() > 0 && a < MAX_ATTEMPTS && holes < MAX_HOLES) {
            SudokuPosition holeAttempt = possibleHoles.get(random.nextInt(possibleHoles.size()));
            int savedValue = replaceValueWithHole(holeAttempt);
            UniqueSolutionValidator solutionValidator = new UniqueSolutionValidator(sudokuBoard.getSudokuCopy());
            if (solutionValidator.hasAnyOtherSolution(sudokuBoard.getSudokuCopy(), addedHoles)) {
                setValueAtPosition(holeAttempt, savedValue);
                a++;
            } else {
                addedHoles.add(new SudokuPosition(holeAttempt.getI(), holeAttempt.getJ()));
                holes++;
            }
            possibleHoles.remove(holeAttempt);
        }
    }

    private void setValueAtPosition(SudokuPosition position, int value) {
        sudokuBoard.getBoard()[position.getI()][position.getJ()] = value;
    }

    private int replaceValueWithHole(SudokuPosition holeAttempt) {
        int savedValue = sudokuBoard.getBoard()[holeAttempt.getI()][holeAttempt.getJ()];
        setValueAtPosition(holeAttempt, 0);
        return savedValue;
    }

    private List<SudokuPosition> buildPossibleHoles() {
        List<SudokuPosition> possibleHoles = new ArrayList<>();
        for (int i = 0; i < SUDOKU_SIZE; i++) {
            for (int j = 0; j < SUDOKU_SIZE; j++) {
                possibleHoles.add(new SudokuPosition(i, j));
            }
        }
        return possibleHoles;
    }
}
