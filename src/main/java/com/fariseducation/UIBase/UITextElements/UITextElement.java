package com.fariseducation.UIBase.UITextElements;

import com.fariseducation.Data.ObservedData.DataObserver;
import com.fariseducation.Data.ObservedData.ObservedGeneric;
import com.fariseducation.UIBase.UIComponent;
import com.fariseducation.UIBase.UIGroup;
import com.fariseducation.UIBase.UIEnums.UIAxis;

public abstract class UITextElement extends UIComponent implements DataObserver {
    private ObservedGeneric observed = null;
    private UITextFormat format = new UITextFormat(false, false, 0, false);

    public UITextElement(String text) {
        this(new ObservedGeneric<String>(text));
    }    
    public UITextElement(ObservedGeneric observedGeneric) {
        System.out.println("Text Element: " + observedGeneric.getVal());
        this.observed = observedGeneric;
        this.observed.addObserver(this);
        build();
        updateText();
    }

    public UITextElement format() {
        this.format = new UITextFormat(false, false, 0, true);
        updateText();

        return this;
    }
    public UITextElement format(boolean bold, boolean italicized, int relativeTextSize) {
        this.format = new UITextFormat(bold, italicized, relativeTextSize, false);
        updateText();

        return this;
    }
    public UITextFormat getFormat() {
        return this.format;
    }

    protected ObservedGeneric getObserved() {
        return this.observed;
    }

    public UIComponent setMaxSize(Integer width, Integer height) {
        return new UIGroup(UIAxis.VERTICAL, new UIComponent[]{
            this
        }).setMaxSize(width, height);
    }
    public UIComponent setMinSize(Integer width, Integer height) {
        return new UIGroup(UIAxis.VERTICAL, new UIComponent[]{
            this
        }).setMinSize(width, height);
    }
    public UIComponent setPreferredSize(Integer width, Integer height) {
        return new UIGroup(UIAxis.VERTICAL, new UIComponent[]{
            this
        }).setPreferredSize(width, height);
    }
    public UIComponent maximize() {
        return new UIGroup(UIAxis.VERTICAL, new UIComponent[]{
            this
        }).maximize();
    }

    protected abstract void build();
    protected abstract void updateText();

    @Override
    public void updateAfterDataChange() {
        updateText();
    }
}
