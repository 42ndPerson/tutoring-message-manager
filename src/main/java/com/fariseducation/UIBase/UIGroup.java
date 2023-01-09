package com.fariseducation.UIBase;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import com.fariseducation.UIBase.UIEnums.UIAlignment;
import com.fariseducation.UIBase.UIEnums.UIAxis;
import com.fariseducation.UIBase.UITextElements.UILabel;

public class UIGroup extends UIComponent {
    private JPanel pane;
    private ArrayList<UIComponent> children;
    private UIAxis axis;
    private UIAlignment alignment;

    public UIGroup(UIAxis axis, UIComponent[] components) {
        this.build(axis, UIAlignment.NONE, components);
    }
    public UIGroup(UIAxis axis, UIAlignment alignment, UIComponent[] components) {
        this.build(axis, alignment, components);
    }

    private void build(UIAxis axis, UIAlignment alignment, UIComponent[] components) {
        this.pane = new JPanel();
        this.children = new ArrayList<UIComponent>(); 

        this.axis = axis;
        this.pane.setLayout(new BoxLayout(this.pane,this.axis.getNumericValue()));
        this.alignment = alignment;

        assembleChildren(components);
    }
    
    public void rebuild(UIComponent[] newComponents) {
        this.pane.removeAll();
        this.children.add(
            new UILabel("test")
        );
        assembleChildren(newComponents);
        this.pane.revalidate();

        System.out.println(this.children.size());
        System.out.println("UIGroup Update");
    }

    /**
     * @param components
     * Takes and array of UIComponents and adds them as children to the UIGroup
     */
    private void assembleChildren(UIComponent[] components) {

        if (
            !(this.alignment == UIAlignment.NONE) && 
            (this.alignment == UIAlignment.TRAILING || this.alignment == UIAlignment.CENTER)) {
            addChild(new UISpacer().inform(this.axis, this.alignment));
        }

        for(UIComponent component: components) {
            component.inform(this.axis, this.alignment);
            addChild(component);
        }

        if (
            !(this.alignment == UIAlignment.NONE) && 
            (this.alignment == UIAlignment.LEADING || this.alignment == UIAlignment.CENTER)) {
            addChild(new UISpacer().inform(this.axis, this.alignment));
        }
    }
    /**
     * @param child
     * Connects child to UIGroup object and adds to pane
     */
    private void addChild(UIComponent child) {
        child.setParent(this);

        this.pane.add(child.getAWTComponent());
        
        this.children.add(child);
    }

    @Override
    protected Component getAWTComponent() {
        return this.pane;
    }
}
