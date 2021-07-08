package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @program: demo3
 * @description: web配置
 * @author: ma
 * @create: 2021-07-02 11:27
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    // 增加映射关系
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

    }
}
