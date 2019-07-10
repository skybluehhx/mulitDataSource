package com.lin.datasource;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * @author jianglinzou
 * @date 2019/7/10 下午3:00
 */
@Component
public class NameSupport implements BeanPostProcessor {


    @Nullable
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {



        return bean;
    }


}
