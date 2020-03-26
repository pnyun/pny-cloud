package com.pny.core.datasource;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;

import com.alibaba.druid.pool.DruidDataSourceFactory;

/**
 * 动态数据源工厂方法
 */
public final class DynamicDataSourceFactory {
    private final String url = "url";
    private final String username = "username";
    private final String password = "password";
    private final String driverClassName = "driver-class-name";
    private final String joiner = ".";
    private String masterPrefix;
    private String slavePrefix;

    public static DynamicDataSourceFactory build(String masterPrefix, String slavePrefix) {
        return new DynamicDataSourceFactory(masterPrefix, slavePrefix);

    }

    private DynamicDataSourceFactory(String masterPrefix, String slavePrefix) {
        this.masterPrefix = masterPrefix;
        this.slavePrefix = slavePrefix;
    }

    public DynamicDataSource createDataSource(Environment env) throws Exception {
        Properties masterProperties = new Properties();
        masterProperties.put("driverClassName", env.getProperty(this.masterPrefix + joiner + driverClassName));
        masterProperties.put("url", env.getProperty(this.masterPrefix + joiner + url));
        masterProperties.put("username", env.getProperty(this.masterPrefix + joiner + username));
        masterProperties.put("password", env.getProperty(this.masterPrefix + joiner + password));

        Properties slaveProperties = new Properties();
        slaveProperties.put("driverClassName", env.getProperty(this.slavePrefix + joiner + driverClassName));
        slaveProperties.put("url", env.getProperty(this.slavePrefix + joiner + url));
        slaveProperties.put("username", env.getProperty(this.slavePrefix + joiner + username));
        slaveProperties.put("password", env.getProperty(this.slavePrefix + joiner + password));
        Map<Object, Object> targetDataSources = new HashMap<>();

        DataSource master = DruidDataSourceFactory.createDataSource(masterProperties);
        targetDataSources.put(DatabaseType.write.getValue(), master);

        DataSource slave = DruidDataSourceFactory.createDataSource(slaveProperties);
        targetDataSources.put(DatabaseType.read.getValue(), slave);

        DynamicDataSource dataSource = new DynamicDataSource();
        dataSource.setTargetDataSources(targetDataSources);
        dataSource.setDefaultTargetDataSource(master);

        return dataSource;
    }

}
