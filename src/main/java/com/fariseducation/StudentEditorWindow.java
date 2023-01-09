package com.fariseducation;

import com.fariseducation.Data.GuardianshipRelationship;
import com.fariseducation.Data.Session;
import com.fariseducation.Data.Student;
import com.fariseducation.Data.ObservedData.DataManager;
import com.fariseducation.Data.ObservedData.ObservedDatum;
import com.fariseducation.Data.ObservedData.ObservedGeneric;
import com.fariseducation.Data.ObservedData.ObservedLiveBoolean;
import com.fariseducation.UIBase.UIAlert;
import com.fariseducation.UIBase.UIComponent;
import com.fariseducation.UIBase.UIConditionalDisplay;
import com.fariseducation.UIBase.UIFrameConstraints;
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
    private ObservedLiveBoolean studentIsRegistered;
    //private ObservedUnlockedList<GuardianshipRelationship> newGRBuffer = new ObservedUnlockedList<GuardianshipRelationship>();
    //private ObservedUnlockedList<Session> newSessionBuffer = new ObservedUnlockedList<Session>();

    public StudentEditorWindow() {
        this.student = new Student("", "");
        this.studentIsRegistered = new ObservedLiveBoolean(
            new ObservedDatum[] {
                (ObservedDatum)DataManager.getInstance().getStudents(),
            }, 
            () -> {
                return DataManager.getInstance().getStudents().contains(this.student);
            });

        spawn();
    }
    public StudentEditorWindow(Student student) {
        this.student = student;
        this.studentIsRegistered = new ObservedLiveBoolean(
            new ObservedDatum[] {
                (ObservedDatum)DataManager.getInstance().getStudents(),
            }, 
            () -> {
                return DataManager.getInstance().getStudents().contains(this.student);
            });

        spawn();
    }

    private void spawn() {
        this.window = new UIWindow("Student Editor", new UIFrameConstraints(700, 500), new UIComponent[]{
            new UIGroup(UIAxis.HORIZONTAL, UIAlignment.NONE, new UIComponent[]{
                new UIGroup(UIAxis.VERTICAL, UIAlignment.NONE, new UIComponent[]{
                    new UIGroup(UIAxis.HORIZONTAL, UIAlignment.NONE, new UIComponent[]{
                        new UISpacer(3),
                        new UILabel("First Name"),
                        new UISpacer()
                    }),
                    new UITextField(this.student.getFirstName(), false)
                        .onTyping((String text) -> {
                            this.student.getFirstName().setVal(text);;
                        })
                        .setMaxSize(Integer.MAX_VALUE, 35),
                    new UIGroup(UIAxis.HORIZONTAL, UIAlignment.NONE, new UIComponent[]{
                        new UISpacer(3),
                        new UILabel("Last Name"),
                        new UISpacer()
                    }),
                    new UITextField(this.student.getLastName(), false)
                        .onTyping((String text) -> {
                            this.student.getLastName().setVal(text);;
                        })
                        .setMaxSize(Integer.MAX_VALUE, 35),
                    new UIConditionalDisplay(
                        this.studentIsRegistered.inverse(),
                        new UIComponent[]{
                        new UIGroup(UIAxis.HORIZONTAL, UIAlignment.NONE, new UIComponent[]{
                            new UIButton("Cancel")
                                .onPress(() -> {
                                    this.window.closeWindow();
                                }),
                            new UIButton("Save")
                            .onPress(() -> {
                                System.out.println("---");
                                System.out.println(this.student.getFirstName().getVal());
                                System.out.println(this.student.getLastName().getVal());
                                if(
                                    !this.student.getFirstName().getVal().equals("") && 
                                    !this.student.getLastName().getVal().equals("")) 
                                    {
                                    DataManager.getInstance().registerDatum(this.student);
                                } else {
                                    UIAlert.alert("Invalid name");
                                }
                            })
                        }),
                        new UISpacer()
                    }),
                    new UIConditionalDisplay(
                        this.studentIsRegistered,
                        new UIComponent[]{
                            new UISpacer(10),
                            new UIGroup(UIAxis.HORIZONTAL, new UIComponent[]{
                                new UISpacer(5),
                                new UILabel("Guardians"),
                                new UISpacer()
                            }),
                            new UIScrollContainer(
                                new UIGroup(UIAxis.VERTICAL, UIAlignment.NONE, new UIComponent[]{
                                    new UIListBuilder<GuardianshipRelationship>(
                                        DataManager.getInstance().getGuardianshipRealtionshipsForStudent(new ObservedGeneric<Student>(this.student)), 
                                        (GuardianshipRelationship gr) -> {
                                            return new UIGroup(UIAxis.HORIZONTAL, UIAlignment.NONE, new UIComponent[]{
                                                new UILabel(gr.getGuardian().getVal().getName()),
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
                    }).setPreferredSize(null, 5000)
                }),
                new UIGroup(UIAxis.VERTICAL, UIAlignment.NONE, new UIComponent[]{
                    new UIConditionalDisplay(
                        this.studentIsRegistered,
                        new UIComponent[]{
                            new UIGroup(UIAxis.HORIZONTAL, new UIComponent[]{
                                new UISpacer(5),
                                new UILabel("Sessions"),
                                new UISpacer()
                            }),
                            new UIScrollContainer(
                                new UIGroup(UIAxis.VERTICAL, UIAlignment.NONE, new UIComponent[]{
                                    new UIGroup(UIAxis.HORIZONTAL, UIAlignment.NONE, new UIComponent[]{
                                        new UIGroup(UIAxis.VERTICAL, UIAlignment.NONE, new UIComponent[]{
                                            new UILabel("Tutor")
                                                .format(false, false, -1),
                                            new UIListBuilder<Session>(
                                                DataManager.getInstance().getSessionsForStudent(new ObservedGeneric<Student>(this.student)), 
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
                                                DataManager.getInstance().getSessionsForStudent(new ObservedGeneric<Student>(this.student)), 
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
                                                DataManager.getInstance().getSessionsForStudent(new ObservedGeneric<Student>(this.student)), 
                                                (Session session) -> {
                                                    return new UILabel(session.getDate().toString());
                                                }, 
                                                UIAxis.VERTICAL, 
                                                UIAlignment.LEADING)
                                        }),
                                        new UIGroup(UIAxis.VERTICAL, UIAlignment.NONE, new UIComponent[]{
                                            new UILabel("Delete")
                                                .format(false, false, -1),
                                            new UIListBuilder<Session>(
                                                DataManager.getInstance().getSessionsForStudent(new ObservedGeneric<Student>(this.student)), 
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
                            )
                        })
                })
            })
        });
    }
}
