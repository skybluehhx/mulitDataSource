package com.lin.support;

import com.lin.annotation.Name;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * 保存数据库名的上下文信息
 *
 * @author jianglinzou
 * @date 2019/7/10 下午3:12
 */
public class JDBCContext {


    private Logger logger = LoggerFactory.getLogger(JDBCContext.class);

    private String dataSourceBean;//优先级最高，有该字段时，将默认使用该字段，没有将从names集合中获取,

    private NameMethod nameMethod;


    private boolean seal;


    public JDBCContext() {

    }

    /**
     * 该构造函数一般只在首次拦截请求的时候使用
     *
     * @param dataSourceBean
     */
    public JDBCContext(String dataSourceBean) {
        this.dataSourceBean = dataSourceBean;
        this.seal = true;
    }


    public void putNameMethod(NameMethod nameMethod) {
        if (seal) {
            throw new IllegalArgumentException("the jdbccontext is seal , you can't operate it");
        }
        this.nameMethod = nameMethod;
    }



    public String getDataSourceBeanName() {
        if (!Strings.isBlank(dataSourceBean)) {
            return dataSourceBean;
        }
        Name name = nameMethod.getName();
        if (Objects.nonNull(name)) {
            return name.name();
        }
        return null;
    }




    public void clear() {
        dataSourceBean = null;
        nameMethod = null;
    }



    public boolean getSeal() {
        return this.seal;
    }
}
