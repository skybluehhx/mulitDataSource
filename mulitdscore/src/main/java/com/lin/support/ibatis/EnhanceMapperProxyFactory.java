package com.lin.support.ibatis;

import org.apache.ibatis.binding.MapperProxy;
import org.apache.ibatis.binding.MapperProxyFactory;
import org.apache.ibatis.session.SqlSession;

import java.lang.reflect.Proxy;

/**
 * @author jianglinzou
 * @date 2019/7/12 上午10:34
 */
public class EnhanceMapperProxyFactory<T> {


    private static final String mapperInterfaceName = "mapperInterface";

    private final MapperProxyFactory<T> mapperProxyFactory;

    public EnhanceMapperProxyFactory(MapperProxyFactory mapperProxyFactory) {
        this.mapperProxyFactory = mapperProxyFactory;
    }

    public T newInstance(SqlSession sqlSession) {
        final MapperProxy<T> mapperProxy = new MulitMapperProxy<>(sqlSession, mapperProxyFactory.getMapperInterface(), mapperProxyFactory.getMethodCache());
        return newInstance(mapperProxy);
    }

    @SuppressWarnings("unchecked")
    protected T newInstance(MapperProxy<T> mapperProxy) {
        return (T) Proxy.newProxyInstance(mapperProxyFactory.getMapperInterface().getClassLoader(), new Class[]{mapperProxyFactory.getMapperInterface()}, mapperProxy);
    }

}
