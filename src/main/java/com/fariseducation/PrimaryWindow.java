package com.fariseducation;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import com.fariseducation.UIBase.UIWindow;
import com.fariseducation.UIBase.UIEnums.UIAxis;
import com.fariseducation.UIBase.UITextElements.UIButton;
import com.fariseducation.UIBase.UITextElements.UILabel;
import com.fariseducation.UIBase.UITextElements.UITextField;
import com.fariseducation.Data.Student;
import com.fariseducation.Data.TimeGroup;
import com.fariseducation.Data.Tutor;
import com.fariseducation.Data.Guardian;
import com.fariseducation.Data.GuardianshipRelationship;
import com.fariseducation.Data.ObservedData.DataManager;
import com.fariseducation.Data.ObservedData.ObservedDatum;
import com.fariseducation.Data.ObservedData.ObservedGeneric;
import com.fariseducation.Data.ObservedData.ObservedLiveValue;
import com.fariseducation.UIBase.UIAlert;
import com.fariseducation.UIBase.UIComponent;
import com.fariseducation.UIBase.UIGroup;
import com.fariseducation.UIBase.UIIndicator;
import com.fariseducation.UIBase.UIListBuilder;
import com.fariseducation.UIBase.UIScrollContainer;
import com.fariseducation.UIBase.UISeparator;
import com.fariseducation.UIBase.UISpacer;
import com.fariseducation.UIBase.UIToggle;

public class PrimaryWindow {
    private ObservedGeneric<TimeGroup> selectedTimeGroup = new ObservedGeneric<TimeGroup>(TimeGroup.BLANK);
    private ObservedGeneric<Student> selectedStudent = new ObservedGeneric<Student>(Student.BLANK);

