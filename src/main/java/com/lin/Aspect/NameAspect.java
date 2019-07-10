package com.lin.Aspect;

import com.lin.annotation.Name;
import com.lin.datasource.MulitDataSourceSupport;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.support.AopUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author jianglinzou
 * @date 2019/7/10 下午3:03
 */

@Aspect
@Component
public class NameAspect {

    @Pointcut("@annotation(com.lin.annotation.Name)")
    public void name() {
    }


    @Before("name()")
    public void setDataSourceName(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        dosetDataSourceName(method);
    }


    protected void dosetDataSourceName(Method method) {
        if (method.isAnnotationPresent(Name.class)) {
            Name name = method.getAnnotation(Name.class);
            MulitDataSourceSupport.putDataSourceName(method, name);
        }

//        AopUtils.isAopProxy()
    }

}
