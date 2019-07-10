package com.lin.server;

import com.lin.annotation.Name;
import com.lin.dao.MulitSourceDao;
import org.springframework.stereotype.Service;

/**
 * @author jianglinzou
 * @date 2019/7/10 下午5:55
 */
@Service
public class TestServer {

    MulitSourceDao mulitSourceDao;

    @Name(name = "dataSourceTwo")
    public String test(int id) {
        return mulitSourceDao.test(id);
    }


}
