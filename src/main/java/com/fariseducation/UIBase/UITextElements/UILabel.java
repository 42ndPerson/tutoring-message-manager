package com.fariseducation.UIBase.UITextElements;

import java.awt.Component;

import javax.swing.JLabel;

import com.fariseducation.Data.ObservedData.ObservedGeneric;

@SuppressWarnings("rawtypes")
public class UILabel extends UITextElement {
    private JLabel label;

    public UILabel(String text) {
        super(text);
    }
    public UILabel(ObservedGeneric observedGeneric) {
        super(observedGeneric);
        System.out.println("Label: " + observedGeneric.getVal());
    }

    @Override
    protected void build() {
        this.label = new JLabel();
    }
    @Override
    protected void updateText() {
        this.label.setText(getFormat().getStartCap() + getObserved().getVal().toString() + getFormat().getEndCap());
        this.label.repaint();
    }

    @Override
    protected Component getAWTComponent() {
        return this.label;
    }
}
