package com.pny.server.upms.config;

import com.pny.core.idwork.IdWorker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IdWorkerConfig {

    @Value("${idworker.workerId:1}")
    private String workerId;
    @Value("${idworker.dataCenterId:0}")
    private String datacenterId;

    @Bean
    public IdWorker getIdWroker() {
        return new IdWorker();
    }

}
