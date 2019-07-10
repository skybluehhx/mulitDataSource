package com.lin.server;

import com.lin.annotation.Name;
import com.lin.dao.DataSourceTwoDao;
import org.aspectj.lang.annotation.After;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author jianglinzou
 * @date 2019/7/10 下午9:39
 */
public class DataSourceTwoServer {

    @Autowired
    DataSourceTwoDao dataSourceTwoDao;

    @Name(name = "dataSourceTwo", order = 15)
    public String test(int id) {
        String value = dataSourceTwoDao.test(id);
        System.out.println("======value:" + value + " from dataSourceTwoServer=======");
        return value;
    }


}
