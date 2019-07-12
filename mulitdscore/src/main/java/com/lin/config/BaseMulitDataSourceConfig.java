package com.lin.config;

import com.lin.support.ibatis.MulitMapperRegistry;
import org.apache.ibatis.binding.MapperRegistry;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

/**
 * @author jianglinzou
 * @date 2019/7/2 上午11:38
 */

public abstract class BaseMulitDataSourceConfig implements InitializingBean {




    public BaseMulitDataSourceConfig() {

    }


    public void afterPropertiesSet() throws Exception {
        init();
    }

    private void init() {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        alterMapperRegistry(sqlSessionFactory);
    }


    protected void alterMapperRegistry(SqlSessionFactory sqlSessionFactory) {
        Field configField = ReflectionUtils.findField(sqlSessionFactory.getClass(), "configuration", org.apache.ibatis.session.Configuration.class);
        ReflectionUtils.makeAccessible(configField);
        org.apache.ibatis.session.Configuration configuration = (org.apache.ibatis.session.Configuration) ReflectionUtils.getField(configField, sqlSessionFactory);
        Field registryField = ReflectionUtils.findField(org.apache.ibatis.session.Configuration.class, "mapperRegistry", MapperRegistry.class);
        ReflectionUtils.makeAccessible(registryField);
        //修改sqlSessionFactory中configuration属性的MapperRegistry的实现
        ReflectionUtils.setField(registryField, configuration, new MulitMapperRegistry(configuration)); //将字段替换为我们自定义的MulitMapperRegistry

    }

    public abstract SqlSessionFactory getSqlSessionFactory();


}
