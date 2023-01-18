package com.fariseducation.Data.ObservedData;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public abstract class ObservedDatum implements Serializable {
    private transient ArrayList<DataObserver> observingComponents = new ArrayList<DataObserver>();
    private static final long serialVersionUID = 2482894380178106854L;

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

    public String stringObservers() {
        return this.observingComponents.toString();
    }

    public abstract boolean equals(Object o);

    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        this.observingComponents = new ArrayList<DataObserver>();
    }
}