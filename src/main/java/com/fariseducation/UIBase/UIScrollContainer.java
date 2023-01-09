package com.fariseducation.UIBase;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JScrollPane;

public class UIScrollContainer extends UIComponent {
    private JScrollPane scrollPane;
    private UIComponent contents;

    public UIScrollContainer(UIComponent contents) {
        this.contents = contents;

        contents.getAWTComponent().setPreferredSize(
            new Dimension(
                contents.getAWTComponent().getPreferredSize().width+30,
                contents.getAWTComponent().getPreferredSize().height));
        this.scrollPane = new JScrollPane(contents.getAWTComponent());
        this.scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    }

    @Override
    protected Component getAWTComponent() {
        return this.scrollPane;
    }
}
