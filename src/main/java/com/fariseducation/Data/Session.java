package com.fariseducation.Data;

import com.fariseducation.Data.ObservedData.ObservedDate;
import com.fariseducation.Data.ObservedData.ObservedGeneric;

public class Session extends ManagedDataSource {
    private ObservedGeneric<Student> student;
    private ObservedGeneric<Tutor> tutor;
    private ObservedDate date;
    private ObservedGeneric<Double> hoursDuration;
    private ObservedGeneric<Double> hourlyRate;

    public Session(Student student, Tutor tutor, ObservedDate date, double hoursDuration, double hourlyRate) {
        this.student = new ObservedGeneric<Student>(student);
        this.tutor = new ObservedGeneric<Tutor>(tutor);
        this.date = date;
        this.hoursDuration = new ObservedGeneric<Double>(hoursDuration);
        this.hourlyRate = new ObservedGeneric<Double>(hourlyRate);
    }

    public ObservedGeneric<Student> getStudent() {
        return this.student;
    }
    public ObservedGeneric<Tutor> getTutor() {
        return this.tutor;
    }
    public ObservedDate getDate() {
        return this.date;
    }
    public ObservedGeneric<Double> getHoursDuration() {
        return this.hoursDuration;
    }
    public ObservedGeneric<Double> getHourlyRate() {
        return this.hourlyRate;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Session) {
            Session session = (Session)o;

            return 
                this.student.equals(session.student) &&
                this.tutor.equals(session.tutor) &&
                this.date.equals(session.date) &&
                this.hoursDuration.equals(session.hoursDuration) &&
                this.hourlyRate.equals(session.hourlyRate);
        }
        return false;
    }

    @Override
    public void save() {
        super.save("/MessageManagerData/Sessions/");
    }
}
