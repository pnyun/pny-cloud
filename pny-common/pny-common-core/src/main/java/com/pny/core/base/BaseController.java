package com.pny.core.base;

import com.pny.exception.ErrorCodes;
import com.pny.exception.PnyYunException;
import com.pny.util.YunHttpResponse;
import com.pny.util.Logs;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * 
 * @author pmyun
 */
public abstract class BaseController {

    private final Logger log = Logs.getLogger();

    /**
     * <p>
     * 全局异常拦截
     * </p>
     * 
     * @param request
     * @param response
     * @param e
     * @return
     */
    @ExceptionHandler({ Exception.class })
    @ResponseBody
    public <T> YunHttpResponse<T> exception(HttpServletRequest request,
            HttpServletResponse response, Exception e) {
        log.error("error intercept___________:", e);
        // 需要过滤相关异常，做精准返回，比如传入数据的格式问题
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        return exceptionResult(e);
    }

    public static <T> YunHttpResponse<T> exceptionResult(Exception e) {
        YunHttpResponse<T> result = new YunHttpResponse<>();
        if (e instanceof PnyYunException) {
            PnyYunException he = (PnyYunException) e;
            result.setStatus(he.getCode());
            result.setMessage(he.getMessage());
            result.setToast(he.getToast());
            result.setTimestamp(he.getTimestamp());
        } else if (e instanceof MissingServletRequestParameterException) {
            result.setStatus(ErrorCodes.MISSING_PARAM_ERROR);
            result.setMessage(e.getMessage());
            result.setToast("缺少参数！");
            result.setTimestamp(System.currentTimeMillis());
        } else if (e instanceof HttpMessageNotReadableException) {
            result.setStatus(ErrorCodes.MISSING_PARAM_ERROR);
            result.setMessage(e.getMessage());
            result.setToast("缺少参数！");
            result.setTimestamp(System.currentTimeMillis());
        } else {
            result.setStatus(500);
            result.setMessage("服务器异常，请稍后再试！");
            result.setToast("服务器异常，请稍后再试！");
            result.setTimestamp(System.currentTimeMillis());
        }
        return result;
    }

    /** 获取客户端IP */
    public static final String getHost(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if ("127.0.0.1".equals(ip)) {
            InetAddress inet = null;
            try { // 根据网卡取本机配置的IP
                inet = InetAddress.getLocalHost();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            ip = inet.getHostAddress();
        }
        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ip != null && ip.length() > 15) {
            if (ip.indexOf(",") > 0) {
                ip = ip.substring(0, ip.indexOf(","));
            }
        }
        return ip;
    }
}
