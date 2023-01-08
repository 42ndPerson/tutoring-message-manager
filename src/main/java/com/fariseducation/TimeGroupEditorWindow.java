package com.fariseducation;

import java.time.DateTimeException;
import java.time.LocalDate;

import com.fariseducation.Data.TimeGroup;
import com.fariseducation.Data.ObservedData.DataManager;
import com.fariseducation.UIBase.UIAlert;
import com.fariseducation.UIBase.UIComponent;
import com.fariseducation.UIBase.UIGroup;
import com.fariseducation.UIBase.UISpacer;
import com.fariseducation.UIBase.UIWindow;
import com.fariseducation.UIBase.UIEnums.UIAlignment;
import com.fariseducation.UIBase.UIEnums.UIAxis;
import com.fariseducation.UIBase.UITextElements.UIButton;
import com.fariseducation.UIBase.UITextElements.UILabel;
import com.fariseducation.UIBase.UITextElements.UITextField;

public class TimeGroupEditorWindow {
    private UIWindow window;
    private String name = "";
    private String sdm = "";
    private String sdd = "";
    private String sdy = "";
    private String edm = "";
    private String edd = "";
    private String edy = "";

    public TimeGroupEditorWindow() {
        spawn();
    }

    private void spawn() {
        this.window = new UIWindow("Time Group Editor", new UIComponent[] {
            new UIGroup(UIAxis.VERTICAL, UIAlignment.NONE, new UIComponent[] {
                new UILabel("Name"),
                new UITextField(null, false)
                    .onTyping(
                        (String val) -> {
                            this.name = val;
                        })
                    .setMaxSize(50, 75),
                new UIGroup(UIAxis.HORIZONTAL, UIAlignment.NONE, new UIComponent[] {
                    new UILabel("Start Date"),
                    new UISpacer()
                }),
                new UIGroup(UIAxis.HORIZONTAL, UIAlignment.NONE, new UIComponent[] {
                    new UITextField("Month", false)
                        .onTyping(
                            (String val) -> {
                                this.sdm = val;
                            }),
                    new UITextField("Day", false)
                        .onTyping(
                            (String val) -> {
                                this.sdd = val;
                            }),
                    new UITextField("Year", false)
                        .onTyping(
                            (String val) -> {
                                this.sdy = val;
                            }),
                }),
                new UIGroup(UIAxis.HORIZONTAL, UIAlignment.NONE, new UIComponent[] {
                    new UILabel("End Date"),
                    new UISpacer()
                }),
                new UIGroup(UIAxis.HORIZONTAL, UIAlignment.NONE, new UIComponent[] {
                    new UITextField("Month", false)
                        .onTyping(
                            (String val) -> {
                                this.edm = val;
                            }),
                    new UITextField("Day", false)
                        .onTyping(
                            (String val) -> {
                                this.edd = val;
                            }),
                    new UITextField("Year", false)
                        .onTyping(
                            (String val) -> {
                                this.edy = val;
                            }),
                }),
                new UISpacer(),
                new UIGroup(UIAxis.HORIZONTAL, UIAlignment.NONE, new UIComponent[] {
                    new UISpacer(),
                    new UIButton("Cancel")
                        .onPress(() -> {
                            this.window.closeWindow();
                        }),
                    new UIButton("Save")
                        .onPress(() -> {
                            if(!name.equals("")) {
                                LocalDate sd;
                                LocalDate ed;
                                try {
                                    sd = LocalDate.of(
                                        Integer.parseInt(sdy), 
                                        Integer.parseInt(sdm), 
                                        Integer.parseInt(sdd));
                                    
                                    try {
                                        ed = LocalDate.of(
                                            Integer.parseInt(edy), 
                                            Integer.parseInt(edm), 
                                            Integer.parseInt(edd));

                                        DataManager.getInstance().registerDatum(new TimeGroup(name, sd, ed));
                                        this.window.closeWindow();
                                    } catch(NumberFormatException | DateTimeException e) {
                                        UIAlert.alert("Invalid end date.");
                                    }
                                } catch(NumberFormatException | DateTimeException e) {
                                    UIAlert.alert("Invalid start date.");
                                }
                            } else {
                                UIAlert.alert("Invalid name.");
                            }
                        })
                })
            })
        });
    }
}
