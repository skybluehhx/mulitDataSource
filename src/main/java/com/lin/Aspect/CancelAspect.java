package com.lin.Aspect;

import com.lin.datasource.JDBCContext;
import com.lin.datasource.MulitDataSourceSupport;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author jianglinzou
 * @date 2019/7/10 下午7:54
 */

@Aspect
@Component
public class CancelAspect {

    static ThreadLocal<JDBCContext> tempContexts = new ThreadLocal();

    @Pointcut("@annotation(com.lin.annotation.Cancel)")
    public void cancel() {
    }


    @Before("cancel()")
    public void HideCurrentContext(JoinPoint joinPoint) {
        JDBCContext jdbcContext = MulitDataSourceSupport.getCurrentJDBCCurrent();
        if (Objects.nonNull(jdbcContext)) {
            tempContexts.set(jdbcContext);
            MulitDataSourceSupport.removeCurrentJDBCCurrent();
        }
    }


    @After("cancel()")
    public void resumeCurrentContext(JoinPoint joinPoint) {

        JDBCContext jdbcContext = tempContexts.get();
        if (Objects.nonNull(jdbcContext)) {
            MulitDataSourceSupport.setCurrentJDBCCurrent(jdbcContext);
        }
    }

}
