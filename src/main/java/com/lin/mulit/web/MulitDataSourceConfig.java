package com.lin.mulit.web;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * @author jianglinzou
 * @date 2019/7/2 上午11:38
 */
@Configuration
@MapperScan(basePackages = {"com.lin.dao"}, sqlSessionFactoryRef = "mulitSqlSessionFactory")
public class MulitDataSourceConfig {


    static final String MAPPER_LOCATION = "classpath*:mybatis/mapper/*.xml";

    @Bean(name = "mulitSqlSessionFactory")
    public SqlSessionFactory clusterSqlSessionFactory(@Qualifier("multiDataSource") DataSource mulitDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(mulitDataSource);
        sessionFactory.setTypeAliasesPackage("com.lin.dao");
        sessionFactory.setConfigLocation(new ClassPathResource("mybatis/mybatis-config.xml"));
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(MulitDataSourceConfig.MAPPER_LOCATION));
        return sessionFactory.getObject();
    }


}
