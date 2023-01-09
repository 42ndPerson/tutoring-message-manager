package com.fariseducation.Data.ObservedData;

import java.util.function.Supplier;

public class ObservedLiveBoolean extends ObservedGeneric<Boolean> implements DataObserver {
    private ObservedDatum[] observedVariables;
    private Supplier<Boolean> stateSupplier;
    private ObservedLiveBoolean inverse;

    public ObservedLiveBoolean(ObservedDatum[] observedVariables, Supplier<Boolean> stateSupplier) {
        super(stateSupplier.get());

        for(ObservedDatum datum : observedVariables) datum.addObserver(this);
        this.observedVariables = observedVariables.clone();

        this.stateSupplier = stateSupplier;
    }

    public Boolean getVal() {
        return this.val;
    }

    public ObservedLiveBoolean inverse() {
        this.inverse = new ObservedLiveBoolean(observedVariables, () -> {
            return !this.stateSupplier.get();
        });

        return this.inverse;
    }

    @Override
    public void updateAfterDataChange() {
        Boolean oldState = this.val;
        this.val = this.stateSupplier.get();

        if(this.val!=oldState) update();
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof ObservedLiveBoolean) {
            return this.observedVariables==((ObservedLiveBoolean)o).observedVariables;
        }
        return false;
    }
}
