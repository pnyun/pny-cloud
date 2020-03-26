package com.pny.exception;

/**
 * Main exception. All non-rollback must inherit this class.
 *
 */
@SuppressWarnings("serial")
public class PnyYunException extends RuntimeException {

    private Object[] arguments;

    // 错误码 -1 为未登录或者无权限
    private int code;
    // 错误信息
    private String msg;
    private String toast;// 用于客户端弹出提示
    private long timestamp = System.currentTimeMillis();// 时间戳

    public PnyYunException() {
        super();
    }

    public PnyYunException(String msg) {
        super(msg);
    }

    public PnyYunException(String message, Throwable cause,
            Object... arguments) {
        super(message, cause);
        this.arguments = arguments;
    }

    public PnyYunException(String message, Object... arguments) {
        super(message);
        this.arguments = arguments;
    }

    public PnyYunException(int code, String message, Object... arguments) {
        super(message);
        this.arguments = arguments;
        this.code = code;
    }

    public PnyYunException(int code, String message, String toast, Object... arguments) {
        super(message);
        this.arguments = arguments;
        this.toast = toast;
        this.code = code;
    }

    public Object[] getArguments() {
        return arguments;
    }

    public void setArguments(Object[] arguments) {
        this.arguments = arguments;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getToast() {
        return toast;
    }

    public void setToast(String toast) {
        this.toast = toast;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

}
