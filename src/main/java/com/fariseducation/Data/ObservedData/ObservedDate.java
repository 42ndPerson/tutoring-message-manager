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

    public boolean isAfter(ObservedDate date) {
        return this.date.isAfter(date.date);
    }
    public boolean isBefore(ObservedDate date) {
        return this.date.isBefore(date.date);
    }

    public static ObservedDate of(int y, int m, int dom) {
        return new ObservedDate(LocalDate.of(y, m, dom));
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof ObservedDate) return this.date.equals(((ObservedDate)o).date);
        return false;
    }

    @Override
    public String toString() {
        return this.date.toString();
    }
}
