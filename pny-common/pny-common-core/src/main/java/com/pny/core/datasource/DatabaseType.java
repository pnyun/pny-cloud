package com.pny.core.datasource;

/**
 * 读写类型
 *
 */
public enum DatabaseType {
    read("slave"), write("master");

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    DatabaseType(String value) {
        this.value = value;
    }
}
