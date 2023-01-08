package com.fariseducation.Data;

public class Student extends Person {
    public static Student BLANK = new Student("","");

    public Student(String firstName, String lastName) {
        super(firstName, lastName);
    }

    @Override
    public void save() {
        super.save("/MessageManagerData/Students/");
    }
}
