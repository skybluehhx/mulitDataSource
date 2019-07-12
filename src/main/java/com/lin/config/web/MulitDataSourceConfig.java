package com.lin.config.web;

import com.lin.support.ibatis.MulitMapperRegistry;
import org.apache.ibatis.binding.MapperRegistry;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.ReflectionUtils;

import javax.sql.DataSource;
import java.lang.reflect.Field;

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
        SqlSessionFactory sqlSessionFactory = sessionFactory.getObject();
        alterMapperRegistry(sqlSessionFactory);
        return sqlSessionFactory;
    }


    protected void alterMapperRegistry(SqlSessionFactory sqlSessionFactory) {
        Field configField = ReflectionUtils.findField(sqlSessionFactory.getClass(), "configuration", org.apache.ibatis.session.Configuration.class);
        ReflectionUtils.makeAccessible(configField);
        org.apache.ibatis.session.Configuration configuration = (org.apache.ibatis.session.Configuration) ReflectionUtils.getField(configField, sqlSessionFactory);
        Field registryField = ReflectionUtils.findField(org.apache.ibatis.session.Configuration.class, "mapperRegistry", MapperRegistry.class);
        ReflectionUtils.makeAccessible(registryField);
        //修改sqlSessionFactory中MapperRegistry的实现
        ReflectionUtils.setField(registryField, configuration, new MulitMapperRegistry(configuration)); //将字段替换为我们自定义的MapperRegistry

    }


}
