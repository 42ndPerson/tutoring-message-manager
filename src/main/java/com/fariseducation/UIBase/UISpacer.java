package com.fariseducation.UIBase;

import java.awt.Component;

import javax.swing.Box;
import javax.swing.plaf.DimensionUIResource;

import com.fariseducation.UIBase.UIEnums.UIAxis;


public class UISpacer extends UIComponent {
    private UIAxis axis;
    private Integer size = null;

    public UISpacer() {}
    public UISpacer(int size) {
        this.size = size;
    }

    @Override
    protected UIComponent inform(UIAxis axis) {
        this.axis = axis;

        return this;
    }

    @Override
    protected Component getAWTComponent() {
        switch (this.axis) {
            case HORIZONTAL:
                if(this.size == null) return Box.createHorizontalGlue();
                return Box.createRigidArea(new DimensionUIResource(this.size, 1));
            case VERTICAL:
                if(this.size == null) return Box.createVerticalGlue();
                return Box.createRigidArea(new DimensionUIResource(1, this.size));
            default:
                return Box.createHorizontalGlue(); //Should never need to run
        }
    }
    
}
