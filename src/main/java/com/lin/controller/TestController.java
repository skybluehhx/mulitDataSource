package com.lin.controller;

import com.lin.annotation.Name;
import com.lin.dao.MulitSourceDao;
import com.lin.datasource.MulitDataSourceSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    DataSourceOneServer mulitSourceServer;


    @GetMapping("/test")
    @ResponseBody
    public String testDataSource() {
        String name = MulitDataSourceSupport.getDataSourceName();
        System.out.println("TestController==before===" + name);
        mulitSourceServer.test(1);
        String name1 = MulitDataSourceSupport.getDataSourceName();
        System.out.println("TestController== after===" + name1);
//        String value = mulitSourceDao.test(1);
        return "1";
    }

    @GetMapping("/haha")
    @ResponseBody
    public String hahaha() {
        return "hahaha!";
    }
}
