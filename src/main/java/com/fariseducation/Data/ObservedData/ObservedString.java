/*package com.fariseducation.Data.ObservedData;

public class ObservedString extends ObservedDatum implements DataObserver {
    private ObservedString[] components = null;
    private String string;

    public ObservedString(String string) {
        this.string = string;
    }
    //Allows a UIObserved String to be made of other UIObserved Strings
    public ObservedString(ObservedString[] components) {
        for(ObservedString oString : components) {
            oString.addObserver(this);
        }

        this.components = components;
        build();
    }

    public String getString() {
        return this.string;
    }
    public void setString(String string) {
        this.string = string;
        update();
    }

    private void build() {
        String stringSum = "";

        if(this.components != null) {
            for(ObservedString oString : this.components) {
                stringSum += oString.getString();
            }

            this.string = stringSum;
        }
    }

    @Override
    public void updateAfterDataChange() {
        build();
        update();
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof ObservedString) return this.string.equals(((ObservedString)o).string);
        return false;
    }
}*/
