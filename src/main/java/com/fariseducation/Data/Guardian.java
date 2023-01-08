package com.fariseducation.Data;

import com.fariseducation.Data.ObservedData.ObservedGeneric;

public class Guardian extends Person {
    private ObservedGeneric<String> email;
    private ObservedGeneric<Boolean> sendEmail;

    public Guardian(String firstName, String lastName, String email, boolean sendEmail) {
        super(firstName, lastName);

        this.email = new ObservedGeneric<String>(email);
        this.sendEmail = new ObservedGeneric<Boolean>(sendEmail);
    }

    public ObservedGeneric<String> getEmail() {
        return this.email;
    }
    public ObservedGeneric<Boolean> getSendEmailControl() {
        return this.sendEmail;
    }

    @Override
    public void save() {
        super.save("/MessageManagerData/Guardians/");
    }
}
