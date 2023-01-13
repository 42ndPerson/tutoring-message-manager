package com.fariseducation.UIBase;

import java.awt.Component;
import java.awt.Dimension;

import com.fariseducation.UIBase.UIEnums.UIAxis;

public abstract class UIComponent {
    protected UIComponent parent;

    public int getWidth() {
        return this.getAWTComponent().getWidth();
    }
    public int getHeight() {
        return this.getAWTComponent().getHeight();
    }
    public UIComponent getParent() {
        return this.parent;
    }
    public UIComponent setParent(UIComponent parent) {
        this.parent = parent;

        return this;
    }
    public UIComponent setMaxSize(Integer width, Integer height) {
        if(width == null && height == null) {
            return this;
        }
        else if(height == null) {
            this.getAWTComponent().setMaximumSize(
                new Dimension(
                    width, 
                    this.getAWTComponent().getPreferredSize().height
            ));
            return this;
        }
        else if(width == null) {
            this.getAWTComponent().setMaximumSize(
                new Dimension(
                    this.getAWTComponent().getPreferredSize().width,
                    height
            ));
            return this;
        }
        else {
            this.getAWTComponent().setMaximumSize(
                new Dimension(
                    width,
                    height
            ));
            return this;
        }
    }
    public UIComponent setMinSize(Integer width, Integer height) {
        if(width == null && height == null) {
            return this;
        }
        else if(height == null) {
            this.getAWTComponent().setMinimumSize(
                new Dimension(
                    width, 
                    this.getAWTComponent().getPreferredSize().height
            ));
            return this;
        }
        else if(width == null) {
            this.getAWTComponent().setMinimumSize(
                new Dimension(
                    this.getAWTComponent().getPreferredSize().width,
                    height
            ));
            return this;
        }
        else {
            this.getAWTComponent().setMinimumSize(
                new Dimension(
                    width,
                    height
            ));
            return this;
        }
    }
    public UIComponent setPreferredSize(Integer width, Integer height) {
        if(width == null && height == null) {
            return this;
        }
        else if(height == null) {
            this.getAWTComponent().setPreferredSize(
                new Dimension(
                    width, 
                    this.getAWTComponent().getPreferredSize().height
            ));
            return this;
        }
        else if(width == null) {
            this.getAWTComponent().setPreferredSize(
                new Dimension(
                    this.getAWTComponent().getPreferredSize().width,
                    height
            ));
            return this;
        }
        else {
            this.getAWTComponent().setPreferredSize(
                new Dimension(
                    width,
                    height
            ));
            return this;
        }
    }
    public UIComponent maximize() {
        this.getAWTComponent().setPreferredSize(
                new Dimension(
                    1500,
                    1500
            ));
            return this;
    }
    protected UIComponent inform(UIAxis axis) { return this; }
    protected abstract Component getAWTComponent();
}
