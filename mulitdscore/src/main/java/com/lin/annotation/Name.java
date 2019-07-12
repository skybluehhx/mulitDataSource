package com.lin.annotation;

import java.lang.annotation.*;

/**
 * 使用该注解可指定可连接的数据库bean名称
 * 该注解遵守就近原则
 *
 * @author jianglinzou
 * @date 2019/7/10 下午2:56
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Name {

    String name(); //指定数据库bean名

}
