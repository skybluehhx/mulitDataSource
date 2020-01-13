package com.lin.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * @author jianglinzou
 * @date 2019/7/12 下午5:37
 */
@Configuration
@MapperScan(basePackages = {"com.lin.dao"}, sqlSessionFactoryRef = "mulitSqlSessionFactory")
public class MulitDataSourceConfig  extends BaseMulitDataSourceConfig {


    @Qualifier("mulitSqlSessionFactory")
    @Autowired
    private SqlSessionFactory sqlSessionFactory;



    @Override
    public SqlSessionFactory getSqlSessionFactory() {
        return this.sqlSessionFactory;
    }

}
