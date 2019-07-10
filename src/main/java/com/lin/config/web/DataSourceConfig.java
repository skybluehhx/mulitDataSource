package com.lin.config.web;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 *
 */

@Configuration
public class DataSourceConfig {

    @Bean(name = "dataSourceTwo")
    @Qualifier("dataSourceTwo")
    @ConfigurationProperties(prefix = "spring.datasource.one")
    public DataSource shangtongdaiDataSource() {
        return DataSourceBuilder.create().build();
    }


    @Bean(name = "dataSourceOne")
    @Qualifier("dataSourceOne")
    @ConfigurationProperties(prefix = "spring.datasource.two")
    @Primary
    public DataSource kingDeeDataSource() {
        return DataSourceBuilder.create().build();
    }

}
