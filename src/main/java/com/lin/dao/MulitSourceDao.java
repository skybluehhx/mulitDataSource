package com.lin.dao;

import com.lin.annotation.Name;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author jianglinzou
 * @date 2019/7/4 下午10:22
 */
@Repository
public interface MulitSourceDao {



    @ResultType(String.class)
    @Select("select name from datasource where id =#{0}")
    public String test(int id);
}
