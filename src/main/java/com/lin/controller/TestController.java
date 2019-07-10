package com.lin.controller;

import com.lin.annotation.Name;
import com.lin.dao.MulitSourceDao;
import com.lin.datasource.MulitDataSourceSupport;
import com.lin.server.DataSourceOneServer;
import com.lin.server.DataSourceThreeServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/")
public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    MulitSourceDao mulitSourceDao;

    @Autowired
    DataSourceOneServer dataSourceOneServer;


    @GetMapping("/test")
    @ResponseBody
    public String testDataSource() {
        String name = MulitDataSourceSupport.getDataSourceName();
        System.out.println("TestController==before===" + name);
        dataSourceOneServer.test(1);
        String name1 = MulitDataSourceSupport.getDataSourceName();
        System.out.println("TestController== after===" + name1);
//        String value = mulitSourceDao.test(1);
        return "1";
    }

    @Name(name = "dataSourceOne")
    @GetMapping("/one")
    @ResponseBody
    public String dataSourceOne() {

        String value = mulitSourceDao.test(1);
        return value;
    }


    @Name(name = "dataSourceTwo")
    @GetMapping("/two")
    @ResponseBody
    public String dataSourceTwo() {

        String value = mulitSourceDao.test(1);
        return value;
    }
}
