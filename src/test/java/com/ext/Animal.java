package com.ext;

import java.util.HashMap;
import java.util.HashSet;

/**
 * @author jianglinzou
 * @date 2019/7/12 上午11:15
 */
public class Animal {

    protected HashSet<String> hashSet = new HashSet<>();

    public void add(String key) {
        System.out.println(hashSet.getClass());
        hashSet.add(key);
    }
}
