package com.fariseducation.UIBase;

import java.awt.Component;

import com.fariseducation.Data.ObservedData.DataObserver;
import com.fariseducation.Data.ObservedData.ObservedGeneric;
import com.fariseducation.UIBase.UIEnums.UIAlignment;
import com.fariseducation.UIBase.UIEnums.UIAxis;

public class UIConditionalDisplay extends UIComponent implements DataObserver {
    private UIGroup contentGroup;
    private ObservedGeneric<Boolean> showConditional;
    private UIComponent[] contents;
    private UIAxis axis;

    public UIConditionalDisplay(
        ObservedGeneric<Boolean> showConditional,
        UIComponent[] contents,
        UIAxis axis)
    {
        this.showConditional = showConditional;
        this.showConditional.addObserver(this);

        this.contents = contents;
        this.contentGroup = new UIGroup(UIAxis.VERTICAL, UIAlignment.NONE, this.contents);

        this.axis = axis;
    }
    public UIConditionalDisplay(
        ObservedGeneric<Boolean> showConditional,
        UIComponent[] contents) 
    {
        this(showConditional, contents, UIAxis.VERTICAL);
    }

    private void build() {
        this.contentGroup.rebuild(this.showConditional.getVal() ? this.contents : new UIComponent[]{});
    }

    @Override
    public void updateAfterDataChange() {
        build();
    }

    @Override
    protected Component getAWTComponent() {
        build();

        return this.contentGroup.getAWTComponent();
    }
    
}
