package com.lin.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * @author jianglinzou
 * @date 2020/1/9 下午12:23
 */
@Configuration
public class SqlSessionFactoryConfig {


    static final String MAPPER_LOCATION = "classpath*:mybatis/mapper/*.xml";





    //    @ConditionalOnMissingBean("mulitSqlSessionFactory")
    @Bean(name = "mulitSqlSessionFactory")
    public SqlSessionFactory mulitSqlSessionFactory(@Qualifier("multiDataSource") DataSource mulitDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(mulitDataSource);
        sessionFactory.setTypeAliasesPackage("com.lin.dao");
        sessionFactory.setConfigLocation(new ClassPathResource("mybatis/mybatis-config.xml"));
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(MAPPER_LOCATION));
        SqlSessionFactory sqlSessionFactory = sessionFactory.getObject();
//        alterMapperRegistry(sqlSessionFactory);
//        this.sqlSessionFactory = sqlSessionFactory;
        return sqlSessionFactory;
//    }

    }
}