package com.lin.support.ibatis;


import org.apache.ibatis.binding.MapperRegistry;
import org.apache.ibatis.session.Configuration;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

/**
 * @author jianglinzou
 * @date 2019/7/12 上午11:08
 */
public class MulitConfiguration extends Configuration {


    //需要替换的MapperRegistry
    private static final String MAPPER_REGISTRY = "mapperRegistry";

    public MulitConfiguration() {
        init();
    }

    private void init() { //主要是利用反射，将Configuration中的MapperRegistry 替换为MulitMapperRegistry类型
        Field registryField = ReflectionUtils.findField(Configuration.class, "mapperRegistry", MapperRegistry.class);
        ReflectionUtils.makeAccessible(registryField);
        ReflectionUtils.setField(registryField, this, new MulitMapperRegistry(this)); //将字段替换为我们自定义的registry
    }

}
