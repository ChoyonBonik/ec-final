package com.ecfingerprint.ecfingerprint.entity;

import java.util.List;

public class FingerData {

    private String rightThumb;
    private String rightIndex;
    private String leftThumb;
    private String leftIndex;

    public FingerData(String rightThumb, String rightIndex, String leftThumb, String leftIndex) {

        this.rightThumb = rightThumb;
        this.rightIndex = rightIndex;
        this.leftThumb = leftThumb;
        this.leftIndex = leftIndex;
    }

    public String getRightThumb() {
        return rightThumb;
    }

    public void setRightThumb(String rightThumb) {
        this.rightThumb = rightThumb;
    }

    public String getRightIndex() {
        return rightIndex;
    }

    public void setRightIndex(String rightIndex) {
        this.rightIndex = rightIndex;
    }

    public String getLeftThumb() {
        return leftThumb;
    }

    public void setLeftThumb(String leftThumb) {
        this.leftThumb = leftThumb;
    }

    public String getLeftIndex() {
        return leftIndex;
    }

    public void setLeftIndex(String leftIndex) {
        this.leftIndex = leftIndex;
    }
}
