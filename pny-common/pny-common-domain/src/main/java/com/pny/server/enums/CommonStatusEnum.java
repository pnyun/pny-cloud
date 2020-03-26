package com.pny.server.enums;

/**
 * 状态
 *
 * @author pmyun
 */
public enum CommonStatusEnum {

    /**
     * 已删除
     */
    DELETED(2),

    /**
     * 启用
     */
    ENABLED(1),

    /**
     * 禁用
     */
    DISABLED(0);

    private int value;

    CommonStatusEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}

