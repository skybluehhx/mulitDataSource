package com.lin.server;

import com.lin.annotation.Name;
import com.lin.datasource.MulitDataSourceSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author jianglinzou
 * @date 2019/7/10 下午9:39
 */

@Component
public class DataSourceTwoServer {

    @Autowired
    DataSourceThreeServer dataSourceThreeServer;


    public String test(int id) {
        String name = MulitDataSourceSupport.getDataSourceName();
        System.out.println("DataSourceTwoServer===before===" + name);
        dataSourceThreeServer.test(id);
        name = MulitDataSourceSupport.getDataSourceName();
        System.out.println("DataSourceTwoServer=== after===" + name);
        return name;
    }


}
