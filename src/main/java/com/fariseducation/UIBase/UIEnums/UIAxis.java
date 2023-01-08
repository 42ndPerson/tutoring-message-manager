package com.fariseducation.UIBase.UIEnums;

public enum UIAxis {
    HORIZONTAL(0),
    VERTICAL(1);

    private int numericVal;

    UIAxis(int numeric) {
        this.numericVal = numeric;
    }

    public int getNumericValue() {
        return this.numericVal;
    }
}
