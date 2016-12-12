package jysco.exercises.gamify;

public class SudokuPosition {

    private final int i;
    private final int j;

    public SudokuPosition(int i, int j) {
        this.i = i;
        this.j = j;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SudokuPosition that = (SudokuPosition) o;

        if (i != that.i) return false;
        return j == that.j;
    }

    @Override
    public int hashCode() {
        int result = i;
        result = 31 * result + j;
        return result;
    }

    @Override
    public String toString() {
        return "SudokuPosition{" +
                "i=" + i +
                ", j=" + j +
                '}';
    }
}
