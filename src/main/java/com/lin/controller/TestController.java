package com.lin.controller;

import com.lin.annotation.Name;
import com.lin.dao.MulitSourceDao;
import com.lin.datasource.MulitDataSourceSupport;
import com.lin.datasource.MultiDataSource;
import com.lin.server.MulitSourceServer;
import org.aspectj.lang.annotation.AfterReturning;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */

@Controller
@RequestMapping("/")
public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    MulitSourceDao mulitSourceDao;

    @Autowired
    MulitSourceServer mulitSourceServer;


    @Name(name = "dataSourceOne", extend = false)
    @GetMapping("/test")
    @ResponseBody
    public String testDataSource() {
        mulitSourceServer.test(1);
        String name = MulitDataSourceSupport.getDataSourceName();
        String value = mulitSourceDao.test(1);
        return value;
    }

    @GetMapping("/haha")
    @ResponseBody
    public String hahaha() {
        return "hahaha!";
    }
}
