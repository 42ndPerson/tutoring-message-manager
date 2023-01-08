package com.fariseducation.Data.ObservedData;

public class ObservedGeneric<ContentType> extends ObservedDatum {
    private ContentType val;

    public ObservedGeneric(ContentType val) {
        this.val = val;
    }

    public ContentType getVal() {
        return this.val;
    }
    public void setVal(ContentType val) {
        this.val = val;
        update();
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public boolean equals(Object o) {
        try {
            return this.val==((ObservedGeneric<ContentType>)o).val;
        } catch(ClassCastException e) {
            return false;
        }
    }
}
