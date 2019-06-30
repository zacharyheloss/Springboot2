package com.zachary.springboot.blog.pushlian.mrdatasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @Auther: 
 * @Date: 
 * @Description: 数据源切换
 */
public class DynamicRoutingDataSource extends AbstractRoutingDataSource {


    @Override
    protected Object determineCurrentLookupKey() {
        //String dataSourceName = DynamicDataSourceContextHolder.getDataSourceRouterKey();
        return DynamicDataSourceContextHolder.getDataSourceRouterKey();
    }
}
