package com.fariseducation.Data.ObservedData;

public class ObservedDouble extends ObservedDatum {
    private double val;

    public ObservedDouble(double val) {
        this.val = val;
    }

    public double getVal() {
        return this.val;
    }
    public void setVal(double val) {
        this.val = val;
        update();
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof ObservedDouble) return this.val==((ObservedDouble)o).val;
        return false;
    }
}
