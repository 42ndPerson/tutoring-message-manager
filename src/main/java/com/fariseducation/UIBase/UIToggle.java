package com.fariseducation.UIBase;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JToggleButton;

import com.fariseducation.Data.ObservedData.DataObserver;
import com.fariseducation.Data.ObservedData.ObservedGeneric;

public class UIToggle extends UIComponent implements DataObserver {
    private JToggleButton button;
    private ObservedGeneric<Boolean> state;

    public UIToggle(ObservedGeneric<Boolean> state) {
        this.state = state;
        this.state.addObserver(this);

        this.button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UIToggle.this.state.setVal(UIToggle.this.button.getModel().isSelected());
            }
        });
    }

    @Override
    public void updateAfterDataChange() {
        this.button.getModel().setSelected(this.state.getVal());
        this.button.repaint();
    }

    @Override
    protected Component getAWTComponent() {
        return this.button;
    }
}
