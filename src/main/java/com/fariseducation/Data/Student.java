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

    public void setMessageForTimeGroup(TimeGroup tg, String val) {
        this.messages.put(tg, val);
    }
    public String getMessageForTimeGroup(TimeGroup tg) {
        if(!this.messages.containsKey(tg)) this.messages.put(tg, generateMessageFromTemplate(tg));
        return this.messages.get(tg);
    }
    public void resetMessageForTimeGroup(TimeGroup tg) {
        this.messages.put(tg, generateMessageFromTemplate(tg));
    }

    private String generateMessageFromTemplate(TimeGroup tg) {
        String filledIn = tg.getTemplate().getVal() + " ";

        System.out.println("Start-------");

        while(true) {
            if(filledIn.contains("%SN%")) filledIn = filledIn.replace("%SN%", this.getFirstName().getVal());
            else break;
        }
        while(true) {
            if(filledIn.contains("%TN%")) filledIn = filledIn.replace("%TN%", StringManager.listify(
                DataManager.getInstance().getTutorsForStudent(new ObservedGeneric<Student>(this)).getStaticArray(),
                (Object o) -> {
                    Tutor t = (Tutor)o;

                    return t.getName().getVal();
                }));
            else break;
        }
        while(true) {
            if(filledIn.contains("%GN%")) filledIn = filledIn.replace("%GN%", StringManager.listify(
                DataManager.getInstance().getGuardiansForStudent(new ObservedGeneric<Student>(this)).getStaticArray(),
                (Object o) -> {
                    Guardian g = (Guardian)o;

                    return g.getName().getVal();
                }));
            else break;
        }
        //TD, TH, BT
        while(true) {
            if(filledIn.contains("%TD%")) filledIn = filledIn.replace("%TD%", StringManager.listify(
                DataManager.getInstance().getSessionsInTimeGroupForStudent(new ObservedGeneric<TimeGroup>(tg), new ObservedGeneric<Student>(this)).getStaticArray(),
                (Object o) -> {
                    Session s = (Session)o;

                    return 
                        s.getDate().getDate().getDayOfMonth() + 
                        (s.getHoursDuration().getVal() != 1 ? "(" + s.getHoursDuration().getVal() + ")" : "");
                }));
            else break;
        }
        while(true) {
            if(filledIn.contains("%TH%")) {
                double sum = 0;

                for(Session s : DataManager.getInstance().getSessionsInTimeGroupForStudent(new ObservedGeneric<TimeGroup>(tg), new ObservedGeneric<Student>(this))) {
                    sum += s.getHoursDuration().getVal();
                }

                filledIn = filledIn.replace("%TH%", sum + "");
            }
            else break;
        }
        while(true) {
            if(filledIn.contains("%BT%")) {
                double sum = 0;

                for(Session s : DataManager.getInstance().getSessionsInTimeGroupForStudent(new ObservedGeneric<TimeGroup>(tg), new ObservedGeneric<Student>(this))) {
                    sum += s.getHoursDuration().getVal()*s.getHourlyRate().getVal();
                }

                filledIn = filledIn.replace("%BT%", sum + "");
            }
            else break;
        }


        System.out.println("End-------");

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
