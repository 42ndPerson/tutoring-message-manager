package com.fariseducation.Data;

import com.fariseducation.Data.ObservedData.ObservedBoolean;
import com.fariseducation.Data.ObservedData.ObservedGenericImmutable;

public class Guardian extends Person {
    private ObservedGenericImmutable<String> email;
    private ObservedBoolean sendEmail;

    public Guardian(String firstName, String lastName, String email, boolean sendEmail) {
        super(firstName, lastName);

        this.email = new ObservedGenericImmutable<String>(email);
        this.sendEmail = new ObservedBoolean(sendEmail);
    }

    public ObservedGenericImmutable<String> getEmail() {
        return this.email;
    }
    public ObservedBoolean getSendEmailControl() {
        return this.sendEmail;
    }

    @Override
    public void save() {
        super.save("/MessageManagerData/Guardians/");
    }
}
