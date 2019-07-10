package com.lin.datasource;

import com.lin.annotation.Name;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @author jianglinzou
 * @date 2019/7/2 下午7:16
 */
public class MulitDataSourceSupport {


    private static ThreadLocal<JDBCContext> local = new ThreadLocal<JDBCContext>() {
        protected JDBCContext initialValue() {
            return new JDBCContext(); //通过initialValue方法设置默认值
        }
    };

    private static Logger logger = LoggerFactory.getLogger(MulitDataSourceSupport.class);

    public static void putDataSourceName(HttpServletRequest request, String dsn) {
        logger.info("the request:{} put dataSourceName:{}", request.getRequestURL(), dsn);
        if (!Strings.isBlank(dsn)) {
            JDBCContext jdbcContext = local.get();
            jdbcContext.setDataSourceBean(dsn);
        }
    }

    public static void putDataSourceName(Method method, Name name) {
        NameMethod nameMethod = new NameMethod(method, name);
        JDBCContext jdbcContext = local.get();
        jdbcContext.putNameMethod(nameMethod);
    }

    public static String getDataSourceName() {
        JDBCContext jdbcContext = local.get();
        return jdbcContext.getDataSourceBeanName();
    }

    public static void clear(HttpServletRequest request) {
        logger.info("the request:{} clear", request.getRequestURL());
        JDBCContext jdbcContext = local.get();
        jdbcContext.clear();
        local.remove();

    }

}
