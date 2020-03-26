package com.pny.server.upms.config;

import com.pny.core.datasource.BaseDataSourceAspect;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * 读写分离切面，动态切换数据源。
 *
 * @author pmyun
 */
@Component
@Aspect
public class DataSourceAspect extends BaseDataSourceAspect implements Ordered {

    @Override
    @Pointcut("execution(* com.pny.server.upms..*.*(..))")
    public void allService() {
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
