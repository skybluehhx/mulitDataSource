package com.lin.support;

import com.lin.annotation.Name;
import lombok.Data;

import java.lang.reflect.Method;
import java.util.Objects;

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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NameMethod that = (NameMethod) o;
        return Objects.equals(method, that.method) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, name);
    }
}
