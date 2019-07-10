package com.lin.server;

import com.lin.annotation.Name;
import com.lin.dao.MulitSourceDao;
import com.lin.datasource.MulitDataSourceSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author jianglinzou
 * @date 2019/7/10 下午5:53
 */
@Service
public class MulitSourceServer {

    @Autowired
    MulitSourceDao mulitSourceDao;


//    @Name(name = "dataSourceOne", order = 100)
    public String test(int id) {

        String name = MulitDataSourceSupport.getDataSourceName();
        return mulitSourceDao.test(id);
    }


}
