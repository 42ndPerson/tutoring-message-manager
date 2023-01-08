package com.fariseducation.Data;

public abstract class Person extends ManagedDataSource {
    private String firstName;
    private String lastName;
    private String fullName;

    public Person(String fn, String ln) {
        super();

        this.firstName = fn;
        this.lastName = ln;
        this.fullName = fn + " " + ln;
    }

    public String getName() {
        return fullName;
    }

    public String getFirstName() {
        return this.firstName;
    }
    public void setFirstName(String fn) {
        this.firstName = fn;
        update();
    }
    public String getLastName() {
        return this.lastName;
    }
    public void setLastName(String ln) {
        this.firstName = ln;
        update();
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
