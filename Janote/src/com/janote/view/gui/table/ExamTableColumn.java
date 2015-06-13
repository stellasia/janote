package com.janote.view.gui.table;

public enum ExamTableColumn {
    ID(0), NAME(1), SURNAME(2), REPEATING(3);

    private final Integer val;

    ExamTableColumn(Integer val) {
        this.val = val;
    }

    Integer value() {
        return this.val;
    }

    public static ExamTableColumn fromInteger(Integer val) {
        if (val != null) {
            for (ExamTableColumn b : ExamTableColumn.values()) {
                if (val == b.val) {
                    return b;
                }
            }
        }
        throw new IllegalArgumentException("No constant with text "
                + val.toString() + " found");
    }
}
