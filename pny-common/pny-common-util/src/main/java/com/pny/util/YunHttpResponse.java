package com.pny.util;

import com.pny.exception.ErrorCodes;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;

public final class YunHttpResponse<T> implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger log = Logs.getLogger();

    /**
     * 错误码 -1 为未登录或者无权限
     */
    private int status;
    /**
     * 错误信息
     */
    private String message;
    /**
     * 用于客户端弹出提示
     */
    private String toast;
    /**
     * 时间戳
     */
    private long timestamp = System.currentTimeMillis();
    /**
     * 相应数据
     */
    private T content;

    public YunHttpResponse() {

    }

    public YunHttpResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public YunHttpResponse(int status, String message, T result) {
        this.status = status;
        this.message = message;
        this.content = result;
    }

    public YunHttpResponse(Throwable e) {
        this.status = ErrorCodes.SERVER_ERROR;
        this.message = e.getMessage();
    }

    /**
     * 返回错误信息
     */
    public static <T> YunHttpResponse<T> error(int status, String msg) {
        return new YunHttpResponse<T>(status, msg);
    }

    /**
     * <p> 直接返回成功 </p>
     */
    public static <T> YunHttpResponse<T> success() {
        return new YunHttpResponse<T>(YunHttpStatus.OK.value(), YunHttpStatus.OK.name());
    }

    /**
     * 返回成功信息 此方法将传入参数装到result中，因此只有返回业务数据时调用此方法
     */
    public static <T> YunHttpResponse<T> success(T result) {
        return new YunHttpResponse<T>(YunHttpStatus.OK.value(), YunHttpStatus.OK.name(), result);
    }

    /**
     * <p> 返回某个参数需要给予别名 <li>case:</li> <li>alias=id,content=100000 ,result={"id":100000}</li> </p>
     *
     * @param alias 别名
     * @param content 内容
     */
    public static <T> YunHttpResponse<Map<String, T>> success(String alias, T content) {
        Map<String, T> result = new HashMap<>();
        result.put(alias, content);
        return new YunHttpResponse<Map<String, T>>(YunHttpStatus.OK.value(),
            YunHttpStatus.OK.name(),
            result);

    }

    public static boolean isSuccess(YunHttpResponse<?> response) {

        if (response == null) {
            return false;
        }

        if (response.getStatus() == success().getStatus()) {
            return true;
        }

        return false;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
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
