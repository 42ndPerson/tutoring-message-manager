package com.fariseducation.UIBase;

import java.awt.Component;
import java.awt.event.WindowEvent; 

import javax.swing.JFrame;

import com.fariseducation.UIBase.UIEnums.UIAxis;

public class UIWindow extends UIComponent {
    private JFrame frame;
    private UIGroup group;
    private UIFrameConstraints constraints;

    public UIWindow(String title, UIFrameConstraints constraints, UIComponent[] components) {
        this.frame = new JFrame(title);
        this.group = new UIGroup(
            UIAxis.VERTICAL,
            components);
        this.group.setParent(this);

        this.frame.add(this.group.getAWTComponent());

        System.out.println(this.frame.getComponentCount());

        this.constraints = constraints;
        this.frame.setSize(this.constraints.getTargetWidth(), this.constraints.getTargetHeight());
        //this.frame.setLayout(null);
        this.frame.setVisible(true);
    }
    public UIWindow(String title, UIComponent[] components) {
        this(title, new UIFrameConstraints(1200,600), components);
    }

    public void closeWindow() {
        this.frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }

    @Override
    public Component getAWTComponent() {
        return this.frame;
    }
}
