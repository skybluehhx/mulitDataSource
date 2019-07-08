package com.lin.mulit.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * @author jianglinzou
 * @date 2019/7/2 下午7:16
 */
public class MulitDataSourceSupport {


    private static ThreadLocal<String> local = new ThreadLocal<>();

    private static Logger logger = LoggerFactory.getLogger(MulitDataSourceSupport.class);

    public static void putDataSourceName(HttpServletRequest request, String dsn) {
        logger.info("the request:{} put dataSourceName:{}", request.getRequestURL(), dsn);
        local.set(dsn);
    }

    public static String getDataSourceName() {
        return local.get();
    }

    public static void clear(HttpServletRequest request) {
        logger.info("the request:{} clear", request.getRequestURL());
        local.remove();
    }

}
