package com.fariseducation.Data;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.UUID;

import com.fariseducation.Data.ObservedData.ObservedDatum;

public abstract class ManagedData extends ObservedDatum implements Identifiable, Savable {
    private UUID id;

    public ManagedData() {
        this.id = UUID.randomUUID();
    }

    public UUID getUUID() {
        return this.id; //No concern about observing id because UUID is immutable
    };

    public void save(String loc) {
        try {
            FileOutputStream fileOut = new FileOutputStream(
                loc + getUUID() + ".ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.close();
            System.out.println("Serialized data is saved in " + loc + getUUID() + ".ser");
        } catch (IOException i) {
            i.printStackTrace();
        }
    };
}
