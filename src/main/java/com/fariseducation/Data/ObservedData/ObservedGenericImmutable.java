package com.fariseducation.Data.ObservedData;

public class ObservedGenericImmutable<ContentType> extends ObservedDatum {
    private ContentType val;

    public ObservedGenericImmutable(ContentType val) {
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
            return this.val==((ObservedGenericImmutable<ContentType>)o).val;
        } catch(ClassCastException e) {
            return false;
        }
    }
}
