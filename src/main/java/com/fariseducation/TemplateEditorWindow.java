package com.fariseducation;

import com.fariseducation.UIBase.UIWindow;
import com.fariseducation.Data.Student;
import com.fariseducation.Data.TimeGroup;
import com.fariseducation.Data.ObservedData.DataManager;
import com.fariseducation.Data.ObservedData.ObservedDatum;
import com.fariseducation.Data.ObservedData.ObservedGeneric;
import com.fariseducation.Data.ObservedData.ObservedLiveBoolean;
import com.fariseducation.Data.ObservedData.ObservedLiveValue;
import com.fariseducation.UIBase.UIComponent;
import com.fariseducation.UIBase.UIConditionalDisplay;
import com.fariseducation.UIBase.UIDropdown;
import com.fariseducation.UIBase.UIFrameConstraints;
import com.fariseducation.UIBase.UIGroup;
import com.fariseducation.UIBase.UIListBuilder;
import com.fariseducation.UIBase.UISpacer;
import com.fariseducation.UIBase.UIScrollContainer;
import com.fariseducation.UIBase.UIAlert;
import com.fariseducation.UIBase.UIEnums.UIAxis;
import com.fariseducation.UIBase.UITextElements.UIButton;
import com.fariseducation.UIBase.UITextElements.UILabel;
import com.fariseducation.UIBase.UITextElements.UITextField;

public class TemplateEditorWindow {
    private UIWindow window;
    private ObservedGeneric<TimeGroup> timeGroup = new ObservedGeneric<TimeGroup>(TimeGroup.BLANK);

    public TemplateEditorWindow() {
        spawn();
    }

    private void spawn() {
        this.window = (UIWindow)new UIWindow("View Students", new UIFrameConstraints(400, 500), new UIComponent[]{//new UIWindow("View Guardians", new UIFrameConstraints(400, 600), new UIComponent[]{
            new UIGroup(UIAxis.HORIZONTAL, new UIComponent[]{
                new UISpacer(3),
                new UILabel("Template")
                    .format(true, false, 2),
                new UISpacer()
            }),
            new UIGroup(UIAxis.HORIZONTAL, new UIComponent[]{
                new UISpacer(3),
                new UIDropdown<TimeGroup>(
                    DataManager.getInstance().getTimeGroups(), 
                    (TimeGroup timeGroup) -> {
                        return timeGroup.getName().getVal();
                    })
                        .onSelect((TimeGroup timeGroup) -> {
                            if(timeGroup != null) this.timeGroup.setVal(timeGroup);
                        }),
                new UISpacer()
            }),
            new UIConditionalDisplay( //Spacer to keep dropdown at top before selection
                new ObservedLiveBoolean(
                    new ObservedDatum[]{this.timeGroup}, 
                    () -> {
                        return this.timeGroup.getVal() == TimeGroup.BLANK;
                    }), 
                new UIComponent[]{
                    new UISpacer()
            }),
            new UIConditionalDisplay(
                new ObservedLiveBoolean(
                    new ObservedDatum[]{this.timeGroup}, 
                    () -> {
                        return this.timeGroup.getVal() != TimeGroup.BLANK;
                    }), 
                new UIComponent[]{
                    new UIGroup(UIAxis.HORIZONTAL, new UIComponent[]{
                        new UISpacer(3),
                        new UIScrollContainer(
                            new UIGroup(UIAxis.VERTICAL, new UIComponent[]{
                                new UITextField(
                                    new ObservedLiveValue<ObservedGeneric<String>>(
                                        new ObservedDatum[]{this.timeGroup}, 
                                        () -> { return this.timeGroup.getVal().getTemplate(); }), true)
                                    .onTyping((String val) -> {
                                        if(this.timeGroup.getVal() != null) this.timeGroup.getVal().getTemplate().setVal(val);
                                    })
                            })
                                .setPreferredSize(null, 500)  
                        )
                            .setMinSize(null, 125),
                        new UISpacer(3),
                        new UIGroup(UIAxis.VERTICAL, new UIComponent[]{
                            new UILabel("Add Insertion Point For:"),
                            new UIButton("Student Name")
                                .onPress(() -> {
                                    this.timeGroup.getVal().getTemplate().setVal(this.timeGroup.getVal().getTemplate().getVal() + "%SN%");
                                }),
                            new UIButton("Tutor Name(s)")
                                .onPress(() -> {
                                    this.timeGroup.getVal().getTemplate().setVal(this.timeGroup.getVal().getTemplate().getVal() + "%TN%");
                                }),
                            new UIButton("Parent Name(s)")
                                .onPress(() -> {
                                    this.timeGroup.getVal().getTemplate().setVal(this.timeGroup.getVal().getTemplate().getVal() + "%PN%");
                                }),
                            new UIButton("Tutoring Dates")
                                .onPress(() -> {
                                    this.timeGroup.getVal().getTemplate().setVal(this.timeGroup.getVal().getTemplate().getVal() + "%TD%");
                                }),
                            new UIButton("Total Hours")
                                .onPress(() -> {
                                    this.timeGroup.getVal().getTemplate().setVal(this.timeGroup.getVal().getTemplate().getVal() + "%TH%");
                                }),
                            new UIButton("Billing Total")
                                .onPress(() -> {
                                    this.timeGroup.getVal().getTemplate().setVal(this.timeGroup.getVal().getTemplate().getVal() + "%BT%");
                                }),
                            new UISpacer()
                        })
                            .setMaxSize(100, null),
                        new UISpacer(3),
                    }),
                    new UISpacer(3)
                })
        })
            .setMinSize(500, 300);
    }
}
