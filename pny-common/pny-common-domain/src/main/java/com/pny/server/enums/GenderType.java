package com.pny.server.enums;

/**
 * 性别
 *
 * @author pmyun
 */
public enum GenderType {

    FAMALE(1, "女", "famale"),
    MALE(2, "男", "male"),
    UNKNOWN(0, "未知", "unknown");

    private int code;
    private String text;
    private String textEn;

    GenderType(int code, String text, String textEn) {
        this.code = code;
        this.text = text;
        this.textEn = textEn;
    }

    public static GenderType of(int code) {

        if (code == MALE.getCode()) {
            return MALE;
        }
        if (code == FAMALE.getCode()) {
            return FAMALE;
        }

        return null;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTextEn() {
        return textEn;
    }

    public void setTextEn(String textEn) {
        this.textEn = textEn;
    }

    @Override
    public String toString() {
        return text;
    }

}
