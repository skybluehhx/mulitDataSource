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

    static final String MAPPER_LOCATION = "classpath*:mybatis/mapper/*.xml";

    @Qualifier("mulitSqlSessionFactory")
    @Autowired
    private SqlSessionFactory sqlSessionFactory;


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

    @Override
    public SqlSessionFactory getSqlSessionFactory() {
        return this.sqlSessionFactory;
    }

}
