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

        assembleChildren(newComponents);
        this.pane.revalidate();
        this.pane.repaint();
    }

    /**
     * @param components
     * Takes and array of UIComponents and adds them as children to the UIGroup
     */
    private void assembleChildren(UIComponent[] components) {
        for(UIComponent component: components) {
            component.inform(this.axis);
            addChild(component);
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
