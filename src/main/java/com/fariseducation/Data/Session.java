package com.fariseducation.Data;

import java.time.LocalDate;

import com.fariseducation.Data.ObservedData.ObservedDate;
import com.fariseducation.Data.ObservedData.ObservedGenericImmutable;

public class Session extends ManagedDataSource {
    private ObservedGenericImmutable<Student> student;
    private ObservedGenericImmutable<Tutor> tutor;
    private ObservedDate date;
    private ObservedGenericImmutable<Double> hoursDuration;

    public Session(Student student, Tutor tutor, LocalDate date, double hoursDuration) {
        this.student = new ObservedGenericImmutable<Student>(student);
        this.tutor = new ObservedGenericImmutable<Tutor>(tutor);
        this.date = new ObservedDate(date);
        this.hoursDuration = new ObservedGenericImmutable<Double>(hoursDuration);
    }

    public ObservedGenericImmutable<Student> getStudent() {
        return this.student;
    }
    public ObservedGenericImmutable<Tutor> getTutor() {
        return this.tutor;
    }
    public ObservedDate getDate() {
        return this.date;
    }
    public ObservedGenericImmutable<Double> getHoursDuration() {
        return this.hoursDuration;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Session) {
            Session session = (Session)o;

            return 
                this.student.equals(session.student) &&
                this.tutor.equals(session.tutor) &&
                this.date.equals(session.date) &&
                this.hoursDuration.equals(session.hoursDuration);
        }
        return false;
    }

    @Override
    public void save() {
        super.save("/MessageManagerData/Sessions/");
    }
}
