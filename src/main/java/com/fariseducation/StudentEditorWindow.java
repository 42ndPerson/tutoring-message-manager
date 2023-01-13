package com.fariseducation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.fariseducation.Data.GuardianshipRelationship;
import com.fariseducation.Data.Session;
import com.fariseducation.Data.Student;
import com.fariseducation.Data.Tutor;
import com.fariseducation.Data.ObservedData.DataManager;
import com.fariseducation.Data.ObservedData.ObservedDate;
import com.fariseducation.Data.ObservedData.ObservedDatum;
import com.fariseducation.Data.ObservedData.ObservedGeneric;
import com.fariseducation.Data.ObservedData.ObservedLiveBoolean;
import com.fariseducation.Data.ObservedData.ObservedLiveList;
import com.fariseducation.UIBase.UIAlert;
import com.fariseducation.UIBase.UIComponent;
import com.fariseducation.UIBase.UIConditionalDisplay;
import com.fariseducation.UIBase.UIDropdown;
import com.fariseducation.UIBase.UIFrameConstraints;
import com.fariseducation.UIBase.UIGroup;
import com.fariseducation.UIBase.UIListBuilder;
import com.fariseducation.UIBase.UIScrollContainer;
import com.fariseducation.UIBase.UISpacer;
import com.fariseducation.UIBase.UIWindow;
import com.fariseducation.UIBase.UIEnums.UIAxis;
import com.fariseducation.UIBase.UITextElements.UIButton;
import com.fariseducation.UIBase.UITextElements.UILabel;
import com.fariseducation.UIBase.UITextElements.UITextField;

public class StudentEditorWindow {
    private UIWindow window;
    private Student student;
    private ObservedLiveBoolean studentIsRegistered;
    private ObservedLiveList<Session> sessions;

    private ObservedGeneric<Tutor> candiateNewSessionTutor = new ObservedGeneric<Tutor>(null);
    private ObservedGeneric<String> candidateNewSessionDate = new ObservedGeneric<String>("");
    private ObservedGeneric<String> candidateNewSessionDuration = new ObservedGeneric<String>("");
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
        this.sessions = DataManager.getInstance().getSessionsForStudent(new ObservedGeneric<Student>(this.student));

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
        this.sessions = DataManager.getInstance().getSessionsForStudent(new ObservedGeneric<Student>(this.student));

