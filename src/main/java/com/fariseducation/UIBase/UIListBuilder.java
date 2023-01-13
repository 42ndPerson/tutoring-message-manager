package com.fariseducation.UIBase;

import java.awt.Component;
import java.util.ArrayList;
import java.util.function.Function;

import com.fariseducation.Data.ObservedData.DataObserver;
import com.fariseducation.Data.ObservedData.ObservedLockedList;
import com.fariseducation.UIBase.UIEnums.UIAxis;

public class UIListBuilder<InputType> extends UIComponent implements DataObserver {
    private UIGroup content;
    private ObservedLockedList<InputType> inputElements;
    private Function<InputType,UIComponent> builder;
    private UIAxis axis;

    public UIListBuilder(
        ArrayList<InputType> inputElements, 
        Function<InputType,UIComponent> builder,
        UIAxis axis) {

        this(new ObservedLockedList<InputType>(inputElements), builder, axis);
    }
    public UIListBuilder(
        ObservedLockedList<InputType> inputElements, 
        Function<InputType,UIComponent> builder,
        UIAxis axis) {

        this.inputElements = inputElements;
        this.inputElements.addObserver(this);
        this.builder = builder;
        this.axis = axis;

        System.out.print("Construction Grs:");
        inputElements.print();

        this.content = new UIGroup(this.axis, new UIComponent[]{});

        build();
    }

    private void build() {
        System.out.println(this.inputElements.size());
        UIComponent[] assembledComponents = new UIComponent[this.inputElements.size()];

        System.out.println("Build Grs:");
        inputElements.print();
        System.out.println("---");


        for(int i = 0; i < this.inputElements.size(); i++) {
            assembledComponents[i] = builder.apply(this.inputElements.get(i));
        }
        System.out.println("AC: " + assembledComponents.length);
        this.content.rebuild(assembledComponents);
    }

    @Override
    protected Component getAWTComponent() {
        return this.content.getAWTComponent();
    }

    @Override
    public void updateAfterDataChange() {
        System.out.println("UIListBuilder Update: " + this.toString());
        build();
    }
}
