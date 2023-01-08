package com.fariseducation.Data;

import java.time.LocalDate;

import com.fariseducation.Data.ObservedData.DataManager;
import com.fariseducation.Data.ObservedData.ObservedDate;
import com.fariseducation.Data.ObservedData.ObservedGenericImmutable;
import com.fariseducation.Data.ObservedData.ObservedLiveList;

public class TimeGroup extends ManagedDataSource {
    public static TimeGroup BLANK = new TimeGroup(
        "", 
        LocalDate.of(0, 1, 1), 
        LocalDate.of(0, 1, 1));

    private ObservedGenericImmutable<String> name;
    private ObservedDate startDate;
    private ObservedDate endDate;

    public TimeGroup(String name, LocalDate startDate, LocalDate endDate) {
        this.name = new ObservedGenericImmutable<String>(name);
        this.startDate = new ObservedDate(startDate);
        this.endDate = new ObservedDate(endDate);

        /*GregorianCalendar gc = new GregorianCalendar();
        gc.set(0, 0, 0);
        gc.getTime();*/
    }

    public ObservedGenericImmutable<String> getName() {
        return this.name;
    }
    public ObservedDate getStartDate() {
        return this.startDate;
    }
    public ObservedDate getEndDate() {
        return this.endDate;
    }

    public boolean isDateInRange(LocalDate date) {
        return date.isAfter(this.startDate.getDate()) && date.isBefore(this.endDate.getDate());
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