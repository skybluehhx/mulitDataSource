package com.lin.server;

import com.lin.annotation.Name;
import com.lin.support.MulitDataSourceSupport;
import org.springframework.stereotype.Service;

/**
 * @author jianglinzou
 * @date 2019/7/10 下午5:55
 */
@Service
public class DataSourceThreeServer {


    @Name(name = "dataSourceThree")
    public String test(int id) {
        String name = MulitDataSourceSupport.getDataSourceName();
        System.out.println("DataSourceThreeServer===" + name);
        return name;
    }


}
