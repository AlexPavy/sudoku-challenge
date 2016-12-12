package jysco.exercises.validation;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Scope(proxyMode= ScopedProxyMode.TARGET_CLASS, value="session")
@Component
public class SudokuSolution {

    private int[][][][] sudoku;

    public int[][][][] getSudoku() {
        return sudoku;
    }

    public void setSudoku(int[][][][] sudoku) {
        this.sudoku = sudoku;
    }
}
