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
        UIAxis axis) {

        this(new ObservedLockedList<InputType>(inputElements), builder, axis, UIAlignment.NONE);
    }
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
        UIAxis axis) {

        this(inputElements, builder, axis, UIAlignment.NONE);
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

        System.out.print("Construction Grs:");
        inputElements.print();

        this.content = new UIGroup(this.axis, this.alignment, new UIComponent[]{});

        build();
    }

    private void build() {
        UIComponent[] assembledComponents = new UIComponent[this.inputElements.size()];

        System.out.println("Build Grs:");
        inputElements.print();
        System.out.println("---");

        for(int i = 0; i < this.inputElements.size(); i++) {
            System.out.println(this.inputElements);
            System.out.println(this.inputElements.get(i));
            System.out.println(this.inputElements.getClass());
            System.out.println(this.inputElements.get(i).getClass());
            assembledComponents[i] = builder.apply(this.inputElements.get(i));
        }

        this.content.rebuild(assembledComponents);
        System.out.println("ListBuilder Update");
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
