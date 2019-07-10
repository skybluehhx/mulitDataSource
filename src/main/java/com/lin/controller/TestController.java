package com.lin.controller;

import com.lin.dao.MulitSourceDao;
import com.lin.datasource.MultiDataSource;
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
