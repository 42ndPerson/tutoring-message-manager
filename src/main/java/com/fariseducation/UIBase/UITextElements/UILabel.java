package com.fariseducation.UIBase.UITextElements;

import java.awt.Component;

import javax.swing.JLabel;

import com.fariseducation.Data.ObservedData.ObservedGeneric;

public class UILabel extends UITextElement {
    private JLabel label;

    public UILabel(String text) {
        super(text);
    }
    public UILabel(ObservedGeneric<String> observedString) {
        super(observedString);
    }

    @Override
    protected void build() {
        this.label = new JLabel();
    }
    @Override
    protected void updateText() {
        this.label.setText(getFormat().getStartCap() + getObservedString().getVal() + getFormat().getEndCap());
        this.label.repaint();
    }

    @Override
    protected Component getAWTComponent() {
        return this.label;
    }
}
