package jysco.exercises.constraint;

import java.util.SortedSet;
import java.util.TreeSet;

import static jysco.exercises.SudokuBoard.SUDOKU_PART_SIZE;
import static jysco.exercises.SudokuBoard.SUDOKU_SIZE;

/**
 * Builds {@link OrderedConstrainingNumbers}
 */
public class UsedNumbersBuilder {

    private final int[][] sudoku;
    private final int i;
    private final int j;
    private final SortedSet<Integer> usedNumbers;
    private final OrderedConstrainingNumbers constrainingNumbers;
    private final boolean keepNumbersPositions;
    private final boolean addFutureNumbers;

    /**
     * @param keepNumbersPositions space optimization : it can be false in case we only need to keep
     *                             the values of the constraints, not their positions
     * @param addFutureNumbers time optimization : it can be false in case we know future numbers
     *                         were not generated yet
     */
    public UsedNumbersBuilder(int[][] sudoku, int i, int j,
                              boolean keepNumbersPositions, boolean addFutureNumbers) {
        this.sudoku = sudoku;
        this.i = i;
        this.j = j;
        this.usedNumbers = new TreeSet<>();
        this.constrainingNumbers = new OrderedConstrainingNumbers();
        this.keepNumbersPositions = keepNumbersPositions;
        this.addFutureNumbers = addFutureNumbers;
        addUsedColumn();
        addUsedRow();
        addUsedSubPart();
    }

    public SortedSet<Integer> getUsedNumbers() {
        return usedNumbers;
    }

    public OrderedConstrainingNumbers getConstrainingNumbers() {
        return constrainingNumbers;
    }

    private void addUsedRow() {
        int j1 = j - (j % SUDOKU_PART_SIZE);
        for (int l = 0; l<j1; l++) {
            addConstrainingNumber(i, l, ConstraintRule.ROW);
        }
        if (addFutureNumbers && j < SUDOKU_SIZE - SUDOKU_PART_SIZE) {
            int j2 = j1 + SUDOKU_PART_SIZE;
            for (int l = j2; l < SUDOKU_SIZE - 1; l++) {
                addConstrainingNumber(i, l, ConstraintRule.ROW);
            }
        }
    }

    private void addUsedColumn() {
        int i1 = i - (i % SUDOKU_PART_SIZE);
        for (int k = 0; k<i1; k++) {
            addConstrainingNumber(k, j, ConstraintRule.COLUMN);
        }
        if (addFutureNumbers && i < SUDOKU_SIZE - SUDOKU_PART_SIZE) {
            int i2 = i1 + SUDOKU_PART_SIZE;
            for (int k = i2; k < SUDOKU_SIZE - 1; k++) {
                addConstrainingNumber(k, j, ConstraintRule.COLUMN);
            }
        }
    }

    private void addUsedSubPart() {
        int i1 = i - (i % SUDOKU_PART_SIZE);
        int j1 = j - (j % SUDOKU_PART_SIZE);
        int i3 = i1 + SUDOKU_PART_SIZE;
        int j3 = j1 + SUDOKU_PART_SIZE;

        for (int i2 = i1; i2<i3; i2++) {
            for (int j2 = j1; j2<j3; j2++) {
                addConstrainingNumber(i2, j2, ConstraintRule.SUB_SQUARE);
            }
        }
    }

    private void addConstrainingNumber(int i, int j, ConstraintRule constraintRule) {
        int value = sudoku[i][j];
        if (value != 0) {
            usedNumbers.add(value);
            if (keepNumbersPositions) {
                constrainingNumbers.getConstrainingNbs(constraintRule)
                        .add(new ConstrainingNumber(i, j, value, constraintRule));
            }
        }
    }

}
