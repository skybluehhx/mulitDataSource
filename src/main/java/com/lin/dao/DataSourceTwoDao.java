package com.lin.dao;

import com.lin.annotation.Name;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author jianglinzou
 * @date 2019/7/10 下午9:35
 */
@Component
public interface DataSourceTwoDao {


    @Name(name = "dataSourceTwo")
    @ResultType(String.class)
    @Select("select name from datasource2 where id =#{0}")
    public String test(int id);

}
