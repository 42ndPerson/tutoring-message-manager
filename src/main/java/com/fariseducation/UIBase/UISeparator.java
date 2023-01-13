package com.fariseducation.UIBase;

import java.awt.Component;

import javax.swing.JSeparator;

import com.fariseducation.UIBase.UIEnums.UIAxis;

public class UISeparator extends UIComponent {
    private UIAxis axis;

    @Override
    protected UIComponent inform(UIAxis axis) {
        this.axis = axis;

        return this;
    }

    @Override
    protected Component getAWTComponent() {
        return new JSeparator(
            this.axis.getNumericValue() == 1 ? 
            0 :
            1);
    }
    
}
