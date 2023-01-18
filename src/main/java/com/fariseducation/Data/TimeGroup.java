package com.fariseducation.Data;

import java.io.IOException;
import java.time.LocalDate;

import com.fariseducation.Data.ObservedData.ObservedDate;
import com.fariseducation.Data.ObservedData.ObservedGeneric;

public class TimeGroup extends ManagedDataSource {
    public static TimeGroup BLANK = new TimeGroup(
        "", 
        ObservedDate.of(0, 1, 1), 
        ObservedDate.of(0, 1, 1));

    private ObservedGeneric<String> name;
    private ObservedDate startDate;
    private ObservedDate endDate;
    private ObservedGeneric<String> template;

    //private static final long serialVersionUID = -3841962726599067523L; //REMOVE; POTENTIAL ERROR SOURCE

    public TimeGroup(String name, LocalDate startDate, LocalDate endDate) {
        this(name, new ObservedDate(startDate), new ObservedDate(endDate));
    }
    public TimeGroup(String name, ObservedDate startDate, ObservedDate endDate) {
        this.name = new ObservedGeneric<String>(name);
        this.startDate = startDate;
        this.endDate = endDate;
        this.template = new ObservedGeneric<String>("");
    }

    public ObservedGeneric<String> getName() {
        return this.name;
    }
    public ObservedDate getStartDate() {
        return this.startDate;
    }
    public ObservedDate getEndDate() {
        return this.endDate;
    }
    public ObservedGeneric<String> getTemplate() {
        return this.template;
    }

    public boolean isDateInRange(ObservedDate date) {
        return date.equals(this.startDate) || (date.isAfter(this.startDate) && date.isBefore(this.endDate));
    }
    public boolean containsSession(Session session) {
        return isDateInRange(session.getDate());
    }

    @Override
    public boolean equals(Object o) {
        // TODO Auto-generated method stub
        return false;
    } 

    @Override
    public String toString() {
        return this.name + ": (" + this.startDate.toString() + ", " + this.endDate.toString() + ")";
    }

    @Override
    public void save() {
        super.save("/MessageManagerData/TimeGroups/");
    } 
    
    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
    }
}