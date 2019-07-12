package com.ext;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.HashSet;

/**
 * @author jianglinzou
 * @date 2019/7/12 上午11:17
 */
public class People extends Animal {

    public People() {
        Field hashSetField = ReflectionUtils.findField(People.class, "hashSet", HashSet.class);
        ReflectionUtils.makeAccessible(hashSetField);
        ReflectionUtils.setField(hashSetField, this, new CustomHashSet());
    }
}
