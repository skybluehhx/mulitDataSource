package com.lin.config;


import com.lin.Interceptor.MulitDataSourceInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author jianglinzou
 * @date 2018/12/6 下午2:14
 */
@EnableWebMvc
@ComponentScan
@Configuration
public class WebXML extends WebMvcConfigurerAdapter {




    @Autowired
    MulitDataSourceInterceptor mulitDataSourceInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(mulitDataSourceInterceptor);
        super.addInterceptors(registry);
    }


    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

}