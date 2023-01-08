package com.fariseducation.UIBase;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JToggleButton;

import com.fariseducation.Data.ObservedData.DataObserver;
import com.fariseducation.Data.ObservedData.ObservedBoolean;

public class UIToggle extends UIComponent implements DataObserver {
    private JToggleButton button;
    private ObservedBoolean state;

    public UIToggle(ObservedBoolean state) {
        this.state = state;
        this.state.addObserver(this);

        this.button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UIToggle.this.state.setBoolean(UIToggle.this.button.getModel().isSelected());
            }
        });
    }

    @Override
    public void updateAfterDataChange() {
        this.button.getModel().setSelected(this.state.getBoolean());
        this.button.repaint();
    }

    @Override
    protected Component getAWTComponent() {
        return this.button;
    }
}
