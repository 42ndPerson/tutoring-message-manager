package com.fariseducation.UIBase;

import java.awt.Component;

import javax.swing.JSeparator;

import com.fariseducation.UIBase.UIEnums.UIAlignment;
import com.fariseducation.UIBase.UIEnums.UIAxis;

public class UISeparator extends UIComponent {
    private UIAxis axis;

    @Override
    protected UIComponent inform(UIAxis axis, UIAlignment alignment) {
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
