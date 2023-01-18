package com.fariseducation.Data.ObservedData;

public class ObservedGeneric<ContentType> extends ObservedDatum {
    protected ContentType val;
    
    private static final long serialVersionUID = -4692484524987273961L;
    
    public ObservedGeneric(ContentType val) {
        this.val = val;
    }

    public ContentType getVal() {
        return this.val;
    }
    public void setVal(ContentType val) {
        this.val = val;
        System.out.println("Update: " + val.toString());
        System.out.println("Update Object: " + val);
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
    @Override
    public String toString() {
        return this.val.toString();
    }
}
