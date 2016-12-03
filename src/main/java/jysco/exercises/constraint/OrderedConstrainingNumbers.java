package jysco.exercises.constraint;

import java.util.*;

public class OrderedConstrainingNumbers {

    private final SortedSet<ConstrainingNumber> rowConstrainingNumbers;
    private final SortedSet<ConstrainingNumber> columnConstrainingNumbers;
    private final SortedSet<ConstrainingNumber> subsquareConstrainingNumbers;

    OrderedConstrainingNumbers() {
        this.rowConstrainingNumbers = new TreeSet<>();
        this.columnConstrainingNumbers = new TreeSet<>();
        this.subsquareConstrainingNumbers = new TreeSet<>();
    }

    public List<ConstrainingNumber> getAllUniqueConstrainingNbs() {
        List<ConstrainingNumber> allUniqueConstrainingNbs = new ArrayList<>();
        allUniqueConstrainingNbs.addAll(getUniqueConstrainingNbs(ConstraintRule.COLUMN));
        allUniqueConstrainingNbs.addAll(getUniqueConstrainingNbs(ConstraintRule.ROW));
        allUniqueConstrainingNbs.addAll(getUniqueConstrainingNbs(ConstraintRule.SUB_SQUARE));
        return allUniqueConstrainingNbs;
    }

    public List<ConstrainingNumber> getAvailableNbsInOppositeConstraints(ConstraintRule constraintRule) {
        List<ConstrainingNumber> nbsInOppositeConstraints = new ArrayList<>();
        nbsInOppositeConstraints.addAll(getOppositeConstrainingNbs(constraintRule));
        nbsInOppositeConstraints.removeAll(getConstrainingNbs(constraintRule));
        return nbsInOppositeConstraints;
    }

    public boolean contains(ConstrainingNumber number) {
        return rowConstrainingNumbers.contains(number)
                || columnConstrainingNumbers.contains(number)
                || subsquareConstrainingNumbers.contains(number);
    }

    SortedSet<ConstrainingNumber> getConstrainingNbs(ConstraintRule constraintRule) {
        switch (constraintRule) {
            case COLUMN:
                return columnConstrainingNumbers;
            case ROW:
                return rowConstrainingNumbers;
            case SUB_SQUARE:
                return subsquareConstrainingNumbers;
        }
        return null;
    }

    private SortedSet<ConstrainingNumber> getUniqueConstrainingNbs(ConstraintRule constraintRule) {
        SortedSet<ConstrainingNumber> uniqueConstrainingNbs = new TreeSet<>(getConstrainingNbs(constraintRule));
        uniqueConstrainingNbs.removeAll(getOppositeConstrainingNbs(constraintRule));
        return uniqueConstrainingNbs;
    }

    private SortedSet<ConstrainingNumber> getOppositeConstrainingNbs(ConstraintRule constraintRule) {
        final SortedSet<ConstrainingNumber> oppositeConstrainingNbs = new TreeSet<>();
        switch (constraintRule) {
            case COLUMN:
                oppositeConstrainingNbs.addAll(rowConstrainingNumbers);
                oppositeConstrainingNbs.addAll(subsquareConstrainingNumbers);
                break;
            case ROW:
                oppositeConstrainingNbs.addAll(columnConstrainingNumbers);
                oppositeConstrainingNbs.addAll(subsquareConstrainingNumbers);
                break;
            case SUB_SQUARE:
                oppositeConstrainingNbs.addAll(columnConstrainingNumbers);
                oppositeConstrainingNbs.addAll(rowConstrainingNumbers);
                break;
        }
        return oppositeConstrainingNbs;
    }

}
