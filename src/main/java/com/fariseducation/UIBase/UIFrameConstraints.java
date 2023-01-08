package com.fariseducation.UIBase;

public class UIFrameConstraints {
    private Integer targetWidth;
    private Integer minWidth;
    private Integer maxWidth;
    private Integer targetHeight;
    private Integer minHeight;
    private Integer maxHeight;

    public UIFrameConstraints(int targetWidth, int minWidth, int maxWidth, int targetHeight, int minHeight, int maxHeight) {
        this.targetWidth = targetWidth;
        this.minWidth = minWidth;
        this.maxWidth = maxWidth;
        this.targetHeight = targetHeight;
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
    }
    public UIFrameConstraints(int width, int height) {
        this(width, width, width, height, height, height);
    }
    public UIFrameConstraints() {
        this.targetWidth = null;
        this.minWidth = null;
        this.maxWidth = null;
        this.targetHeight = null;
        this.minHeight = null;
        this.maxHeight = null;
    }

    public Integer getTargetWidth() {
        return this.targetWidth;
    }
    public Integer getMinWidth() {
        return this.minWidth;
    }
    public Integer getMaxWidth() {
        return this.maxWidth;
    }
    public Integer getTargetHeight() {
        return this.targetHeight;
    }
    public Integer getMinHeight() {
        return this.minHeight;
    }
    public Integer getMaxHeight() {
        return this.maxHeight;
    }
    public void setTargetWidth(int targetWidth) {
        this.targetWidth = targetWidth;
    }
    public void setMinWidth(int minWidth) {
        this.minWidth = minWidth;
    }
    public void setMaxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
    }
    public void setTargetHeight(int targetHeight) {
        this.targetHeight = targetHeight;
    }
    public void setMinHeight(int minHeight) {
        this.minHeight = minHeight;
    }
    public void setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
    }
}
