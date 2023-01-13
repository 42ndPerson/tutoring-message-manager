package com.fariseducation.UIBase;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import com.fariseducation.UIBase.UIEnums.UIAxis;

public class UIGroup extends UIComponent {
    private JPanel pane;
    private ArrayList<UIComponent> children;
    private UIAxis axis;

    public UIGroup(UIAxis axis, UIComponent[] components) {
        this.build(axis, components);
    }

    private void build(UIAxis axis, UIComponent[] components) {
        this.pane = new JPanel();
        this.children = new ArrayList<UIComponent>(); 

        this.axis = axis;
        this.pane.setLayout(new BoxLayout(this.pane,this.axis.getNumericValue()));

        assembleChildren(components);
    }
    
    public void rebuild(UIComponent[] newComponents) {
        this.pane.removeAll();
        this.children.clear();

        System.out.println("NC: " + newComponents.length);

        assembleChildren(newComponents);
        this.pane.revalidate();
        this.pane.repaint();

        System.out.println(this.children.size());
        System.out.println("UIGroup Update");
    }

    /**
     * @param components
     * Takes and array of UIComponents and adds them as children to the UIGroup
     */
    private void assembleChildren(UIComponent[] components) {
        for(UIComponent component: components) {
            System.out.println("*****");
            component.inform(this.axis);
            addChild(component);
        }

        System.out.println("CC: " + this.pane.getComponentCount());
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
