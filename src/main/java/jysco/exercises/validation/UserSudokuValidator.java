package jysco.exercises.validation;

import jysco.exercises.api.SudokuResponse;
import org.springframework.stereotype.Component;

import static jysco.exercises.SudokuBoard.SUDOKU_PART_SIZE;

@Component
public class UserSudokuValidator {

    private final SudokuSolution solution;

    public UserSudokuValidator(SudokuSolution solution) {
        this.solution = solution;
    }

    public SudokuResponse getValidatedSudoku(int[][][][] userSudoku) {
        int[][][][] solutionSudoku = solution.getSudoku();
        if (solutionSudoku == null) {
            throw new IllegalStateException("A unique solution should have been stored in the session");
        }

        return buildValidatedResponse(userSudoku, solutionSudoku);
    }

    public SudokuResponse getSolution() {
        int[][][][] sudoku = solution.getSudoku();
        SudokuResponse response = new SudokuResponse();
        response.setSudoku(sudoku);
        response.setValid(true);
        return response;
    }

    private SudokuResponse buildValidatedResponse(int[][][][] userSudoku, int[][][][] solutionSudoku) {
        SudokuResponse response = new SudokuResponse();
        response.setValid(true);
        for (int i = 0; i< SUDOKU_PART_SIZE; i++) {
            for (int j = 0; j < SUDOKU_PART_SIZE; j++) {
                for (int i1 = 0; i1 < SUDOKU_PART_SIZE; i1++) {
                    for (int j1 = 0; j1 < SUDOKU_PART_SIZE; j1++) {
                        if (solutionSudoku[i][j][i1][j1] != userSudoku[i][j][i1][j1]) {
                            response.getSudoku()[i][j][i1][j1] = -1;
                            response.setValid(false);
                        } else {
                            response.getSudoku()[i][j][i1][j1] = userSudoku[i][j][i1][j1];
                        }
                    }
                }
            }
        }
        return response;
    }

}
