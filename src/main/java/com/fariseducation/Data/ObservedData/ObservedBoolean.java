package com.fariseducation.Data.ObservedData;

public class ObservedBoolean extends ObservedDatum {
    private boolean val;

    public ObservedBoolean(boolean val) {
        this.val = val;
    }

    public boolean getBoolean() {
        return this.val;
    }
    public void setBoolean(boolean val) {
        this.val = val;
        update();
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof ObservedBoolean) return this.val==((ObservedBoolean)o).val;
        return false;
    }
}
