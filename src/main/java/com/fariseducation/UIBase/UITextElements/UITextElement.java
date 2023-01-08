package com.fariseducation.UIBase.UITextElements;

import com.fariseducation.Data.ObservedData.DataObserver;
import com.fariseducation.Data.ObservedData.ObservedGenericImmutable;
import com.fariseducation.UIBase.UIComponent;

public abstract class UITextElement extends UIComponent implements DataObserver {
    private ObservedGenericImmutable<String> observedString = null;
    private UITextFormat format = new UITextFormat(false, false, 0, false);

    public UITextElement(String text) {
        this(new ObservedGenericImmutable<String>(text));
    }    
    public UITextElement(ObservedGenericImmutable<String> observedString) {
        this.observedString = observedString;
        this.observedString.addObserver(this);
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

    protected ObservedGenericImmutable<String> getObservedString() {
        return this.observedString;
    }

    protected abstract void build();
    protected abstract void updateText();

    @Override
    public void updateAfterDataChange() {
        updateText();
    }
}
