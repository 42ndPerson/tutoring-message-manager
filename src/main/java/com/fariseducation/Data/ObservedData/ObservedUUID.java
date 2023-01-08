package com.fariseducation.Data.ObservedData;

import java.util.UUID;

public class ObservedUUID extends ObservedDatum {
    public static ObservedUUID BLANK = new ObservedUUID(new UUID(0,0));
    
    private UUID id;

    public ObservedUUID(UUID id) {
        this.id = id;
    }
    public ObservedUUID() {
        this.id = UUID.randomUUID();
    }

    public UUID getUUID() {
        return this.id;
    }
    public void setUUID(UUID id) {
        this.id = id;
        update();
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof ObservedUUID) return this.id.equals(((ObservedUUID)o).id);
        return false;
    }
}
