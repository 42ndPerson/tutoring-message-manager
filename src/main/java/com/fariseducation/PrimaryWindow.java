package com.fariseducation;

import com.fariseducation.UIBase.UIWindow;
import com.fariseducation.UIBase.UIEnums.UIAlignment;
import com.fariseducation.UIBase.UIEnums.UIAxis;
import com.fariseducation.UIBase.UITextElements.UIButton;
import com.fariseducation.UIBase.UITextElements.UILabel;
import com.fariseducation.UIBase.UITextElements.UITextField;
import com.fariseducation.Data.Student;
import com.fariseducation.Data.TimeGroup;
import com.fariseducation.Data.Tutor;

import com.fariseducation.Data.Guardian;
import com.fariseducation.Data.ObservedData.DataManager;
import com.fariseducation.Data.ObservedData.ObservedGenericImmutable;
import com.fariseducation.Data.ObservedData.ObservedUUID;
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
    private ObservedGenericImmutable<TimeGroup> selectedTimeGroup = new ObservedGenericImmutable<TimeGroup>(TimeGroup.BLANK);
    private ObservedGenericImmutable<Student> selectedStudent = new ObservedGenericImmutable<Student>(Student.BLANK);

    //private ObservedLockedList<TimeGroup> timeGroups = DataManager.getInstance().getTimeGroups();
    //private ObservedLockedList<Student> filteredStudents = DataManager.getInstance().getStudents();

    public PrimaryWindow() {
        spawn();
    }

    private void spawn() {
        new UIWindow("Message Manager", new UIComponent[]{
            new UIGroup(UIAxis.VERTICAL, UIAlignment.NONE, new UIComponent[]{
                new UIGroup(UIAxis.HORIZONTAL, UIAlignment.NONE, new UIComponent[]{
                    new UIButton("Load Data")
                        .format()
                        .setMaxSize(50, null),
                    new UIButton("Create Template")
                        .format()
                        .setMaxSize(50, null),
                    new UIButton("View Parent")
                        .format()
                        .setMaxSize(50, null),
                    new UIButton("View Tutors")
                        .format()
                        .setMaxSize(50, null),
                    new UISpacer(),
                    new UIButton("Search")
                        .onPress(() -> {
                            DataManager.getInstance().printRegisteredLiveLists();
                            System.out.println(DataManager.getInstance().getTimeGroups().size());
                        })
                        .format()
                        .setMaxSize(50, null),
                }),
                new UISeparator(),
                new UIGroup(UIAxis.HORIZONTAL, UIAlignment.CENTER, new UIComponent[]{
                    new UIGroup(UIAxis.VERTICAL, UIAlignment.NONE, new UIComponent[]{
                        new UIGroup(UIAxis.HORIZONTAL, UIAlignment.LEADING, new UIComponent[]{
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
                                (val) -> {
                                    return new UIGroup(UIAxis.HORIZONTAL, UIAlignment.NONE, new UIComponent[]{
                                        new UIButton(val.getName(), true),
                                        new UIIndicator<ObservedUUID>(
                                            new ObservedUUID(val.getUUID()), 
                                            new ObservedUUID(this.selectedTimeGroup.getVal().getUUID()), 
                                            50, 
                                            50)
                                    });
                                }, 
                                UIAxis.VERTICAL, 
                                UIAlignment.LEADING)
                        ).setPreferredSize(600,3000)
                    }),
                    new UISeparator(),
                    new UIGroup(UIAxis.VERTICAL, UIAlignment.NONE, new UIComponent[]{
                        new UIGroup(UIAxis.HORIZONTAL, UIAlignment.LEADING, new UIComponent[]{
                            new UISpacer(5),
                            new UILabel("Students")
                                .format(
                                    true, 
                                false, 
                                1),
                            new UIButton("+")
                                .onPress(() -> {
                                    if(this.selectedTimeGroup != null) new StudentEditorWindow();
                                    else UIAlert.alert("Select a time group to add a student to.");
                                })
                        }),
                        new UISeparator(),
                        new UIScrollContainer(
                            new UIListBuilder<Student>(
                                DataManager.getInstance().getStudentsForTimeGroup(this.selectedTimeGroup), 
                                (val) -> {
                                    return new UIGroup(UIAxis.HORIZONTAL, UIAlignment.NONE, new UIComponent[]{
                                        new UIButton(val.getName(), true),
                                        new UIIndicator<ObservedUUID>(
                                            new ObservedUUID(val.getUUID()), 
                                            new ObservedUUID(this.selectedTimeGroup.getVal().getUUID()), 
                                            50, 
                                            50)
                                    });
                                }, 
                                UIAxis.VERTICAL, 
                                UIAlignment.LEADING)
                        ).setPreferredSize(600,3000)
                    }),
                    new UISeparator(),
                    new UIGroup(UIAxis.VERTICAL, UIAlignment.LEADING, new UIComponent[]{
                        new UIGroup(UIAxis.HORIZONTAL, UIAlignment.LEADING, new UIComponent[]{
                            new UISpacer(10),
                            new UILabel("Student Name")
                                .format(true, false, 2),
                            new UIButton("Edit")
                                .setMaxSize(50, null)
                        }),
                        new UISeparator(),
                        new UIGroup(UIAxis.VERTICAL, UIAlignment.LEADING, new UIComponent[]{
                            new UIGroup(UIAxis.HORIZONTAL, UIAlignment.NONE, new UIComponent[]{
                                new UISpacer(5),
                                new UILabel("Guardians")
                                    .format(false, false, 1),
                                new UIButton("+")
                                    .setMaxSize(50, null),
                                new UISpacer()
                            }),
                            new UIScrollContainer(
                                new UIListBuilder<Guardian>(
                                    DataManager.getInstance().getGuardiansForStudent(this.selectedStudent), 
                                    (val) -> {
                                        return new UIGroup(UIAxis.HORIZONTAL, UIAlignment.NONE, new UIComponent[]{
                                            new UILabel(((Guardian)val).getName()),
                                            new UISpacer(),
                                            new UIToggle(((Guardian)val).getSendEmailControl())
                                        });
                                    }, 
                                    UIAxis.VERTICAL, 
                                    UIAlignment.LEADING)
                            ).setPreferredSize(600,3000),
                            new UIGroup(UIAxis.HORIZONTAL, UIAlignment.NONE, new UIComponent[]{
                                new UISpacer(5),
                                new UILabel("Tutors")
                                    .format(false, false, 1),
                                new UIButton("+")
                                    .setMaxSize(50, null),
                                new UISpacer()
                            }),
                            new UIScrollContainer(
                                new UIListBuilder<Tutor>(
                                    DataManager.getInstance().getTutorsForStudent(this.selectedStudent), 
                                    (val) -> {
                                        return new UILabel(val.getName());
                                    }, 
                                    UIAxis.VERTICAL, 
                                    UIAlignment.LEADING)
                            ).setPreferredSize(600,3000),
                        }),
                        new UISpacer(10),
                        new UIGroup(UIAxis.HORIZONTAL, UIAlignment.LEADING, new UIComponent[]{
                            new UILabel("Message")
                                .format(false, false, 1),
                            new UISpacer(),
                            new UIButton("Revert to Template")
                        }),
                        new UIScrollContainer(
                            new UIGroup(UIAxis.VERTICAL, UIAlignment.NONE, new UIComponent[]{
                                new UITextField(true)
                            })
                                .setPreferredSize(null, 500)  
                        )
                            .setMinSize(null, 125),
                        new UIGroup(UIAxis.HORIZONTAL, UIAlignment.NONE, new UIComponent[]{
                            new UISpacer(),
                            new UIButton("Send")
                                .setMaxSize(100, null)
                        })
                    }).maximize()
                })
            })
        })
            .setMinSize(600, 500);
    }

    /*
    public ObservedLiveList<Guardian> getGuardiansOfSelected() {
        return ((Student)DataManager.getInstance().getByUUID(selectedStudent.getUUID())).getGuardians();
    }
    public ObservedLiveList<Tutor> getTutorsOfSelected() {
        return ((Student)DataManager.getInstance().getByUUID(selectedStudent.getUUID())).getTutors();
    }*/
}
