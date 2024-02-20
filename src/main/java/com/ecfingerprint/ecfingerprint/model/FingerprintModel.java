package com.ecfingerprint.ecfingerprint.model;

public class FingerprintModel {
    private String dateOfBirth;
    private String nid10Digit;
    private String nid17Digit;

    private String rightThumbUrl;
    private String rightIndexUrl;
    private String leftThumbUrl;
    private String leftIndexUrl;

    private String resultCheckUrl;



    public String getRightThumbUrl() {
        return rightThumbUrl;
    }

    public void setRightThumbUrl(String rightThumbUrl) {
        this.rightThumbUrl = rightThumbUrl;
    }

    public String getRightIndexUrl() {
        return rightIndexUrl;
    }

    public void setRightIndexUrl(String rightIndexUrl) {
        this.rightIndexUrl = rightIndexUrl;
    }

    public String getLeftThumbUrl() {
        return leftThumbUrl;
    }

    public void setLeftThumbUrl(String leftThumbUrl) {
        this.leftThumbUrl = leftThumbUrl;
    }

    public String getLeftIndexUrl() {
        return leftIndexUrl;
    }

    public void setLeftIndexUrl(String leftIndexUrl) {
        this.leftIndexUrl = leftIndexUrl;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getNid10Digit() {
        return nid10Digit;
    }

    public void setNid10Digit(String nid10Digit) {
        this.nid10Digit = nid10Digit;
    }

    public String getNid17Digit() {
        return nid17Digit;
    }

    public void setNid17Digit(String nid17Digit) {
        this.nid17Digit = nid17Digit;
    }

    public String getResultCheckUrl() {
        return resultCheckUrl;
    }

    public void setResultCheckUrl(String resultCheckUrl) {
        this.resultCheckUrl = resultCheckUrl;
    }
}
