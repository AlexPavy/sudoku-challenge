package jysco.exercises;

import jysco.exercises.gamify.Gamifier;
import jysco.exercises.generator.SudokuFullGeneratorWithRetry;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Random;

import static jysco.exercises.SudokuBoard.SUDOKU_PART_SIZE;

@Controller
public class SudokuController {

    @GetMapping("/")
    public String generateNewGame(Model model) {
        final Random random = new Random();
        SudokuBoard sudokuBoard = new SudokuFullGeneratorWithRetry(random).generate();
        new Gamifier(random, sudokuBoard).gamify();
        int[][][][] splitSudoku = splitSudoku(sudokuBoard.getBoard());
        model.addAttribute("sudoku", splitSudoku);
        return "index";
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