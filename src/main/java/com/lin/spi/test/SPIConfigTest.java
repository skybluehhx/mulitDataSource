package com.lin.spi.test;

import com.lin.spi.config.SPIConfig;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author jianglinzou
 * @date 2019/7/5 下午1:21
 */
public class SPIConfigTest {


    public static void main(String[] args) {
        List<String> list = SPIConfig.getInitSetValue("SPIPath", Collections.emptyList());
        System.out.println(list.size());
    }

}
