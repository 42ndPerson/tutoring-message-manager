package com.fariseducation;

import com.fariseducation.Data.Guardian;
import com.fariseducation.Data.GuardianshipRelationship;
import com.fariseducation.Data.Student;
import com.fariseducation.Data.ObservedData.DataManager;
import com.fariseducation.Data.ObservedData.ObservedDatum;
import com.fariseducation.Data.ObservedData.ObservedGeneric;
import com.fariseducation.Data.ObservedData.ObservedLiveBoolean;
import com.fariseducation.UIBase.UIAlert;
import com.fariseducation.UIBase.UIComponent;
import com.fariseducation.UIBase.UIConditionalDisplay;
import com.fariseducation.UIBase.UIDropdown;
import com.fariseducation.UIBase.UIFrameConstraints;
import com.fariseducation.UIBase.UIGroup;
import com.fariseducation.UIBase.UIListBuilder;
import com.fariseducation.UIBase.UIScrollContainer;
import com.fariseducation.UIBase.UISpacer;
import com.fariseducation.UIBase.UIToggle;
import com.fariseducation.UIBase.UIWindow;
import com.fariseducation.UIBase.UIEnums.UIAxis;
import com.fariseducation.UIBase.UITextElements.UIButton;
import com.fariseducation.UIBase.UITextElements.UILabel;
import com.fariseducation.UIBase.UITextElements.UITextField;

public class GuardianEditorWindow {
    private UIWindow window;
    private Guardian guardian;
    private ObservedLiveBoolean guardianIsRegistered;
    private ObservedGeneric<Student> candidateNewStudent = new ObservedGeneric<Student>(null);

    public GuardianEditorWindow() {
        this.guardian = new Guardian("", "", "", true);
        this.guardianIsRegistered = new ObservedLiveBoolean(
            new ObservedDatum[] {
                (ObservedDatum)DataManager.getInstance().getGuardians(),
            }, 
            () -> {
                return DataManager.getInstance().getGuardians().contains(this.guardian);
            });

        spawn();
    }
    public GuardianEditorWindow(Guardian student) {
        this.guardian = student;
        this.guardianIsRegistered = new ObservedLiveBoolean(
            new ObservedDatum[] {
                (ObservedDatum)DataManager.getInstance().getGuardians(),
            }, 
            () -> {
                return DataManager.getInstance().getGuardians().contains(this.guardian);
            });

        spawn();
    }

    private void spawn() {
        this.window = (UIWindow)new UIWindow("Guardian Editor", new UIFrameConstraints(700, 500), new UIComponent[]{
            new UIGroup(UIAxis.VERTICAL, new UIComponent[]{
                new UIGroup(UIAxis.HORIZONTAL, new UIComponent[]{
                    new UISpacer(3),
                    new UILabel("First Name"),
                    new UISpacer()
                }),
                new UITextField(this.guardian.getFirstName(), false)
                    .onTyping((String text) -> {
                        this.guardian.getFirstName().setVal(text);;
                    })
                    .setMaxSize(Integer.MAX_VALUE, 35),
                new UIGroup(UIAxis.HORIZONTAL, new UIComponent[]{
                    new UISpacer(3),
                    new UILabel("Last Name"),
                    new UISpacer()
                }),
                new UITextField(this.guardian.getLastName(), false)
                    .onTyping((String text) -> {
                        this.guardian.getLastName().setVal(text);;
                    })
                    .setMaxSize(Integer.MAX_VALUE, 35),
                new UIGroup(UIAxis.HORIZONTAL, new UIComponent[]{
                    new UISpacer(3),
                    new UILabel("Email"),
                    new UISpacer()
                }),
                new UITextField(this.guardian.getEmail(), false)
                    .onTyping((String text) -> {
                        this.guardian.getEmail().setVal(text);;
                    })
                    .setMaxSize(Integer.MAX_VALUE, 35),
                new UIGroup(UIAxis.HORIZONTAL, new UIComponent[]{
                    new UIToggle(this.guardian.getSendEmailControl()),
                    new UILabel("Send Email")
                }),
                new UIConditionalDisplay(
                    this.guardianIsRegistered.inverse(),
                    new UIComponent[]{
                    new UIGroup(UIAxis.HORIZONTAL, new UIComponent[]{
                        new UIButton("Cancel")
                            .onPress(() -> {
                                this.window.closeWindow();
                            }),
                        new UIButton("Save")
                        .onPress(() -> {
                            if(
                                !this.guardian.getFirstName().getVal().equals("") && 
                                !this.guardian.getLastName().getVal().equals("") &&
                                !this.guardian.getEmail().getVal().equals("")) 
                                {
                                DataManager.getInstance().registerDatum(this.guardian);
                            } else {
                                UIAlert.alert("Invalid info");
                            }
                        })
                    }),
                    new UISpacer()
                }),
                new UIConditionalDisplay(
                    this.guardianIsRegistered,
                    new UIComponent[]{
                        new UISpacer(10),
                        new UIGroup(UIAxis.HORIZONTAL, new UIComponent[]{
                            new UILabel("Students"),
                            new UISpacer()
                        }),
                        new UIScrollContainer(
                            new UIGroup(UIAxis.VERTICAL, new UIComponent[]{
                                //Existing Students
                                new UIListBuilder<GuardianshipRelationship>(
                                    DataManager.getInstance().getGuardianshipRealtionshipsForGuardian(new ObservedGeneric<Guardian>(this.guardian)), 
                                    (GuardianshipRelationship gr) -> {
                                        return new UIGroup(UIAxis.HORIZONTAL, new UIComponent[]{
                                            new UILabel(gr.getStudent().getVal().getName()),
                                            new UISpacer(),
                                            new UIButton("X")
                                                .onPress(() -> {
                                                    DataManager.getInstance().deleteDatum(gr);
                                                })
                                        });
                                    }, 
                                    UIAxis.VERTICAL),
                                new UISpacer()
                            })
                        ),
                        new UIGroup(UIAxis.VERTICAL, new UIComponent[]{
                            new UIGroup(UIAxis.HORIZONTAL, new UIComponent[]{
                                new UISpacer(5),
                                new UILabel("Add new relationship")
                                    .format(false, false, -1),
                                new UISpacer()
                            }),
                            //Add new Student
                            new UIGroup(UIAxis.HORIZONTAL, new UIComponent[]{
                                new UIDropdown<Student>(
                                    DataManager.getInstance().getStudents(), 
                                    (Student student) -> {
                                        return student.getName().getVal();
                                    })
                                        .onSelect((Student student) -> {
                                            this.candidateNewStudent.setVal(student);
                                        }),
                                new UISpacer(),
                                new UIConditionalDisplay(
                                    new ObservedLiveBoolean(new ObservedDatum[]{
                                        this.candidateNewStudent
                                    }, 
                                    () -> {
                                        return this.candidateNewStudent.getVal()!=null;
                                    }),
                                    new UIComponent[]{
                                        new UIButton("Add")
                                            .onPress(() -> {
                                                System.out.println("***");
                                                DataManager.getInstance().registerDatum(
                                                    new GuardianshipRelationship(guardian, this.candidateNewStudent.getVal())
                                                );
                                            })
                                    })
                            })
                        })
                            .setMaxSize(5000, 125)
                            
                }).setPreferredSize(5000, 5000)
            })
        })
            .setMinSize(400, 400);
    }
}
