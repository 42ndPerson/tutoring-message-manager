package com.fariseducation.UIBase.UITextElements;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import com.fariseducation.Data.ObservedData.ObservedGeneric;
import com.fariseducation.UIBase.UIComponent;
import com.fariseducation.UIBase.UIGroup;
import com.fariseducation.UIBase.UIEnums.UIAxis;

@SuppressWarnings("rawtypes")
public class UIButton extends UITextElement {
    private JButton button;
    private boolean flat;
    
    public UIButton(String text) {
        super(text);
    }
    public UIButton(ObservedGeneric observedGeneric) {
        super(observedGeneric);
    }
    public UIButton(String text, boolean flat) {
        super(text);

        this.flat = flat;
    }
    public UIButton(ObservedGeneric observedGeneric, boolean flat) {
        super(observedGeneric);

        this.flat = flat;
    }

    public UIComponent setMaxSize(Integer width, Integer height) {
        return new UIGroup(UIAxis.VERTICAL, new UIComponent[]{
            this
        }).setMaxSize(width, height);
    }
    public UIComponent setMinSize(Integer width, Integer height) {
        return new UIGroup(UIAxis.VERTICAL, new UIComponent[]{
            this
        }).setMinSize(width, height);
    }
    public UIComponent setPreferredSize(Integer width, Integer height) {
        return new UIGroup(UIAxis.VERTICAL, new UIComponent[]{
            this
        }).setPreferredSize(width, height);
    }
    public UIComponent maximize() {
        return new UIGroup(UIAxis.VERTICAL, new UIComponent[]{
            this
        }).maximize();
    }

    @Override
    protected void build() {
        this.button = new JButton();

        if(this.flat) {
            this.button.setContentAreaFilled(false);
        }
        //this.getAWTComponent().setMaximumSize(new Dimension(20,this.button.getPreferredSize().height));//.setPreferredSize(new Dimension(20,15));
    }
    @Override
    protected void updateText() {
        this.button.setText(getFormat().getStartCap() + getObserved().getVal().toString() + getFormat().getEndCap());
        
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
