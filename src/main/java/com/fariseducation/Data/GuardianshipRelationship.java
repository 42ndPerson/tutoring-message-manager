package com.fariseducation.Data;

public class GuardianshipRelationship extends ManagedDataSource {
    private Guardian guardian;
    private Student student;

    public GuardianshipRelationship(Guardian guardian, Student student) {
        this.guardian = guardian;
        this.student = student;
    }

    public Guardian getGuardian() {
        return this.guardian;
    }
    public Student getStudent() {
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
