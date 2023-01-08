package com.fariseducation;

import com.fariseducation.Data.GuardianshipRelationship;
import com.fariseducation.Data.Session;
import com.fariseducation.Data.Student;
import com.fariseducation.Data.ObservedData.DataManager;
import com.fariseducation.Data.ObservedData.ObservedGenericImmutable;
import com.fariseducation.Data.ObservedData.ObservedUnlockedList;
import com.fariseducation.UIBase.UIAlert;
import com.fariseducation.UIBase.UIComponent;
import com.fariseducation.UIBase.UIGroup;
import com.fariseducation.UIBase.UIListBuilder;
import com.fariseducation.UIBase.UIScrollContainer;
import com.fariseducation.UIBase.UISpacer;
import com.fariseducation.UIBase.UIWindow;
import com.fariseducation.UIBase.UIEnums.UIAlignment;
import com.fariseducation.UIBase.UIEnums.UIAxis;
import com.fariseducation.UIBase.UITextElements.UIButton;
import com.fariseducation.UIBase.UITextElements.UILabel;
import com.fariseducation.UIBase.UITextElements.UITextField;

public class StudentEditorWindow {
    private UIWindow window;
    private Student student;
    private String firstName;
    private String lastName;
    private ObservedUnlockedList<GuardianshipRelationship> newGRBuffer = new ObservedUnlockedList<GuardianshipRelationship>();
    private ObservedUnlockedList<Session> newSessionBuffer = new ObservedUnlockedList<Session>();

    public StudentEditorWindow() {
        this.student = new Student("", "");

        this.firstName = "";
        this.lastName = "";

        spawn();
    }

    private void spawn() {
        this.window = new UIWindow("Student Editor", new UIComponent[]{
            new UIGroup(UIAxis.HORIZONTAL, UIAlignment.NONE, new UIComponent[]{
                new UIGroup(UIAxis.VERTICAL, UIAlignment.NONE, new UIComponent[]{
                    new UILabel("First Name"),
                    new UITextField(this.firstName, false)
                        .onTyping((String text) -> {
                            this.firstName = text;
                        })
                        .setMaxSize(3000, 100),
                    new UISpacer(10),
                    new UILabel("Guardians"),
                    new UIScrollContainer(
                        new UIGroup(UIAxis.VERTICAL, UIAlignment.NONE, new UIComponent[]{
                            new UIListBuilder<GuardianshipRelationship>(
                                DataManager.getInstance().getGuardianshipRealtionshipsForStudent(new ObservedGenericImmutable<Student>(this.student)), 
                                (GuardianshipRelationship gr) -> {
                                    return new UIGroup(UIAxis.HORIZONTAL, UIAlignment.NONE, new UIComponent[]{
                                        new UILabel(gr.getGuardian().getName()),
                                        new UISpacer(),
                                        new UIButton("X")
                                            .onPress(() -> {
                                                DataManager.getInstance().deleteDatum(gr);
                                            })
                                    });
                                }, 
                                UIAxis.VERTICAL, 
                                UIAlignment.LEADING)
                                //Add new parent option here
                        })
                    )
                }),
                new UIGroup(UIAxis.VERTICAL, UIAlignment.NONE, new UIComponent[]{
                    new UILabel("Last Name"),
                    new UITextField(this.lastName, false)
                        .onTyping((String text) -> {
                            this.lastName = text;
                        })
                        .setMaxSize(3000, 100),
                    new UILabel("Sessions"),
                    new UIScrollContainer(
                        new UIGroup(UIAxis.VERTICAL, UIAlignment.NONE, new UIComponent[]{
                            new UIGroup(UIAxis.HORIZONTAL, UIAlignment.NONE, new UIComponent[]{
                                new UIGroup(UIAxis.VERTICAL, UIAlignment.NONE, new UIComponent[]{
                                    new UILabel("Tutor")
                                        .format(false, false, -1),
                                    new UIListBuilder<Session>(
                                        DataManager.getInstance().getSessionsForStudent(new ObservedGenericImmutable<Student>(this.student)), 
                                        (Session session) -> {
                                            return new UILabel(session.getTutor().getVal().getName());
                                        }, 
                                        UIAxis.VERTICAL, 
                                        UIAlignment.LEADING)
                                }),
                                new UIGroup(UIAxis.VERTICAL, UIAlignment.NONE, new UIComponent[]{
                                    new UILabel("Hours")
                                        .format(false, false, -1),
                                    new UIListBuilder<Session>(
                                        DataManager.getInstance().getSessionsForStudent(new ObservedGenericImmutable<Student>(this.student)), 
                                        (Session session) -> {
                                            return new UITextField(
                                                session.getHoursDuration().getVal()+"", //Needs observable fix
                                                false);
                                                /*.onTyping(
                                                    (String val) -> {
                                                        try {
                                                            session.set
                                                        }
                                                    });*/
                                        }, 
                                        UIAxis.VERTICAL, 
                                        UIAlignment.LEADING)
                                }),
                                new UIGroup(UIAxis.VERTICAL, UIAlignment.NONE, new UIComponent[]{
                                    new UILabel("Date")
                                        .format(false, false, -1),
                                    new UIListBuilder<Session>(
                                        DataManager.getInstance().getSessionsForStudent(new ObservedGenericImmutable<Student>(this.student)), 
                                        (Session session) -> {
                                            return new UILabel(session.getDate().getVal().toString());
                                        }, 
                                        UIAxis.VERTICAL, 
                                        UIAlignment.LEADING)
                                }),
                                new UIGroup(UIAxis.VERTICAL, UIAlignment.NONE, new UIComponent[]{
                                    new UILabel("Delete")
                                        .format(false, false, -1),
                                    new UIListBuilder<Session>(
                                        DataManager.getInstance().getSessionsForStudent(new ObservedGenericImmutable<Student>(this.student)), 
                                        (Session session) -> {
                                            return new UIButton("X")
                                                .onPress(() -> {
                                                    DataManager.getInstance().deleteDatum(session);
                                                });
                                        }, 
                                        UIAxis.VERTICAL, 
                                        UIAlignment.LEADING)
                                })
                            }),
                            new UIButton("")
                            //Add new session option here
                        })
                    ),
                    new UIGroup(UIAxis.HORIZONTAL, UIAlignment.NONE, new UIComponent[]{
                        new UIButton("Cancel")
                            .onPress(() -> {
                                this.window.closeWindow();
                            }),
                        new UIButton("Save")
                        .onPress(() -> {
                            if(!this.firstName.equals("") && !this.lastName.equals("")) {
                                DataManager.getInstance().registerDatum(this.student);
                                this.window.closeWindow();
                            } else {
                                UIAlert.alert("Invalid name");
                            }
                        })
                    })
                })
            })
        });
    }
}
