package jysco.exercises.generator;

import jysco.exercises.SudokuBoard;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class SudokuFullGeneratorWithRetry {

    private final Random random;
    private static final int MAX_ATTEMPTS = 100;
    private final org.slf4j.Logger logger;

    public SudokuFullGeneratorWithRetry(Random random) {
        logger = LoggerFactory.getLogger(getClass());
        this.random = random;
    }

    public SudokuBoard generate() {
        for (int a = 1; a < MAX_ATTEMPTS; a++) {
            SudokuFullGenerator generator = new SudokuFullGenerator(random);
            boolean success = generator.generate();
            if (success) {
                logger.info("Successfully generated full sudoku after " + a + " attempts");
                return generator.getSudokuBoard();
            }
        }
        throw new InternalError("Could not generate after " + MAX_ATTEMPTS + " attempts");
    }

}
