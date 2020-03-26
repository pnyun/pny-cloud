package com.pny.core.datasource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 找到targetDataSource。key为master，slave，为创建dynamicDatasource时设置key。
 */
@Slf4j
public class DynamicDataSource extends AbstractRoutingDataSource {

    protected Object determineCurrentLookupKey() {
//        log.info("current lookupkey : {} ", DatabaseContextHolder.getDatabaseType());
        return DatabaseContextHolder.getDatabaseType();
    }
}
