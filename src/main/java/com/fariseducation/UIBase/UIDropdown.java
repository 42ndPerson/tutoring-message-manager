package com.fariseducation.UIBase;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import com.fariseducation.Data.ObservedData.DataObserver;
import com.fariseducation.Data.ObservedData.ObservedLockedList;

public class UIDropdown<ContentType> extends UIComponent implements DataObserver {
    private JComboBox<String> dropdown;
    private ObservedLockedList<ContentType> options;
    private Function<ContentType,String> displayStringExtractor;
    private String[] displayStrings;

    public UIDropdown(ObservedLockedList<ContentType> options, Function<ContentType,String> displayStringExtractor) {
        this.options = options;
        this.options.addObserver(this);

        this.displayStringExtractor = displayStringExtractor;

        build();
    }

    public void onSelect(Consumer<ContentType> behavior) {
        this.dropdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                behavior.accept(getSelected());; 
            }
        });
    }
    public void build() {
        createDisplayStrings();

        this.dropdown.setModel(new DefaultComboBoxModel<String>(this.displayStrings));
    }

    private void createDisplayStrings() {
        ArrayList<String> displayStrings = new ArrayList<String>();
        displayStrings.add("---");

        for(ContentType content : this.options) {
            displayStrings.add(displayStringExtractor.apply(content));
        }

        this.displayStrings = (String[])displayStrings.toArray();
    }
    private ContentType getSelected() {
        if(this.dropdown.getSelectedIndex() == 0) return null;
        return this.options.get(dropdown.getSelectedIndex()-1);
    }

    @Override
    protected Component getAWTComponent() {
        return this.dropdown;
    }

    @Override
    public void updateAfterDataChange() {
        build();
    }
    
}
