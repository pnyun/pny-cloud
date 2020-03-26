package com.pny.server.upms.config;

import com.pny.core.datasource.DynamicDataSource;
import com.pny.core.datasource.DynamicDataSourceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

/**
 * 数据源配置，使用dynamicdatasource。并使用driud数据源
 *
 * @author pmyun
 */
@Configuration
public class DataSourceConfig {
    @Autowired
    private Environment env;

    @Bean
    @Primary
    @RefreshScope
    public DynamicDataSource dataSource() throws Exception {
        return DynamicDataSourceFactory.build("master.datasource", "slave.datasource").createDataSource(env);
    }

    /**
     * 配置事务管理器
     */
    @Bean
    public DataSourceTransactionManager transactionManager(DynamicDataSource dataSource) throws Exception {
        return new DataSourceTransactionManager(dataSource);
    }
}
