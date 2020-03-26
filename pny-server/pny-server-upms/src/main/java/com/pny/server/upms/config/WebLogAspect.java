
package com.pny.server.upms.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pny.util.Logs;
import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponseWrapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.support.AbstractMultipartHttpServletRequest;

@Aspect
@Component
public class WebLogAspect {

    private Logger logger = Logs.getLogger();

    @Pointcut("execution(public * com.pny.server.upms.controller..*(..))")
    public void webLog() {
    }

    @Around("webLog()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        long startTime = System.currentTimeMillis();
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 记录下请求内容
        logger.info("URL:{}请求方法:{},请求开始", request.getRequestURL().toString(),request.getMethod());
//        logger.info("Params : " + Arrays.toString(pjp.getArgs()));
        printMethodArgs(pjp);
//
        // result的值就是被拦截方法的返回值
        Object result = pjp.proceed();
        try {
            logger.info("URL:{},请求结束,状态码:{},result:{} ", request.getRequestURL().toString(), attributes.getResponse().getStatus(), JSON
                .toJSONString(result));
            logger.info("Cost Time : {}ms" ,(System.currentTimeMillis() - startTime));
        } catch (Exception e) {
            //ignore
            logger.error("打印日志异常" + e.getMessage(), e);
        }
        return result;
    }

    protected void printMethodArgs(final ProceedingJoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        StringBuffer buffer = new StringBuffer("参数值[");
        try {
            if (null != args) {
                // 这里排除了一些容器相关以及无法序列化的参数()
                for (int i = 0, size = args.length; i < size; i++) {
                    if (null == args[i] || args[i] instanceof ExtendedModelMap
                        || args[i] instanceof HttpServletRequestWrapper
                        || args[i] instanceof HttpServletResponseWrapper
                        || args[i] instanceof Filter
                        || args[i] instanceof AbstractMultipartHttpServletRequest) {
                        continue;
                    }
                    String className = StringUtils.trimAllWhitespace(args[i].getClass().getName());
                    if (args[i] instanceof org.springframework.web.multipart.commons.CommonsMultipartResolver
                        || className.contains("org.springframework.web.multipart")
                        || className.contains("org.springframework.session")
                        || className.contains("org.mortbay.jetty.servlet")
                        || className.contains("org.mortbay.jetty.Request")
                        || className.contains("org.mortbay.jetty.Response")
                        || !StringUtils.hasText(args[i].getClass().getPackage().getName())
                        || args[i].getClass().getPackage().getName().startsWith("org.apache.catalina")) {
                        continue;
                    }
                    buffer.append(JSONObject.toJSONString(args[i]));
                    if (i != size - 1) {
                        buffer.append(",");
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        buffer.append("]");
        if (buffer.toString().contains(",")) {
            logger.info("调用方法{}.{},{}", joinPoint.getTarget().getClass().getName(), joinPoint.getSignature().getName(),
                buffer.toString());
        } else {
            logger.info("调用方法{}.{}", joinPoint.getTarget().getClass().getName(), joinPoint.getSignature().getName());
        }
    }
}