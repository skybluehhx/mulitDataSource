package com.lin.support.ibatis;

import com.lin.annotation.Name;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.binding.MapperProxy;
import org.apache.ibatis.session.SqlSession;

import java.lang.reflect.Method;
import java.util.Map;

import static com.lin.support.MulitDataSourceSupport.*;

/**
 * @author jianglinzou
 * @date 2019/7/12 上午10:31
 */
public class MulitMapperProxy<T> extends MapperProxy<T> {


    public MulitMapperProxy(SqlSession sqlSession, Class<T> mapperInterface, Map<Method, MapperMethod> methodCache) {
        super(sqlSession, mapperInterface, methodCache);
    }

    /**
     * 重新生成代理
     *
     * @param o
     * @param method
     * @param objects
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        boolean handler = method.isAnnotationPresent(Name.class); //查看是否有name注解
        doSaveJDBCContextIfNecessary(method, handler);
        dosetDataSourceNameIfNecessary(method, handler); //执行调用时，设置上下文
        try {
            return super.invoke(o, method, objects);
        } finally {
            doRemoveContextIfNecessary(method, handler); //如有必要，需要移除当前上下文
            doPopContextIfNecessary(method, handler);//如有必要，需要恢复上层上下文
        }
    }

}
