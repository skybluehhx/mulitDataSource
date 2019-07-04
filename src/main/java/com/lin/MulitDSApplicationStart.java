package com.lin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author jianglinzou
 * @date 2019/7/4 下午9:37
 */
@EnableConfigurationProperties
@SpringBootApplication
public class MulitDSApplicationStart {
    public static void main(String[] args) {
        SpringApplication.run(MulitDSApplicationStart.class, args);
    }

}
