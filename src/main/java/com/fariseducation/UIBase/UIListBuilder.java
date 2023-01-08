package com.fariseducation.UIBase;

import java.awt.Component;
import java.util.ArrayList;
import java.util.function.Function;

import com.fariseducation.Data.ObservedData.DataObserver;
import com.fariseducation.Data.ObservedData.ObservedLockedList;
import com.fariseducation.UIBase.UIEnums.UIAlignment;
import com.fariseducation.UIBase.UIEnums.UIAxis;

public class UIListBuilder<InputType> extends UIComponent implements DataObserver {
    private UIGroup content;
    private ObservedLockedList<InputType> inputElements;
    private Function<InputType,UIComponent> builder;
    private UIAxis axis;
    private UIAlignment alignment;

    public UIListBuilder(
        ArrayList<InputType> inputElements, 
        Function<InputType,UIComponent> builder,
        UIAxis axis,
        UIAlignment alignment) {

        this(new ObservedLockedList<InputType>(inputElements), builder, axis, alignment);
    }
    public UIListBuilder(
        ObservedLockedList<InputType> inputElements, 
        Function<InputType,UIComponent> builder,
        UIAxis axis,
        UIAlignment alignment) {

        this.inputElements = inputElements;
        this.inputElements.addObserver(this);
        this.builder = builder;
        this.axis = axis;
        this.alignment = alignment;

        this.content = new UIGroup(this.axis, this.alignment, new UIComponent[]{});

        build();
    }

    private void build() {
        UIComponent[] assembledComponents = new UIComponent[this.inputElements.size()];

        for(int i = 0; i < this.inputElements.size(); i++) {
            assembledComponents[i] = builder.apply(this.inputElements.get(i));
        }

        this.content.rebuild(assembledComponents);
    }

    @Override
    protected Component getAWTComponent() {
        return this.content.getAWTComponent();
    }

    @Override
    public void updateAfterDataChange() {
        build();
    }
    
}
