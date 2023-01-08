package com.fariseducation;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import com.fariseducation.UIBase.UIWindow;
import com.fariseducation.UIBase.UIEnums.UIAlignment;
import com.fariseducation.UIBase.UIEnums.UIAxis;
import com.fariseducation.UIBase.UITextElements.UIButton;
import com.fariseducation.UIBase.UITextElements.UILabel;
import com.fariseducation.UIBase.UITextElements.UITextField;
import com.fariseducation.UIBase.UITextElements.UITextFormat;
import com.fariseducation.Data.Guardian;
import com.fariseducation.Data.ObservedData.DataManager;
import com.fariseducation.Data.ObservedData.ObservedLiveList;
import com.fariseducation.Data.ObservedData.ObservedLockedList;
import com.fariseducation.Data.ObservedData.ObservedString;
import com.fariseducation.UIBase.UIComponent;
import com.fariseducation.UIBase.UIGroup;
import com.fariseducation.UIBase.UIIndicator;
import com.fariseducation.UIBase.UIListBuilder;
import com.fariseducation.UIBase.UIScrollContainer;
import com.fariseducation.UIBase.UISeparator;
import com.fariseducation.UIBase.UISpacer;

/**
 * Hello world!
 */
public final class App {
    private App() {
    }

    /**
     * Says hello to the world.
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {
        //LocalDate ld = LocalDate.of(0, 1, 1);
        //System.out.println(ld.toString());
        /*GregorianCalendar gc = new GregorianCalendar();
        gc.set(0, 13, 0, 0, 0);
        System.out.println(gc.getTime().toString());
        System.out.println(gc.getTime().);*/
        /*ObservedLiveList test = new ObservedLiveList<Guardian>(
            DataManager.getInstance()::getGuardians,
            (Guardian val) -> {
                return true;
            }
        );
        ObservedLockedList test2 = (ObservedLockedList)test;
        System.out.println(test==test2);*/

        new PrimaryWindow();
        /*ObservedString oString = new ObservedString("A");

        new UIWindow("Message Manager", new UIComponent[]{
            new UIButton(oString)
                .onPress(() -> {
                    if(oString.getString().equals("A")) oString.setString("B");
                    else if(oString.getString().equals("B")) oString.setString("A");
                }),
            new UIIndicator<ObservedString>(new ObservedString("A"), oString, 100, 100)
        });*/
        /*
        ArrayList<Integer> range = new ArrayList<Integer>();
        for(int i = 0; i < 1000; i++) {
            range.add(i);
        }

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
                        }),
                        new UISeparator(),
                        new UIScrollContainer(
                            new UIListBuilder<Integer>(
                                range, 
                                (val) -> {
                                    return new UILabel("asduvasiudfvbaosiduvbaosdiuvbaosidbv");
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
                        }),
                        new UISeparator(),
                        new UIScrollContainer(
                            new UIListBuilder<Integer>(
                                range, 
                                (val) -> {
                                    return new UILabel("asduvasiudfvbaosiduvbaosdiuvbaosidbv");
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
                                new UILabel("Parents")
                                    .format(false, false, 1),
                                new UIButton("+")
                                    .setMaxSize(50, null),
                                new UISpacer()
                            }),
                            new UIScrollContainer(
                                new UIListBuilder<Integer>(
                                    range, 
                                    (val) -> {
                                        return new UILabel("asduvasiudfvbaosiduvbaosdiuvbaosidbv");
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
                                new UIListBuilder<Integer>(
                                    range, 
                                    (val) -> {
                                        return new UILabel("asduvasiudfvbaosiduvbaosdiuvbaosidbv");
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
                                .setPreferredSize(3000, 500)  
                        )
                            .setPreferredSize(3000, 2000),
                        new UIGroup(UIAxis.HORIZONTAL, UIAlignment.NONE, new UIComponent[]{
                            new UISpacer(),
                            new UIButton("Send")
                                .setMaxSize(100, null)
                        })
                    }).maximize()
                })
            })
        });*/
    }
}
