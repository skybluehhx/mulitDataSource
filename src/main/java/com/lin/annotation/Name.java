package com.lin.annotation;

import java.lang.annotation.*;

/**
 * 使用该注解可指定可连接的数据库名
 * 注意：作用在接口上将不生效
 *
 *
 *  如果该注解的extend的属性为ture 调用时将会保存到上下文中,
 *  如果extend为false(不推荐使用)，
 *  请配合{@link Cancel}注解使用
 * {@link Cancel}
 * @author jianglinzou
 * @date 2019/7/10 下午2:56
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Name {


    String name();

    int order() default 0; //优先级，越大代表优先级越高



    boolean extend() default true;
}
