package com.fariseducation;

import com.fariseducation.UIBase.UIWindow;
import com.fariseducation.Data.Student;
import com.fariseducation.Data.ObservedData.DataManager;
import com.fariseducation.UIBase.UIComponent;
import com.fariseducation.UIBase.UIFrameConstraints;
import com.fariseducation.UIBase.UIGroup;
import com.fariseducation.UIBase.UIListBuilder;
import com.fariseducation.UIBase.UISpacer;
import com.fariseducation.UIBase.UIScrollContainer;
import com.fariseducation.UIBase.UIEnums.UIAxis;
import com.fariseducation.UIBase.UITextElements.UIButton;
import com.fariseducation.UIBase.UITextElements.UILabel;

public class StudentListWindow {
    private UIWindow window;

    public StudentListWindow() {
        spawn();
    }

    private void spawn() {
        this.window = (UIWindow)new UIWindow("View Students", new UIFrameConstraints(400, 500), new UIComponent[]{//new UIWindow("View Guardians", new UIFrameConstraints(400, 600), new UIComponent[]{
            new UIGroup(UIAxis.HORIZONTAL, new UIComponent[]{
                new UISpacer(3),
                new UILabel("Students")
                    .format(true, false, 2),
                new UISpacer()
            }),
            new UIScrollContainer(
                new UIListBuilder<Student>(
                    DataManager.getInstance().getStudents(), 
                    (Student student) -> {
                        return new UIGroup(UIAxis.HORIZONTAL, new UIComponent[] {
                            new UILabel(student.getName()),
                            new UISpacer(),
                            new UIButton("Edit")
                                .onPress(() -> {
                                    new StudentEditorWindow(student);
                                })
                        });
                    }, 
                    UIAxis.VERTICAL)
            )
                .setPreferredSize(5000, 5000),
            new UIGroup(UIAxis.HORIZONTAL, new UIComponent[]{
                new UISpacer(),
                new UIButton("+")
                    .onPress(() -> {
                        new StudentEditorWindow();
                    }),
                new UISpacer()
            }),
        })
            .setMinSize(300, 300);
    }
}
