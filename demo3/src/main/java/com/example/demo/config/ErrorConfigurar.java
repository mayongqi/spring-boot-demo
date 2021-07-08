package com.example.demo.config;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

/**
 * @program: demo3
 * @description: 异常页面配置
 * @author: ma
 * @create: 2021-07-05 09:26
 */
@Configuration
public class ErrorConfigurar implements ErrorPageRegistrar {

    @Override
    public void registerErrorPages(ErrorPageRegistry registry) {
        ErrorPage[] errorPages = new ErrorPage[3];
        errorPages[0] = new ErrorPage(HttpStatus.NOT_FOUND, "/404");
        errorPages[1] = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500");
        errorPages[2] = new ErrorPage(HttpStatus.FORBIDDEN, "/403");
        registry.addErrorPages(errorPages);
    }
}
