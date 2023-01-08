package com.fariseducation.Data.ObservedData;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class ObservedDatum implements Serializable {
    private transient ArrayList<DataObserver> observingComponents = new ArrayList<DataObserver>();

    public void addObserver(DataObserver observer) {
        this.observingComponents.add(observer);
    }
    protected void removeObserver(DataObserver observer) {
        this.observingComponents.remove(observer);
    }
    protected void update() {
        for (DataObserver observer : this.observingComponents) {
            observer.updateAfterDataChange();
        }
    }

    public abstract boolean equals(Object o);
}