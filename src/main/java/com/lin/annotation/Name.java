package com.lin.annotation;

import org.springframework.web.bind.annotation.Mapping;

import java.lang.annotation.*;

/**
 * @author jianglinzou
 * @date 2019/7/10 下午2:56
 */


@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Name {


    String name();

    int order() default 0;
}
