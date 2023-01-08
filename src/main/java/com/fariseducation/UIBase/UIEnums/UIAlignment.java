package com.fariseducation.UIBase.UIEnums;

public enum UIAlignment {
    LEADING(0),
    CENTER(1),
    TRAILING(2),
    NONE(3);

    private int numericVal;

    UIAlignment(int numeric) {
        this.numericVal = numeric;
    }

    public int getNumericValue() {
        return this.numericVal;
    }
}
