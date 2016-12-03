package jysco.exercises.constraint;

public class ConstrainingNumber implements Comparable<ConstrainingNumber> {

    private final int i;
    private final int j;
    private final int value;
    private final ConstraintRule constraintRule;

    ConstrainingNumber(int i, int j, int value, ConstraintRule constraintRule) {
        this.i = i;
        this.j = j;
        this.value = value;
        this.constraintRule = constraintRule;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConstrainingNumber that = (ConstrainingNumber) o;

        if (i != that.i) return false;
        if (j != that.j) return false;
        if (value != that.value) return false;
        return constraintRule == that.constraintRule;
    }

    @Override
    public int hashCode() {
        int result = i;
        result = 31 * result + j;
        result = 31 * result + value;
        result = 31 * result + (constraintRule != null ? constraintRule.hashCode() : 0);
        return result;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public int getValue() {
        return value;
    }

    public ConstraintRule getConstraintRule() {
        return constraintRule;
    }

    @Override
    public int compareTo(ConstrainingNumber o) {
        return this.getValue() - o.getValue();
    }
}
