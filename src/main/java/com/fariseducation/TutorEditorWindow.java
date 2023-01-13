package com.fariseducation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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

public class TutorEditorWindow {
    private UIWindow window;
    private Tutor tutor;
    private ObservedLiveBoolean tutorIsRegistered;
    private ObservedLiveList<Session> sessions;

    private ObservedGeneric<Student> candiateNewSessionStudent = new ObservedGeneric<Student>(null);
    private ObservedGeneric<String> candidateNewSessionDate = new ObservedGeneric<String>("");
    private ObservedGeneric<String> candidateNewSessionDuration = new ObservedGeneric<String>("");
    //private ObservedUnlockedList<GuardianshipRelationship> newGRBuffer = new ObservedUnlockedList<GuardianshipRelationship>();
    //private ObservedUnlockedList<Session> newSessionBuffer = new ObservedUnlockedList<Session>();

    public TutorEditorWindow() {
        this.tutor = new Tutor("", "");
        this.tutorIsRegistered = new ObservedLiveBoolean(
            new ObservedDatum[] {
                (ObservedDatum)DataManager.getInstance().getTutors(),
            }, 
            () -> {
                return DataManager.getInstance().getTutors().contains(this.tutor);
            });
        this.sessions = DataManager.getInstance().getSessionsForTutor(new ObservedGeneric<Tutor>(this.tutor));

        spawn();
    }
    public TutorEditorWindow(Tutor tutor) {
        this.tutor = tutor;
        this.tutorIsRegistered = new ObservedLiveBoolean(
            new ObservedDatum[] {
                (ObservedDatum)DataManager.getInstance().getTutors(),
            }, 
            () -> {
                return DataManager.getInstance().getTutors().contains(this.tutor);
            });
        this.sessions = DataManager.getInstance().getSessionsForTutor(new ObservedGeneric<Tutor>(this.tutor));

        spawn();
    }

    private void spawn() {
        this.window = new UIWindow("Tutor Editor", new UIFrameConstraints(700, 500), new UIComponent[]{
            new UIGroup(UIAxis.VERTICAL, new UIComponent[]{
                new UIGroup(UIAxis.HORIZONTAL, new UIComponent[]{
                    new UISpacer(3),
                    new UILabel("First Name"),
                    new UISpacer()
                }),
                new UITextField(this.tutor.getFirstName(), false)
                    .onTyping((String text) -> {
                        this.tutor.getFirstName().setVal(text);;
                    })
                    .setMaxSize(Integer.MAX_VALUE, 35),
                new UIGroup(UIAxis.HORIZONTAL, new UIComponent[]{
                    new UISpacer(3),
                    new UILabel("Last Name"),
                    new UISpacer()
                }),
                new UITextField(this.tutor.getLastName(), false)
                    .onTyping((String text) -> {
                        this.tutor.getLastName().setVal(text);;
                    })
                    .setMaxSize(Integer.MAX_VALUE, 35),
                new UIConditionalDisplay(
                    this.tutorIsRegistered.inverse(),
                    new UIComponent[]{
                    new UIGroup(UIAxis.HORIZONTAL, new UIComponent[]{
                        new UIButton("Cancel")
                            .onPress(() -> {
                                this.window.closeWindow();
                            }),
                        new UIButton("Save")
                        .onPress(() -> {
                            if(
                                !this.tutor.getFirstName().getVal().equals("") && 
                                !this.tutor.getLastName().getVal().equals("")) 
                                {
                                DataManager.getInstance().registerDatum(this.tutor);
                            } else {
                                UIAlert.alert("Invalid name");
                            }
                        })
                    }),
                    new UISpacer()
                }),
            }),
            new UIGroup(UIAxis.VERTICAL, new UIComponent[]{
                new UIConditionalDisplay(
                    this.tutorIsRegistered,
                    new UIComponent[]{
                        new UIGroup(UIAxis.HORIZONTAL, new UIComponent[]{
                            new UISpacer(5),
                            new UILabel("Sessions"),
                            new UISpacer()
                        }),
                        new UIScrollContainer(
                            new UIGroup(UIAxis.VERTICAL, new UIComponent[]{
                                new UIGroup(UIAxis.HORIZONTAL, new UIComponent[]{
                                    new UIGroup(UIAxis.VERTICAL, new UIComponent[]{
                                        new UILabel("Student")
                                            .format(false, false, -1),
                                        new UIListBuilder<Session>(
                                            this.sessions, 
                                            (Session session) -> {
                                                return new UILabel(session.getStudent().getVal().getName())
                                                    .setMinSize(500, 111);
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
                                                    .setMinSize(500, 111);
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
                                                    .setMinSize(500, 111);
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
                            })
                        )
                            .setPreferredSize(5000, 5000),
                        new UISpacer(),
                        //New Session
                        new UIGroup(UIAxis.HORIZONTAL, new UIComponent[]{
                            new UIGroup(UIAxis.VERTICAL, new UIComponent[]{
                                new UIGroup(UIAxis.HORIZONTAL, new UIComponent[]{
                                    new UISpacer(5),
                                    new UILabel("Student")
                                        .format(false, false, -1),
                                    new UISpacer()
                                }),
                                new UIDropdown<Student>(
                                    DataManager.getInstance().getStudents(), 
                                    (Student student) -> {
                                        return student.getName().getVal();
                                    })
                                        .onSelect((Student student) -> {
                                            if(student != null) this.candiateNewSessionStudent.setVal(student);
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
                                                        this.candiateNewSessionStudent.getVal(), 
                                                        this.tutor, 
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
        });
    }
}
