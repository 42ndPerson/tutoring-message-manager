package com.fariseducation.Data;

import java.io.IOException;
import java.util.HashMap;

import com.fariseducation.Data.ObservedData.DataManager;
import com.fariseducation.Data.ObservedData.ObservedGeneric;

public class Student extends Person {
    public static Student BLANK = new Student("","");

    private HashMap<TimeGroup,String> messages = new HashMap<TimeGroup,String>();

    private static final long serialVersionUID = 3729241471652888975L;

    public Student(String firstName, String lastName) {
        super(firstName, lastName);
    }

    public String getMessageForTimeGroup(TimeGroup tg) {
        if(!this.messages.containsKey(tg)) this.messages.put(tg, generateMessageFromTemplate(tg));
        return this.messages.get(tg);
    }

    private String generateMessageFromTemplate(TimeGroup tg) {
        String filledIn = tg.getTemplate().getVal() + " ";

        while(true) {
            if(filledIn.contains("%SN%")) filledIn.replace("%SN%", this.getFirstName().getVal());
            else break;
        }
        while(true) {
            if(filledIn.contains("%TN%")) filledIn.replace("%TN%", StringManager.listify(
                DataManager.getInstance().getTutorsForStudent(new ObservedGeneric<Student>(this)).getStaticArray(),
                (Object o) -> {
                    Tutor t = (Tutor)o;

                    return t.getName().getVal();
                }));
            else break;
        }
        while(true) {
            if(filledIn.contains("%GN%")) filledIn.replace("%GN%", StringManager.listify(
                DataManager.getInstance().getGuardiansForStudent(new ObservedGeneric<Student>(this)).getStaticArray(),
                (Object o) -> {
                    Guardian t = (Guardian)o;

                    return t.getName().getVal();
                }));
            else break;
        }

        return filledIn;
    }

    @Override
    public void save() {
        super.save("/MessageManagerData/Students/");
    }

    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        this.messages = new HashMap<TimeGroup,String>();
    }
}
