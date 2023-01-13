package com.fariseducation.Data.ObservedData;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public abstract class ObservedDatum implements Serializable {
    private transient ArrayList<DataObserver> observingComponents = new ArrayList<DataObserver>();

    public void addObserver(DataObserver observer) {
        System.out.println("Observer Added");
        this.observingComponents.add(observer);
        System.out.println("Observers: " + this.observingComponents);
    }
    protected void removeObserver(DataObserver observer) {
        this.observingComponents.remove(observer);
    }
    protected void update() {
        System.out.println("Observers: " + this.observingComponents);
        for (DataObserver observer : this.observingComponents) {
            observer.updateAfterDataChange();
        }
    }

    public abstract boolean equals(Object o);

    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        this.observingComponents = new ArrayList<DataObserver>();
    }
}