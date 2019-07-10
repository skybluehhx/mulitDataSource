package com.lin.datasource;

import com.lin.annotation.Name;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 保存数据库名的上下文信息
 *
 * @author jianglinzou
 * @date 2019/7/10 下午3:12
 */
public class JDBCContext {


    private Logger logger = LoggerFactory.getLogger(JDBCContext.class);

    private String dataSourceBean;//优先级最高，有该字段时，将默认使用该字段，没有将从names集合中获取

    private List<NameMethod> nameMethods;


    public JDBCContext() {
        nameMethods = new ArrayList<>();
    }

    public void setDataSourceBean(String beanName) {
        this.dataSourceBean = beanName;
    }


    public void putNameMethod(NameMethod nameMethod) {
        nameMethods.add(nameMethod);
    }


    public String getDataSourceBeanName() {
        if (!Strings.isBlank(dataSourceBean)) {
            return dataSourceBean;
        }

        List<Name> names = nameMethods.stream().map(x -> x.getName()).sorted(new Comparator<Name>() {
            @Override
            public int compare(Name o1, Name o2) {
                return o2.order() - o1.order();
            }
        }).collect(Collectors.toList());

        if (!CollectionUtils.isEmpty(names)) {
            if (names.size() > 1) {
                logger.warn("the number of name:{}  is larger than one ,the methodChain is:{} ", names.size(), getInvokerMethodChain());
            }
            Name name = names.get(0);
            logger.warn("the final name used is :{},the order is:{} ", name.name(), name.order());

            return name.name();
        }
        return null;
    }


    private String getInvokerMethodChain() {

        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;
        List<Method> methods = nameMethods.stream().map(x -> x.getMethod()).collect(Collectors.toList());

        for (Method method : methods) {
            stringBuilder.append(method.getName());
            i++;
            if (i != methods.size()) {
                stringBuilder.append("->");
            }
        }
        return stringBuilder.toString();
    }

    public void clear() {
        dataSourceBean = null;
        nameMethods = null;
    }
}
