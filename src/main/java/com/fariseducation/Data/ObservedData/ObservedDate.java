package com.fariseducation.Data.ObservedData;

import java.time.LocalDate;

public class ObservedDate extends ObservedDatum {
    private LocalDate date;

    public ObservedDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return this.date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
        update();
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof ObservedDate) return this.date.equals(((ObservedDate)o).date);
        return false;
    }
}
