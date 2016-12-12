package jysco.exercises.api;

import jysco.exercises.SudokuBoard;
import jysco.exercises.gamify.Gamifier;
import jysco.exercises.generator.SudokuFullGeneratorWithRetry;
import jysco.exercises.validation.SudokuSolution;
import jysco.exercises.validation.UserSudokuValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

import static jysco.exercises.SudokuBoard.SUDOKU_PART_SIZE;
import static jysco.exercises.gamify.Gamifier.DEFAULT_MAX_HOLES;

@RestController
public class SudokuController {

    private final UserSudokuValidator sudokuValidator;
    private final SudokuSolution solution;

    @Autowired
    public SudokuController(UserSudokuValidator sudokuValidator, SudokuSolution solution) {
        this.sudokuValidator = sudokuValidator;
        this.solution = solution;
    }

    @GetMapping("/board")
    public SudokuResponse generateNewGame(
            @RequestParam(required = false, name = "holes", defaultValue = DEFAULT_MAX_HOLES) Integer nbHoles) {

        final Random random = new Random();
        SudokuBoard sudokuBoard = new SudokuFullGeneratorWithRetry(random).generate();
        saveFullSolution(sudokuBoard);
        new Gamifier(random, sudokuBoard, nbHoles).gamify();
        int[][][][] sudoku = splitSudoku(sudokuBoard.getBoard());
        return buildNewSudokuResponse(sudoku);
    }

    @GetMapping("/solution")
    public SudokuResponse getCorrectedSudoku() {
        return sudokuValidator.getSolution();
    }

    @PostMapping("/validate")
    public SudokuResponse getCorrectedSudoku(@RequestBody int[][][][] userSudoku) {
        return sudokuValidator.getValidatedSudoku(userSudoku);
    }

    private void saveFullSolution(SudokuBoard sudokuBoard) {
        SudokuBoard fullSolution = sudokuBoard.getSudokuCopy();
        int[][][][] fullSolutionSplit = splitSudoku(fullSolution.getBoard());
        solution.setSudoku(fullSolutionSplit);
    }

    private SudokuResponse buildNewSudokuResponse(int[][][][] sudoku) {
        SudokuResponse response = new SudokuResponse();
        response.setSudoku(sudoku);
        response.setValid(false);
        return response;
    }

    /**
     * Split sudoku into parts of size {@link SudokuBoard#SUDOKU_PART_SIZE}
     */
    private int[][][][] splitSudoku(int[][] sudoku) {
        int[][][][] splitSudoku
                = new int[SUDOKU_PART_SIZE][SUDOKU_PART_SIZE]
                [SUDOKU_PART_SIZE][SUDOKU_PART_SIZE];

        for (int i = 0; i< SUDOKU_PART_SIZE; i++) {
            for (int j = 0; j < SUDOKU_PART_SIZE; j++) {
                for (int i1 = 0; i1 < SUDOKU_PART_SIZE; i1++) {
                    for (int j1 = 0; j1 < SUDOKU_PART_SIZE; j1++) {
                        splitSudoku[i][j][i1][j1] = sudoku[i*SUDOKU_PART_SIZE + i1][j*SUDOKU_PART_SIZE + j1];
                    }
                }
            }
        }
        return splitSudoku;
    }

}