package com.fariseducation.Data;


public class Tutor extends Person {
    public Tutor(String fn, String ln) {
        super(fn, ln);
    }

    @Override
    public void save() {
        super.save("/MessageManagerData/Tutors/");
    }
}
