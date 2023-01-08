package com.fariseducation.Data;

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

    public TimeGroup(String name, ObservedDate startDate, ObservedDate endDate) {
        this.name = new ObservedGeneric<String>(name);
        this.startDate = startDate;
        this.endDate = endDate;
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

    public boolean isDateInRange(ObservedDate date) {
        return date.isAfter(this.startDate) && date.isBefore(this.endDate);
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
    public void save() {
        super.save("/MessageManagerData/TimeGroups/");
    }  
}