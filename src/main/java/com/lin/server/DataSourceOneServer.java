package com.lin.server;

import com.lin.annotation.Name;
import com.lin.dao.MulitSourceDao;
import com.lin.support.MulitDataSourceSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author jianglinzou
 * @date 2019/7/10 下午5:53
 */
@Service
public class DataSourceOneServer {

    @Autowired
    MulitSourceDao mulitSourceDao;

    @Autowired
    DataSourceTwoServer dataSourceTwoServer;


    @Name(name = "dataSourceOne")
    public String test(int id) {

        String name = MulitDataSourceSupport.getDataSourceName();
        System.out.println("DataSourceOneServer===before===" + name);
        dataSourceTwoServer.test(1);
        name = MulitDataSourceSupport.getDataSourceName();
        System.out.println("DataSourceOneServer===after===" + name);
        return "one";
    }


}
