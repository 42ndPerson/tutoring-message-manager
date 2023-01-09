package com.fariseducation.Data;

import com.fariseducation.Data.ObservedData.DataObserver;
import com.fariseducation.Data.ObservedData.ObservedGeneric;

public abstract class Person extends ManagedDataSource implements DataObserver {
    private ObservedGeneric<String> firstName;
    private ObservedGeneric<String> lastName;
    private ObservedGeneric<String> fullName;

    public Person(String fn, String ln) {
        super();

        this.firstName = new ObservedGeneric<String>(fn);
        this.lastName = new ObservedGeneric<String>(ln);
        this.fullName = new ObservedGeneric<String>(fn + " " + ln);

        this.firstName.addObserver(this);
        this.lastName.addObserver(this);
    }

    public ObservedGeneric<String> getName() {
        return fullName;
    }

    public ObservedGeneric<String> getFirstName() {
        return this.firstName;
    }
    public ObservedGeneric<String> getLastName() {
        return this.lastName;
    }

    @Override 
    public void updateAfterDataChange() {
        this.fullName.setVal(this.firstName.getVal() + " " + this.lastName.getVal());
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Person) {
            Person person = (Person)o;

            return 
                this.firstName.equals(person.firstName) &&
                this.lastName.equals(person.lastName);
        }
        
        return false;
    }
}
