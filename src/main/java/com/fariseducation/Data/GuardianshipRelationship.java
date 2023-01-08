package com.fariseducation.Data;

import com.fariseducation.Data.ObservedData.ObservedGeneric;

public class GuardianshipRelationship extends ManagedDataSource {
    private ObservedGeneric<Guardian> guardian;
    private ObservedGeneric<Student> student;

    public GuardianshipRelationship(Guardian guardian, Student student) {
        this.guardian = new ObservedGeneric<Guardian>(guardian);
        this.student = new ObservedGeneric<Student>(student);
    }

    public ObservedGeneric<Guardian> getGuardian() {
        return this.guardian;
    }
    public ObservedGeneric<Student> getStudent() {
        return this.student;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof GuardianshipRelationship) {
            GuardianshipRelationship gr = (GuardianshipRelationship)o;

            return 
                this.guardian.equals(gr.guardian) &&
                this.student.equals(gr.student);
        }
        
        return false;
    }

    @Override
    public void save() {
        super.save("/MessageManagerData/GuardianshipRelationships/");
    }
}
