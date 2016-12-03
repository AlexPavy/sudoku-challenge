package jysco.exercises.generator;

import jysco.exercises.constraint.ConstrainingNumber;
import jysco.exercises.constraint.OrderedConstrainingNumbers;
import jysco.exercises.constraint.UsedNumbersBuilder;

import java.util.List;
import java.util.Random;

import static java.util.Collections.shuffle;

public class SudokuConflictResolver {

    private final Random random;
    private final int[][] sudoku;

    public SudokuConflictResolver(Random random, int[][] sudoku) {
        this.random = random;
        this.sudoku = sudoku;
    }

    public int shakeSudoku(int i, int j) {
        UsedNumbersBuilder usedNumbersBuilder
                = new UsedNumbersBuilder(sudoku, i, j, true, false);
        OrderedConstrainingNumbers constrainingNumbers = usedNumbersBuilder.getConstrainingNumbers();
        List<ConstrainingNumber> allUniqueConstrainingNbs = constrainingNumbers.getAllUniqueConstrainingNbs();
        shuffle(allUniqueConstrainingNbs);
        for (ConstrainingNumber uniqueNb : allUniqueConstrainingNbs) {
            if (attemptToReplaceNb(uniqueNb, constrainingNumbers)) return uniqueNb.getValue();
        }
        return -3;
    }

    private boolean attemptToReplaceNb(ConstrainingNumber uniqueNb, OrderedConstrainingNumbers constrainingNumbers) {
        List<ConstrainingNumber> availableNbs = constrainingNumbers
                .getAvailableNbsInOppositeConstraints(uniqueNb.getConstraintRule());
        shuffle(availableNbs, random);
        for (ConstrainingNumber availableNb : availableNbs) {
            boolean replaced = replace(uniqueNb, availableNb);
            if (replaced) {
                return true;
            }
        }
        return false;
    }

    private boolean replace(ConstrainingNumber uniqueNb, ConstrainingNumber availableNb) {
        int i = uniqueNb.getI();
        int j = uniqueNb.getJ();
        OrderedConstrainingNumbers constrainingNumbers
                = new UsedNumbersBuilder(
                        sudoku, i, j,
                true, true)
                .getConstrainingNumbers();
        if (constrainingNumbers.contains(availableNb)) {
            return false;
        } else {
            sudoku[i][j] = availableNb.getValue();
            return true;
        }
    }


}
