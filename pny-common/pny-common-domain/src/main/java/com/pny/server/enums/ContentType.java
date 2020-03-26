package com.pny.server.enums;

/**
 * 
 * @author pmyun
 *
 */
public enum ContentType {

    TEXT(1), IMAGE(2), VOICE(3), VIDEO(4);

    ContentType(int code) {
        this.code = code;
    }

    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}
