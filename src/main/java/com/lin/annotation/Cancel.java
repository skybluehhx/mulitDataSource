package com.lin.annotation;

import java.lang.annotation.*;

/**
 * 该注解用于将上层的@Name注解失效，配合{@link com.lin.annotation.Name}使用
 * @author jianglinzou
 * @date 2019/7/10 下午7:49
 */

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Cancel {
}
