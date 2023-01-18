package com.fariseducation.Data.ObservedData;

import java.io.IOException;
import java.util.function.Supplier;

public class ObservedLiveValue<ContentType> extends ObservedGeneric<ContentType> implements DataObserver {
    private ObservedDatum[] observedVariables;
    private Supplier<ContentType> valSupplier;

    public ObservedLiveValue(ObservedDatum[] observedVariables, Supplier<ContentType> valSupplier) {
        super(valSupplier.get());

        for(ObservedDatum datum : observedVariables) datum.addObserver(this);
        this.observedVariables = observedVariables.clone();
        for(ObservedDatum datum : observedVariables) System.out.println("OLV: " + datum.stringObservers());

        this.valSupplier = valSupplier;
    }

    public ContentType getVal() {
        return this.val;
    }

    @Override
    public void updateAfterDataChange() {
        ContentType oldState = this.val;
        this.val = this.valSupplier.get();

        if(this.val!=oldState) update();
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object o) {
        if(o instanceof ObservedLiveBoolean) {
            return this.observedVariables==((ObservedLiveValue<ContentType>)o).observedVariables;
        }
        return false;
    }
    @Override
    public String toString() {
        return this.hashCode()+"";
    }

    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        for(ObservedDatum datum : observedVariables) datum.addObserver(this);
        this.observedVariables = observedVariables.clone();
    }
}
