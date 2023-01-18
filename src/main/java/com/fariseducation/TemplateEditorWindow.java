package com.fariseducation;

import com.fariseducation.UIBase.UIWindow;
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
import com.fariseducation.UIBase.UISpacer;
import com.fariseducation.UIBase.UIScrollContainer;
import com.fariseducation.UIBase.UIEnums.UIAxis;
import com.fariseducation.UIBase.UITextElements.UILabel;
import com.fariseducation.UIBase.UITextElements.UITextField;

public class TemplateEditorWindow {
    private UIWindow window;
    private ObservedGeneric<TimeGroup> timeGroup = new ObservedGeneric<TimeGroup>(TimeGroup.BLANK);
    private ObservedGeneric<String> template = new ObservedGeneric<String>("");

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
                new UILabel("Select Time Group of Template: ")
                    .format(false, false, -1),
                new UIDropdown<TimeGroup>(
                    DataManager.getInstance().getTimeGroups(), 
                    (TimeGroup timeGroup) -> {
                        return timeGroup.getName().getVal();
                    })
                        .onSelect((TimeGroup timeGroup) -> {
                            if(timeGroup != null) {
                                System.out.println("asdhobvpisduv:" + timeGroup.getTemplate().getVal());
                                this.timeGroup.setVal(timeGroup);
                                this.template.setVal(timeGroup.getTemplate().getVal());
                            } else {
                                this.timeGroup.setVal(TimeGroup.BLANK);
                            }
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
                    new UIGroup(UIAxis.VERTICAL, new UIComponent[]{
                        new UISpacer()
                    })
                        .setPreferredSize(null, 5000)
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
                                    new ObservedLiveValue<>(
                                        new ObservedDatum[]{this.timeGroup}, 
                                        () -> {
                                            return this.timeGroup.getVal().getTemplate();
                                        }
                                    ), 
                                    true
                                )
                                    .onTyping((String val) -> {
                                        if(this.timeGroup.getVal() != null) {
                                            this.timeGroup.getVal().getTemplate().setVal(val);
                                        }
                                    })
                            })
                        )
                            .setPreferredSize(null, 100),
                        new UISpacer(3),
                        new UIGroup(UIAxis.HORIZONTAL, new UIComponent[]{
                            new UISpacer(5),
                            new UIGroup(UIAxis.VERTICAL, new UIComponent[]{
                                new UIGroup(UIAxis.HORIZONTAL, new UIComponent[]{
                                    new UILabel("Add Insertion Point For:"),
                                    new UISpacer()
                                }),
                                new UIGroup(UIAxis.HORIZONTAL, new UIComponent[]{
                                    new UILabel("Student Name : %SN%"),
                                    new UISpacer()
                                }),
                                new UIGroup(UIAxis.HORIZONTAL, new UIComponent[]{
                                    new UILabel("Tutor Name(s): %TN%"),
                                    new UISpacer()
                                }),
                                new UIGroup(UIAxis.HORIZONTAL, new UIComponent[]{
                                    new UILabel("Guardian Name(s): %GN%"),
                                    new UISpacer()
                                }),
                                new UIGroup(UIAxis.HORIZONTAL, new UIComponent[]{
                                    new UILabel("Tutoring Dates: %TD%"),
                                    new UISpacer()
                                }),
                                new UIGroup(UIAxis.HORIZONTAL, new UIComponent[]{
                                    new UILabel("Total Hours: %TH%"),
                                    new UISpacer()
                                }),
                                new UIGroup(UIAxis.HORIZONTAL, new UIComponent[]{
                                    new UILabel("Billing Total: %BT%"),
                                    new UISpacer()
                                }),
                                new UISpacer()
                            })
                                .setPreferredSize(null, 5000),
                            new UISpacer(15)
                        })
                            //.setPreferredSize(140, null)
                            .setMaxSize(170, null)
                            .setMinSize(150, null),
                        new UISpacer(3),
                    }),
                    new UISpacer(3)
                })
        })
            .onClose(() -> {
                DataManager.getInstance().save();
            })    
            .setMinSize(500, 300);
    }

    /*private void setVal(String val) {
        this.timeGroup.getVal().getTemplate().setVal(val);
        this.template.setVal(val);
    }*/
}