    public PrimaryWindow() {
        spawn();

        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                DataManager.getInstance().save();
            }
        });
    }

    private void spawn() {
        UITextField messageBox = new UITextField(
            new ObservedLiveValue<String>(
                new ObservedDatum[]{this.selectedTimeGroup, this.selectedStudent}, 
                () -> {
                    if(this.selectedStudent.getVal() != Student.BLANK) return this.selectedStudent.getVal().getMessageForTimeGroup(this.selectedTimeGroup.getVal());
                    else return "";
                }),
                true
        );

        new UIWindow("Message Manager", new UIComponent[]{
            new UIGroup(UIAxis.VERTICAL, new UIComponent[]{
                new UIGroup(UIAxis.HORIZONTAL, new UIComponent[]{
                    new UIButton("Load Data")
                        .onPress(() -> {
                            new DataLoadingWindow();
                        })
                        .format()
                        .setMaxSize(50, null),
                    new UIButton("Create Template")
                        .onPress(() -> {
                            new TemplateEditorWindow();
                        })
                        .format()
                        .setMaxSize(50, null),
                    new UIButton("View Students")
                        .onPress(() -> {
                            new StudentListWindow();
                        })
                        .format()
                        .setMaxSize(50, null),
                    new UIButton("View Guardians")
                        .onPress(() -> {
                            new GuardianListWindow();
                        })
                        .format()
                        .setMaxSize(50, null),
                    new UIButton("View Tutors")
                        .onPress(() -> {
                            new TutorListWindow();
                        })
                        .format()
                        .setMaxSize(50, null),
                    new UISpacer()
                }),
                new UISeparator(),
                new UIGroup(UIAxis.HORIZONTAL, new UIComponent[]{
                    new UIGroup(UIAxis.VERTICAL, new UIComponent[]{
                        new UIGroup(UIAxis.HORIZONTAL, new UIComponent[]{
                            new UISpacer(5),
                            new UILabel("Time Groups")
                                .format(
                                    true, 
                                false, 
                                1),
                            new UIButton("+")
                                .onPress(() -> {
                                    new TimeGroupEditorWindow();
                                })
                        }),
                        new UISeparator(),
                        new UIScrollContainer(
                            new UIListBuilder<TimeGroup>(
                                DataManager.getInstance().getTimeGroups(), 
                                (TimeGroup val) -> {
                                    return new UIGroup(UIAxis.HORIZONTAL, new UIComponent[]{
                                        new UIButton(val.getName(), true)
                                            .onPress(() -> {
                                                if(this.selectedTimeGroup.getVal()!=val) {
                                                    this.selectedTimeGroup.setVal(val);
                                                    this.selectedStudent.setVal(Student.BLANK);
                                                }
                                                else this.selectedTimeGroup.setVal(TimeGroup.BLANK);
                                            }),
                                        new UIIndicator<ObservedGeneric<TimeGroup>>(
                                            new ObservedGeneric<TimeGroup>(val),
                                            this.selectedTimeGroup, 
                                            50, 
                                            25)
                                    });
                                }, 
                                UIAxis.VERTICAL)
                        ).setPreferredSize(600,3000)
                    }),
                    new UISeparator(),
                    new UIGroup(UIAxis.VERTICAL, new UIComponent[]{
                        new UIGroup(UIAxis.HORIZONTAL, new UIComponent[]{
                            new UISpacer(5),
                            new UILabel("Students")
                                .format(
                                    true, 
                                false, 
                                1),
                        }),
                        new UISeparator(),
                        new UIScrollContainer(
                            new UIListBuilder<Student>(
                                DataManager.getInstance().getStudentsForTimeGroup(this.selectedTimeGroup),
                                (Student val) -> {
                                    return new UIGroup(UIAxis.HORIZONTAL, new UIComponent[]{
                                        new UIButton(val.getName(), true)
                                            .onPress(() -> {
                                                this.selectedStudent.setVal(val);
                                            }),
                                        new UIIndicator<ObservedGeneric<Student>>(
                                            new ObservedGeneric<Student>(val),
                                            this.selectedStudent, 
                                            50, 
                                            25)
                                    });
                                }, 
                                UIAxis.VERTICAL)
                        ).setPreferredSize(600,3000)
                    }),
                    new UISeparator(),
                    new UIGroup(UIAxis.VERTICAL, new UIComponent[]{
                        new UIGroup(UIAxis.HORIZONTAL, new UIComponent[]{
                            new UISpacer(10),
                            new UILabel("Student Name")
                                .format(true, false, 2),
                            new UIButton("Edit")
                                .onPress(() -> {
                                    if(!this.selectedStudent.getVal().equals(Student.BLANK)) new StudentEditorWindow(this.selectedStudent.getVal());
                                })
                                .setMaxSize(50, null)
                        }),
                        new UISeparator(),
                        new UIGroup(UIAxis.VERTICAL, new UIComponent[]{
                            new UIGroup(UIAxis.HORIZONTAL, new UIComponent[]{
                                new UISpacer(5),
                                new UILabel("Guardians")
                                    .format(false, false, 1),
                                new UISpacer()
                            }),
                            new UIScrollContainer(
                                new UIListBuilder<Guardian>(
                                    DataManager.getInstance().getGuardiansForStudent(this.selectedStudent), 
                                    (val) -> {
                                        return new UIGroup(UIAxis.HORIZONTAL, new UIComponent[]{
                                            new UILabel(((Guardian)val).getName()),
                                            new UISpacer(),
                                            new UIToggle(((Guardian)val).getSendEmailControl())
                                        });
                                    }, 
                                    UIAxis.VERTICAL)
                            ).setPreferredSize(600,3000),
                            new UIGroup(UIAxis.HORIZONTAL, new UIComponent[]{
                                new UISpacer(5),
                                new UILabel("Tutors")
                                    .format(false, false, 1),
                                new UISpacer()
                            }),
                            new UIScrollContainer(
                                new UIListBuilder<Tutor>(
                                    DataManager.getInstance().getTutorsForStudent(this.selectedStudent), 
                                    (val) -> {
                                        return new UILabel(val.getName());
                                    }, 
                                    UIAxis.VERTICAL)
                            ).setPreferredSize(600,3000),
                        }),
                        new UISpacer(10),
                        new UIGroup(UIAxis.HORIZONTAL, new UIComponent[]{
                            new UILabel("Message")
                                .format(false, false, 1),
                            new UISpacer(),
                            new UIButton("Revert to Template")
                                .onPress(() -> {
                                    this.selectedStudent.getVal().resetMessageForTimeGroup(this.selectedTimeGroup.getVal());
                                    messageBox.updateAfterDataChange();
                                })
                        }),
                        new UIScrollContainer(
                            new UIGroup(UIAxis.VERTICAL, new UIComponent[]{
                                messageBox
                                    .onTyping((String val) -> {
                                        this.selectedStudent.getVal().setMessageForTimeGroup(this.selectedTimeGroup.getVal(), val);
                                    })
                            })
                                .setPreferredSize(100, 500)  
                        )
                            .setMinSize(null, 125),
                        new UIGroup(UIAxis.HORIZONTAL, new UIComponent[]{
                            new UISpacer(),
                            new UIButton("Send")
                                .onPress(() -> {
                                    String emails = "";
                                    String subject = "";
                                    String body = this.selectedStudent.getVal().getMessageForTimeGroup(this.selectedTimeGroup.getVal());

                                    //Setup Emails
                                    for(
                                        GuardianshipRelationship gr : 
                                        DataManager.getInstance().getGuardianshipRealtionshipsForStudent(this.selectedStudent)) {
                                            Guardian g = gr.getGuardian().getVal();

                                            if(g.getSendEmailControl().getVal()) emails += g.getEmail().getVal() + ",";
                                    }
                                    emails = emails.substring(0, emails.length()-1);

                                    //Setup Subject
                                    subject = this.selectedTimeGroup.getVal().getName() +  "%20Message";

                                    //Setup Body
                                    body = body.replace(" ", "%20");
                                    body = body.replace("\n", "%0D%0A");
                                    body = body.replace("\r", "%0D%0A");

                                    Desktop desktop;

                                    if(Desktop.isDesktopSupported() && (desktop = Desktop.getDesktop()).isSupported(Desktop.Action.MAIL)) {
                                        try {
                                            URI mailto = new URI("mailto:" + emails + "?subject=" + subject + "&body=" + body);

                                            desktop.mail(mailto);
                                        } catch (URISyntaxException | IOException e) {
                                            e.printStackTrace();
                                            UIAlert.alert("Mail error.");
                                        }
                                    } else {
                                        UIAlert.alert("Mail window not supported.");
                                    }
                                })
                                .setMaxSize(100, null)
                        })
                    }).maximize()
                })
            })
        })
            .setMinSize(600, 500);
    }
}
