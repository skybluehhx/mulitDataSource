package com.lin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author jianglinzou
 * @date 2019/7/12 下午5:08
 */
@EnableConfigurationProperties
@SpringBootApplication
public class ExampleApplicationStart {

    public static void main(String[] args) {
        SpringApplication.run(ExampleApplicationStart.class, args);
    }

}
