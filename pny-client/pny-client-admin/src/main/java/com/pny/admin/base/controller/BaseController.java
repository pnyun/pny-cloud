package com.pny.admin.base.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Maps;
import com.pny.admin.upms.service.UpmsUserService;
import com.pny.core.entity.BaseEntity;
import com.pny.core.entity.CommonQuery;
import com.pny.exception.Checks;
import com.pny.exception.ErrorCodes;
import com.pny.exception.PnyYunException;
import com.pny.server.upms.entity.SystemUser;
import com.pny.server.upms.entity.ex.SystemUserDto;
import com.pny.util.Logs;
import com.pny.util.YunHttpResponse;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author pmyun
 */
public abstract class BaseController {

    private final Logger log = Logs.getLogger();

    @Autowired
    private UpmsUserService upmsUserService;

    /**
     * <p> 全局异常拦截 </p>
     */
    @ExceptionHandler({Exception.class})
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
        } else {
            result.setStatus(ErrorCodes.SERVER_ERROR);
            result.setMessage(ErrorCodes.SERVER_ERROR_MSG);
            result.setToast("服务器异常，请稍后再试！");
            result.setTimestamp(System.currentTimeMillis());
        }
        return result;
    }

    /**
     * 获取客户端IP
     */
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

    // 获取当前的操作用户
    public Long getUserid() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
            .getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        request.getHeaders("userid");
        Checks.checkArgument("不存在商户编号！", StringUtils.isNotBlank(request.getHeader("userid")));
        Long merchantId = Long.valueOf(request.getHeader("userid"));
        return merchantId;
    }

    /**
     * 获取当前的操作用户名称
     */
    public String getUsername() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
            .getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String username = "";
        try {
            username = URLDecoder.decode(request.getHeader("username"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return username;
    }

    public <T extends BaseEntity> Map<String, Object> convertQueryParam(CommonQuery query,
        T entity) {
        Map<String, Object> params = Maps.newHashMap();
        if (query != null) {
            params.put("pageIndex", query.getPageIndex());
            params.put("pageSize", query.getPageSize());
        }
        if (entity != null) {
            JSONObject jsonObject = JSONUtil.parseObj(entity);
            params.putAll(jsonObject);
        }
        return params;
    }

    /**
     * 获取当前登录用户信息
     */
    public SystemUserDto getCurrentLoginUser() {
        SecurityContext ctx = SecurityContextHolder.getContext();
        Authentication auth = ctx.getAuthentication();
        User user = (User) auth.getPrincipal();
        return upmsUserService.loadUserByUsername(user.getUsername());
    }
}
