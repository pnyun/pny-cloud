package com.pny.core.datasource;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * 数据源切面基类，子类需实现allService()方法即可完成切面。
 *
 */
@Slf4j
public abstract class BaseDataSourceAspect {
    /**
     * mybatis plus 通用方法切点
     */
    @Pointcut("execution(* com.baomidou.mybatisplus.service..*.*(..))")
    public void superService() {
    }

    public abstract void allService();

    @Before("allService() or superService()")
    public void setReadDataSourceType(JoinPoint point) {
        String method = point.getSignature().getName();
        if (method.matches("^(select|read|find|get|list).*")) {
            DatabaseContextHolder.setDatabaseType(DatabaseType.read.getValue());
        } else {
            DatabaseContextHolder.setDatabaseType(DatabaseType.write.getValue());
        }
        //log.info("current lookupkey : {} , method : {} ", DatabaseContextHolder.getDatabaseType() , method);
    }
}
