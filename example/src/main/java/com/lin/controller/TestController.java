package com.lin.controller;

import com.lin.annotation.Name;
import com.lin.dao.DataSourceTwoDao;
import com.lin.dao.DataSourceOneDao;
import com.lin.support.MulitDataSourceSupport;
import com.lin.server.DataSourceOneServer;
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
    DataSourceOneDao dataSourceOneDao;

    @Autowired
    DataSourceOneServer dataSourceOneServer;

    @Autowired
    DataSourceTwoDao dataSourceTwoDao;


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

    @GetMapping("/one")
    @ResponseBody
    public String dataSourceOne() {

        String value = dataSourceOneDao.test(1);
        return value;
    }


    @GetMapping("/two")
    @ResponseBody
    public String dataSourceTwo() {

        String value = dataSourceTwoDao.test(1);
        return value;
    }


    @Name(name = "dataSourceTwo")
    @GetMapping("/dao")
    @ResponseBody
    public String testDao() {
        String value = dataSourceOneDao.testDataSource(1);
        return value;
    }
}
