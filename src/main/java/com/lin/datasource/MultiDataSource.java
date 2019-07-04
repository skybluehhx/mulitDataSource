package com.lin.datasource;

import lombok.Data;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * @author jianglinzou
 * @date 2019/7/2 下午7:02
 */
@Data
@Component
public class MultiDataSource implements DataSource, ApplicationContextAware {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(MultiDataSource.class);

    @Value("${mulitdatasource.defaultDataSourceBeanName}")
    private String defaultDataSourceBeanName;
    //默认的数据源
    private DataSource defaultDataSource = null;


    //上下文
    private ApplicationContext applicationContext;

    @Override
    public Connection getConnection() throws SQLException {
        DataSource dataSource = getDataSource();
        return dataSource.getConnection();

    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        DataSource dataSource = getDataSource();
        return dataSource.getConnection(username, password);

    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        DataSource dataSource = getDataSource();
        if (Objects.isNull(dataSource)) {
            logger.info("get dataSource from threadlocal fail,will user defaultDataSource", defaultDataSource);
            return defaultDataSource.unwrap(iface);
        } else {
            return dataSource.unwrap(iface);
        }
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return getDataSource().isWrapperFor(iface);
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return getDataSource().getLogWriter();
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        getDataSource().setLogWriter(out);
        return;
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        getDataSource().setLoginTimeout(seconds);
        return;
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return getDataSource().getLoginTimeout();

    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return getDataSource().getParentLogger();
    }


    public void setDefaultDataSource(DataSource dataSource) {

        this.defaultDataSource = dataSource;
    }

    @PostConstruct
    public void configDefaultDataSource() {
        System.out.println("========="+this.defaultDataSourceBeanName+"======");
        this.defaultDataSource = (DataSource) applicationContext.getBean(defaultDataSourceBeanName);
    }


    public DataSource getDefaultDataSource() {
        return this.defaultDataSource;
    }


    protected DataSource getDataSource() {
        String dataSourceName = MulitDataSourceSupport.getDataSourceName();
        logger.info("will to get dataSourceName:{}", dataSourceName);
        DataSource dataSource = getDataSourceFromApplication(dataSourceName);
        return dataSource;

    }

    public DataSource getDataSourceFromApplication(String dataSourceName) {

        try {
            if (Strings.isBlank(dataSourceName)) {
                logger.warn("dataSouceName :{} is not valid,will user defaultDataSourceName:{}", dataSourceName, defaultDataSourceBeanName);
                return this.defaultDataSource;
            }
            return (DataSource) this.applicationContext.getBean(dataSourceName);
        } catch (NoSuchBeanDefinitionException e) {
            logger.warn("there is no dataSourceName:{} ,will use defaultDataSource", dataSourceName);
            return this.defaultDataSource;
        }


    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;

    }


}
