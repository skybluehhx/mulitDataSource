package com.lin.Aspect;

import com.lin.annotation.Name;
import com.lin.datasource.MulitDataSourceSupport;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
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
        doSaveJDBCContext(method);//如果有必要，保存调用上下文
    }


//    @AfterReturning("name()")
//    public void saveJDBCContext(JoinPoint joinPoint) {
//        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
//        Method method = signature.getMethod();
//        doSaveJDBCContext(method);
//    }


    @After("name()")
    public void clearDataSourceName(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        doclearDataSourceName(method);
    }

    protected void dosetDataSourceName(Method method) {
        if (method.isAnnotationPresent(Name.class)) {
            Name name = method.getAnnotation(Name.class);
            MulitDataSourceSupport.putDataSourceName(method, name);
        }
    }


    protected void doclearDataSourceName(Method method) {
        if (method.isAnnotationPresent(Name.class)) {
            Name name = method.getAnnotation(Name.class);
            if (name.extend()) { //如果name为可传递的，我们需要清除本次保存的上下文
                MulitDataSourceSupport.popContext();
            }

        }

    }

    //保存上下文
    protected void doSaveJDBCContext(Method method) {
        if (method.isAnnotationPresent(Name.class)) {
            Name name = method.getAnnotation(Name.class);
            if (name.extend()) {//如果name为可传递的，我们需要保存
                MulitDataSourceSupport.pushContext();
            }

        }
    }

}
