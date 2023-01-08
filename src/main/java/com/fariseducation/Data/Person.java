package com.fariseducation.Data;

import com.fariseducation.Data.ObservedData.DataObserver;
import com.fariseducation.Data.ObservedData.ObservedGenericImmutable;

public abstract class Person extends ManagedDataSource implements DataObserver {
    private ObservedGenericImmutable<String> firstName;
    private ObservedGenericImmutable<String> lastName;
    private ObservedGenericImmutable<String> fullName;

    public Person(String fn, String ln) {
        super();

        this.firstName = new ObservedGenericImmutable<String>(fn);
        this.lastName = new ObservedGenericImmutable<String>(ln);
        this.fullName = new ObservedGenericImmutable<String>(fn + " " + ln);
    }

    public ObservedGenericImmutable<String> getName() {
        return fullName;
    }

    public ObservedGenericImmutable<String> getFirstName() {
        return this.firstName;
    }
    public void setFirstName(String fn) {
        this.firstName.setVal(fn);
    }
    public ObservedGenericImmutable<String> getLastName() {
        return this.lastName;
    }
    public void setLastName(String ln) {
        this.firstName.setVal(ln);
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

    @Override
    public void updateAfterDataChange() {
        this.fullName.setVal(this.firstName.getVal() + " " + this.lastName.getVal());
    }
}
