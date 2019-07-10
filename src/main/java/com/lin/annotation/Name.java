package com.lin.annotation;

import org.springframework.web.bind.annotation.Mapping;

import java.lang.annotation.*;

/**
 * 使用该注解可指定可连接的数据库名
 * 注意：作用在接口上将不生效
 * @author jianglinzou
 * @date 2019/7/10 下午2:56
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Name {


    String name();

    int order() default 0; //优先级，越大代表优先级越高
}
