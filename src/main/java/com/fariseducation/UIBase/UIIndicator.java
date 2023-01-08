package com.fariseducation.UIBase;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JPanel;

import com.fariseducation.Data.ObservedData.DataObserver;
import com.fariseducation.Data.ObservedData.ObservedDatum;
import com.fariseducation.UIBase.UIEnums.UIAlignment;
import com.fariseducation.UIBase.UIEnums.UIAxis;

public class UIIndicator<ConditionDataType extends ObservedDatum> extends UIComponent implements DataObserver {
    private JPanel panel = null;
    private ConditionDataType matchCondition;
    private ConditionDataType controllingVariable;
    private int maxWidth;
    private int maxHeight;
    private UIAxis axis;

    public UIIndicator(
        ConditionDataType matchCondition, 
        ConditionDataType controllingVariable, 
        int maxWidth, 
        int maxHeight) 
    {
        this.matchCondition = matchCondition;
        this.controllingVariable = controllingVariable;

        this.controllingVariable.addObserver(this);

        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
    }

    private void build() {
        System.out.println(this.controllingVariable.equals(this.matchCondition));

        int maxWidth = 0;
        int maxHeight = 0;
        switch(this.axis) {
            case HORIZONTAL:
                maxHeight = this.controllingVariable.equals(this.matchCondition) ? this.maxHeight : 0;
                maxWidth = this.maxWidth;
                break;
            case VERTICAL:
                maxWidth = this.controllingVariable.equals(this.matchCondition) ? this.maxWidth : 0;
                maxHeight = this.maxHeight;
                break;
        }

        if(this.panel == null) this.panel = new JPanel();
        this.panel.setBackground(Color.DARK_GRAY);
        this.panel.setMaximumSize(new Dimension(
            maxWidth,
            maxHeight
        ));
    }

    @Override
    public void updateAfterDataChange() {
        System.out.println("Indicator Update");

        build();
    }

    @Override
    protected UIComponent inform(UIAxis axis, UIAlignment alignment) { //Changes which direction the component shrinks to zero in
        this.axis = axis;

        return this;
    }

    @Override
    protected Component getAWTComponent() {
        build();

        return this.panel;
    }
    
}
