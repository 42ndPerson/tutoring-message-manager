package com.fariseducation.UIBase.UITextElements;

public class UITextFormat {
    private boolean bold;
    private boolean italicized;
    private int relativeTextSize;
    private boolean disableHTML;

    public UITextFormat(boolean bold, boolean italicized, int relativeTextSize, boolean disableHTML) {
        this.bold = bold;
        this.italicized = italicized;
        this.relativeTextSize = relativeTextSize;
        this.disableHTML = disableHTML;
    }

    public String getStartCap() {
        if(this.disableHTML) return "";

        String cap = "<html>";
        if(this.bold) {
            cap += "<b>";
        }
        if(this.italicized) {
            cap += "<i>";
        }
        if(this.relativeTextSize != 0) {
            cap += "<font size=\"+" + this.relativeTextSize + "\">";
        }

        return cap;
    }
    public String getEndCap() {
        if(this.disableHTML) return "";

        String cap = "";
        if(this.relativeTextSize != 0) {
            cap += "</font size=\"+" + this.relativeTextSize + "\">";
        }
        if(this.italicized) {
            cap += "</i>";
        }
        if(this.bold) {
            cap += "</b>";
        }
        cap += "</html>";
        
        return cap;
    }
}
