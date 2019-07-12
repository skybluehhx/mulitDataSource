package com.lin.Aspect;

import com.lin.annotation.Name;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

import static com.lin.support.MulitDataSourceSupport.*;

/**
 * @author jianglinzou
 * @date 2019/7/10 下午3:03
 */

@Aspect
@Component
public class NameAspect {

    private static Logger logger = LoggerFactory.getLogger(NameAspect.class);

    @Pointcut("@annotation(com.lin.annotation.Name)")
    public void name() {
    }


    @Around("name()")
    public Object doInvoker(ProceedingJoinPoint pjp) throws Throwable {

        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        boolean handler = isHandler(method);
        doSaveJDBCContextIfNecessary(method, handler);
        dosetDataSourceNameIfNecessary(method, handler); //执行调用时，设置上下文
        try { //执行调用
            return pjp.proceed();
        } catch (Throwable e) {
            throw e;
        } finally {
            doRemoveContextIfNecessary(method, handler); //如有必要，需要移除当前上下文
            doPopContextIfNecessary(method, handler);//如有必要，需要恢复上层上下文
        }
    }


    /**
     * 判断放方法是否满足处理的条件
     *
     * @param method
     */
    private boolean isHandler(Method method) {

        if (method.isAnnotationPresent(Name.class)) {
            return true;
        }
        return false;
    }

}
