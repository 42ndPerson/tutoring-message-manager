package com.fariseducation.UIBase.UITextElements;

import com.fariseducation.Data.ObservedData.DataObserver;
import com.fariseducation.Data.ObservedData.ObservedGeneric;
import com.fariseducation.UIBase.UIComponent;

public abstract class UITextElement extends UIComponent implements DataObserver {
    private ObservedGeneric<String> observedString = null;
    private UITextFormat format = new UITextFormat(false, false, 0, false);

    public UITextElement(String text) {
        this(new ObservedGeneric<String>(text));
    }    
    public UITextElement(ObservedGeneric<String> observedString) {
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

    protected ObservedGeneric<String> getObservedString() {
        return this.observedString;
    }

    protected abstract void build();
    protected abstract void updateText();

    @Override
    public void updateAfterDataChange() {
        updateText();
    }
}
