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

        this.dropdown = new JComboBox<String>();

        build();
    }

    public UIDropdown<ContentType> onSelect(Consumer<ContentType> behavior) {
        this.dropdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                behavior.accept(getSelected());; 
            }
        });

        return this;
    }
    public void build() {
        createDisplayStrings();

        this.dropdown.setModel(new DefaultComboBoxModel<String>(this.displayStrings));
    }

    private void createDisplayStrings() {
        String[] displayStrings = new String[this.options.size()+1];
        displayStrings[0] = "---";

        for(int i = 0; i < this.options.size(); i++) {
            displayStrings[i+1] = this.displayStringExtractor.apply(this.options.get(i)); //The plus ones is to make space for the default option added above
        }

        this.displayStrings = displayStrings;
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
