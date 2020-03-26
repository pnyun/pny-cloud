package com.pny.exception;

/**
 * 错误应答码列表
 * 
 * @author pmyun
 *
 */
public class ErrorCodes {

    // 成功
    public static final int OK = 200;

    // 成功
    public static final String OK_MSG = "OK";

    public static final int AUTHENTICATION = -1;

    public static final String AUTHENTICATION_MSG = "请先登录！";

    public static final int FORBIDDEN = -2;

    public static final String FORBIDDEN_MSG = "无操作权限！";

    // 资源不存在
    public static final int NO_CONTENT = 498;

    // 资源不存在
    public static final String NO_CONTENT_MSG = "资源不存在";

    // 缺少必填参数
    public static final int MISSING_PARAM_ERROR = 499;

    public static final String MISSING_PARAM_ERROR_MSG = "缺少必填参数";

    // 服务异常
    public static final int SERVER_ERROR = 500;

    public static final String SERVER_ERROR_MSG = "服务异常";

    public static final int DB_ERROR = 501;

    public static final String DB_ERROR_MSG = "数据库异常";

    public static final int ARGUMENT_FALSE = 580;

    public static final String ARGUMENT_FALSE_MSG = "校验失败";

    public static final int DATA_MULTI = 590;

    public static final String DATA_MULTI_MSG = "数据不存在或者存在干扰数据";

    // 未知异常
    public static final int UNKNOWN_ERROR = 599;

    public static final String UNKNOWN_ERROR_MSG = "未知异常";
    

}
