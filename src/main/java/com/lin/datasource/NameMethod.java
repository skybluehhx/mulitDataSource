package com.lin.datasource;

import com.lin.annotation.Name;
import lombok.Data;

import java.lang.reflect.Method;

/**
 * 对带有@name注解方法的简单封装
 *
 * @author jianglinzou
 * @date 2019/7/10 下午4:05
 */
@Data
public class NameMethod {

    public NameMethod(Method method, Name name) {
        this.method = method;
        this.name = name;
    }

    private Method method;

    private Name name;


}
