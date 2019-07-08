package com.lin.mulit.controller;

import com.lin.mulit.dao.MulitSourceDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * created by xwn @ 2018.11.26
 * 主要写一些关于架构的测试
 */

@Controller
@RequestMapping("/")
public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    MulitSourceDao mulitSourceDao;

    @GetMapping("/test")
    @ResponseBody
    public String testDataSource() {
        String value = mulitSourceDao.test(1);
        return value;
    }

    @GetMapping("/haha")
    @ResponseBody
    public String hahaha() {
        return "hahaha!";
    }
}
