package jysco.exercises.gamify;

import jysco.exercises.SudokuBoard;
import jysco.exercises.constraint.UsedNumbersBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

import static jysco.exercises.SudokuBoard.POSSIBLE_NBS;

/**
 * Validates that Sudoku has only a single solution
 */
public class UniqueSolutionValidator {

    private final SudokuBoard fullSolution;
    private final Set<Integer> possibleNbsSet;
    private final Logger logger;

    public UniqueSolutionValidator(SudokuBoard fullSolution) {
        this.logger = LoggerFactory.getLogger(getClass());
        this.possibleNbsSet = Arrays.stream(POSSIBLE_NBS).boxed().collect(Collectors.toSet());
        this.fullSolution = fullSolution;
    }

    public boolean hasAnyOtherSolution(SudokuBoard current, List<SudokuPosition> holes) {
        if (holes.size() == 0) {
            return false;
        }
        return hasOtherSolutionFrom(current, holes, 0);
    }

    private boolean hasOtherSolutionFrom(SudokuBoard sudoku, List<SudokuPosition> holes, int holeIndex) {
        SudokuPosition hole = holes.get(holeIndex);
        SortedSet<Integer> usedNumbers
                = new UsedNumbersBuilder(sudoku.getBoard(), hole.getI(), hole.getJ(),
                false, true)
                .getUsedNumbers();
        if (usedNumbers.size() >= 9) {
            return false;
        } else {
            return testOtherPossibilities(sudoku, holes, holeIndex, usedNumbers);
        }
    }

    private boolean testOtherPossibilities(
            SudokuBoard sudoku, List<SudokuPosition> holes, final int holeIndex,
            final SortedSet<Integer> usedNumbers) {

        SudokuPosition hole = holes.get(holeIndex);
        Set<Integer> possibleChoices = buildPossibleChoices(usedNumbers, hole);
        for (Integer possibleChoice : possibleChoices) {
            SudokuBoard copy = sudoku.getSudokuCopy();
            copy.getBoard()[hole.getI()][hole.getJ()] = possibleChoice;
            if (testNextPosition(copy, holes, holeIndex)) return true;
        }
        return false;
    }

    private Set<Integer> buildPossibleChoices(SortedSet<Integer> usedNumbers, SudokuPosition hole) {
        Set<Integer> possibleChoices = new HashSet<>();
        possibleChoices.addAll(possibleNbsSet);
        possibleChoices.removeAll(usedNumbers);
        possibleChoices.remove(fullSolution.getBoard()[hole.getI()][hole.getJ()]);
        return possibleChoices;
    }

    /**
     * test next step in normal reading direction
     * (from left to right, from top to bottom)
     */
    private boolean testNextPosition(SudokuBoard sudoku, List<SudokuPosition> holes, int holeIndex) {
        holeIndex++;
        if (holeIndex >= holes.size()) {
            return false;
        } else {
            return hasOtherSolutionFrom(sudoku, holes, holeIndex);
        }
    }

}