        spawn();
    }

    private void spawn() {
        this.window = new UIWindow("Student Editor", new UIFrameConstraints(700, 500), new UIComponent[]{
            new UIGroup(UIAxis.HORIZONTAL, new UIComponent[]{
                new UIGroup(UIAxis.VERTICAL, new UIComponent[]{
                    new UIGroup(UIAxis.HORIZONTAL, new UIComponent[]{
                        new UISpacer(3),
                        new UILabel("First Name"),
                        new UISpacer()
                    }),
                    new UITextField(this.student.getFirstName(), false)
                        .onTyping((String text) -> {
                            this.student.getFirstName().setVal(text);;
                        })
                        .setMaxSize(Integer.MAX_VALUE, 35),
                    new UIGroup(UIAxis.HORIZONTAL, new UIComponent[]{
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
                            new UIGroup(UIAxis.HORIZONTAL, new UIComponent[]{
                                new UIButton("Cancel")
                                    .onPress(() -> {
                                        this.window.closeWindow();
                                    }),
                                new UIButton("Save")
                                .onPress(() -> {
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
                                new UIGroup(UIAxis.VERTICAL, new UIComponent[]{
                                    new UIListBuilder<GuardianshipRelationship>(
                                        DataManager.getInstance().getGuardianshipRealtionshipsForStudent(new ObservedGeneric<Student>(this.student)), 
                                        (GuardianshipRelationship gr) -> {
                                            return new UIGroup(UIAxis.HORIZONTAL, new UIComponent[]{
                                                new UILabel(gr.getGuardian().getVal().getName()),
                                                new UISpacer(),
                                                new UIButton("X")
                                                    .onPress(() -> {
                                                        DataManager.getInstance().deleteDatum(gr);
                                                    })
                                        });
                                    }, 
                                    UIAxis.VERTICAL)
                            })
                        )
                    }).setPreferredSize(null, 5000)
                }),
                new UIGroup(UIAxis.VERTICAL, new UIComponent[]{
                    new UIConditionalDisplay(
                        this.studentIsRegistered,
                        new UIComponent[]{
                            new UIGroup(UIAxis.HORIZONTAL, new UIComponent[]{
                                new UISpacer(5),
                                new UILabel("Sessions"),
                                new UISpacer()
                            }),
                            //Existing Sessions
                            new UIScrollContainer(
                                new UIGroup(UIAxis.VERTICAL, new UIComponent[]{
                                    new UIGroup(UIAxis.HORIZONTAL, new UIComponent[]{
                                        new UIGroup(UIAxis.VERTICAL, new UIComponent[]{
                                            new UILabel("Tutor")
                                                .format(false, false, -1),
                                            new UIListBuilder<Session>(
                                                this.sessions, 
                                                (Session session) -> {
                                                    return new UILabel(session.getTutor().getVal().getName())
                                                        .setMaxSize(500, 111);
                                                }, 
                                                UIAxis.VERTICAL),
                                            new UISpacer()
                                        }),
                                        new UIGroup(UIAxis.VERTICAL, new UIComponent[]{
                                            new UILabel("Hours")
                                                .format(false, false, -1),
                                            new UIListBuilder<Session>(
                                                this.sessions, 
                                                (Session session) -> {
                                                    return new UILabel(session.getHoursDuration())
                                                        .setMaxSize(500, 111);
                                                }, 
                                                UIAxis.VERTICAL),
                                            new UISpacer()
                                        }),
                                        new UIGroup(UIAxis.VERTICAL, new UIComponent[]{
                                            new UILabel("Date")
                                                .format(false, false, -1),
                                            new UIListBuilder<Session>(
                                                this.sessions, 
                                                (Session session) -> {
                                                    return new UILabel(session.getDate().toString())
                                                        .setMaxSize(500, 111);
                                                }, 
                                                UIAxis.VERTICAL),
                                            new UISpacer()
                                        }),
                                        new UIGroup(UIAxis.VERTICAL, new UIComponent[]{
                                            new UILabel("Delete")
                                                .format(false, false, -1),
                                            new UIListBuilder<Session>(
                                                this.sessions, 
                                                (Session session) -> {
                                                    return new UIButton("X")
                                                        .onPress(() -> {
                                                            DataManager.getInstance().deleteDatum(session);
                                                        })
                                                        .setMaxSize(500, 30);
                                                }, 
                                                UIAxis.VERTICAL),
                                            new UISpacer()
                                        })
                                    }),
                                }).setPreferredSize(null, 5000)
                            ),
                            //New Session
                            new UIGroup(UIAxis.HORIZONTAL, new UIComponent[]{
                                new UIGroup(UIAxis.VERTICAL, new UIComponent[]{
                                    new UIGroup(UIAxis.HORIZONTAL, new UIComponent[]{
                                        new UISpacer(5),
                                        new UILabel("Tutor")
                                            .format(false, false, -1),
                                        new UISpacer()
                                    }),
                                    new UIDropdown<Tutor>(
                                        DataManager.getInstance().getTutors(), 
                                        (Tutor tutor) -> {
                                            return tutor.getName().getVal();
                                        })
                                            .onSelect((Tutor tutor) -> {
                                                if(tutor != null) this.candiateNewSessionTutor.setVal(tutor);
                                            })
                                }),
                                new UIGroup(UIAxis.VERTICAL, new UIComponent[]{
                                    new UIGroup(UIAxis.HORIZONTAL, new UIComponent[]{
                                        new UISpacer(5),
                                        new UILabel("Hours")
                                            .format(false, false, -1),
                                        new UISpacer()
                                    }),
                                    new UITextField(this.candidateNewSessionDuration, false)
                                        .onTyping((String val) -> {
                                            this.candidateNewSessionDuration.setVal(val);
                                        })
                                }),
                                new UIGroup(UIAxis.VERTICAL, new UIComponent[]{
                                    new UIGroup(UIAxis.HORIZONTAL, new UIComponent[]{
                                        new UISpacer(5),
                                        new UILabel("Date")
                                            .format(false, false, -1),
                                        new UISpacer()
                                    }),
                                    new UITextField("mm/dd/yyyy", false)
                                        .onTyping((String val) -> {
                                            this.candidateNewSessionDate.setVal(val);
                                        })
                                }),
                                new UIGroup(UIAxis.VERTICAL, new UIComponent[]{
                                    new UILabel(""),
                                    new UIButton("Add")
                                        .onPress(() -> {
                                            try {
                                                ObservedDate validDate = new ObservedDate(LocalDate.parse(
                                                    this.candidateNewSessionDate.getVal(), 
                                                    DateTimeFormatter.ofPattern("MM/dd/yyyy")));
    
                                                try {
                                                    double validDuration = Double.parseDouble(this.candidateNewSessionDuration.getVal());
    
                                                    DataManager.getInstance().registerDatum(
                                                        new Session(
                                                            this.student, 
                                                            this.candiateNewSessionTutor.getVal(), 
                                                            validDate,
                                                            validDuration)
                                                    );
                                                } catch (NumberFormatException e) {
                                                    UIAlert.alert("Invalid Duration");
                                                }
                                            } catch (DateTimeParseException e) {
                                                UIAlert.alert("Invalid Date");
                                            }
                                        })
                                })
                            })
                        })
                })
            })
        });
    }
}
