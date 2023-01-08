package com.fariseducation.UIBase.UITextElements;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import com.fariseducation.Data.ObservedData.ObservedGenericImmutable;

public class UIButton extends UITextElement {
    private JButton button;
    private boolean flat;
    
    public UIButton(String text) {
        super(text);
    }
    public UIButton(ObservedGenericImmutable<String> observedString) {
        super(observedString);
    }
    public UIButton(String text, boolean flat) {
        super(text);

        this.flat = flat;
    }
    public UIButton(ObservedGenericImmutable<String> observedString, boolean flat) {
        super(observedString);

        this.flat = flat;
    }

    @Override
    protected void build() {
        this.button = new JButton();

        if(this.flat) {
            this.button.setBorderPainted(false);
            this.button.setFocusPainted(false);
            this.button.setContentAreaFilled(false);
        }
        //this.getAWTComponent().setMaximumSize(new Dimension(20,this.button.getPreferredSize().height));//.setPreferredSize(new Dimension(20,15));
    }
    @Override
    protected void updateText() {
        this.button.setText(getFormat().getStartCap() + getObservedString().getVal() + getFormat().getEndCap());
        
        this.button.repaint();
    }

    public UIButton onPress(Runnable behavior) {
        this.button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                behavior.run(); 
            }
        });

        return this;
    }

    @Override
    protected Component getAWTComponent() {
        return this.button;
    }
}
