package com.janote.view.gui.table;

public enum GroupTableColumn {
    ID(0),
    NAME(1),
    SURNAME(2),
    GENDER(3),
    BIRTHDAY(4),
    EMAIL(5),
    REPEATING(6),
    AVERAGE(7);

    private Integer val;

    GroupTableColumn(Integer val) {
        this.val = val;
    }

    Integer value() {
        return this.val;
    }

    public static GroupTableColumn fromInteger(Integer val) {
        if (val != null) {
            for (GroupTableColumn b : GroupTableColumn.values()) {
                if (val == b.val) {
                    return b;
                }
            }
        }
        throw new IllegalArgumentException("No constant with text "
                + val.toString() + " found");
    }
}
