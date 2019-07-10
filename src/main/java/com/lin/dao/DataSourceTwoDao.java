package com.lin.dao;

import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

/**
 * @author jianglinzou
 * @date 2019/7/10 下午9:35
 */
public interface DataSourceTwoDao {


    @ResultType(String.class)
    @Select("select name from datasource2 where id =#{0}")
    public String test(int id);

}
