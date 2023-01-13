package com.fariseducation;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JFileChooser;

import com.fariseducation.Data.TimeGroup;
import com.fariseducation.Data.ObservedData.DataManager;
import com.fariseducation.Data.ObservedData.ObservedGeneric;
import com.fariseducation.Data.ObservedData.ObservedLockedList;
import com.fariseducation.UIBase.UIComponent;
import com.fariseducation.UIBase.UIConditionalDisplay;
import com.fariseducation.UIBase.UIDropdown;
import com.fariseducation.UIBase.UIGroup;
import com.fariseducation.UIBase.UISeparator;
import com.fariseducation.UIBase.UISpacer;
import com.fariseducation.UIBase.UIWindow;
import com.fariseducation.UIBase.UIEnums.UIAxis;
import com.fariseducation.UIBase.UITextElements.UIButton;
import com.fariseducation.UIBase.UITextElements.UILabel;

public class DataLoadingWindow {
    private UIWindow window;
    private File file;

    private ObservedGeneric<Boolean> fileSelected = new ObservedGeneric<Boolean>(false);
    private ObservedGeneric<Boolean> dataTypeSelected = new ObservedGeneric<Boolean>(false);
    private ObservedGeneric<Boolean> dataLoaded = new ObservedGeneric<Boolean>(false);

    private ArrayList<String> dropdownOptions = new ArrayList<>();

    public DataLoadingWindow() {
        dropdownOptions.add("Load People");
        dropdownOptions.add("Load Sessions");
        
        spawn();
    }

    private void spawn() {
        this.window = new UIWindow("Spreadingsheet Loader", new UIComponent[]{
            new UIGroup(UIAxis.HORIZONTAL, new UIComponent[]{
                new UIButton("Open Spreadsheet")
                    .onPress(() -> {
                        JFileChooser chooser = new JFileChooser();
                        int choosingResult = chooser.showOpenDialog(this.window.getAWTComponent());

                        if(choosingResult == JFileChooser.APPROVE_OPTION) {
                            this.file = chooser.getSelectedFile();
                            this.fileSelected.setVal(true);
                        }
                    }),
                new UISpacer(),
                new UIConditionalDisplay(fileSelected, new UIComponent[]{
                    new UIDropdown<String>(new ObservedLockedList<String>(dropdownOptions), (String val) -> {return val;})
                        .onSelect((String a) -> {this.dataTypeSelected.setVal(true);})
                }),
                new UIConditionalDisplay(dataTypeSelected, new UIComponent[]{
                    new UIButton("Load Data")
                        .onPress(() -> {
                            dataLoaded.setVal(true);
                        })
                })
            }),
            new UIConditionalDisplay(dataLoaded, new UIComponent[]{
                new UIGroup(UIAxis.HORIZONTAL, new UIComponent[] {
                    new UIGroup(UIAxis.VERTICAL, new UIComponent[]{
                        new UIGroup(UIAxis.HORIZONTAL, new UIComponent[] {
                            new UISpacer(5),
                            new UILabel("Alerts")
                                .format(true,false,1),
                            new UISpacer()
                        }),
                        new UISeparator(),
                        new UIGroup(UIAxis.HORIZONTAL, new UIComponent[] {
                            new UISpacer(5),
                            new UILabel("None"),
                            new UISpacer()
                        }),
                        new UIGroup(UIAxis.HORIZONTAL, new UIComponent[] {
                            new UISpacer(),
                            new UIDropdown<TimeGroup>(
                                DataManager.getInstance().getTimeGroups(), 
                                (TimeGroup val) -> {return val.getName().getVal();}),
                            new UISpacer()
                        }),
                        new UIButton("Save")
                    })
                })
            })
        });
    }
}
