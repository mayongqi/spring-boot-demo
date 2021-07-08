package com.example.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * @program: demo3
 * @description: security配置
 * @author: ma
 * @create: 2021-07-02 11:21
 */
@Configuration
public class WebSecurityConfg extends WebSecurityConfigurerAdapter {

    private Logger logger = LoggerFactory.getLogger(WebSecurityConfg.class);

    @Autowired
    private MyLoginSuccessHandler myLoginSuccessHandler; //认证成功结果处理器

    @Autowired
    private MyLoginFailureHandler myLoginFailureHandler; //认证失败结果处理器

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/500").permitAll() //这里可设置一些公共都可访问的页面
                .antMatchers("/403").permitAll()
                .antMatchers("/404").permitAll()
                .antMatchers("/user/login").permitAll()  //登录页面
                .antMatchers("/admin/index").hasRole("ADMIN")//指定权限为ADMIN才能访问
                .antMatchers("/person").hasAnyRole("ADMIN","USER")//指定多个权限都能访问
                .anyRequest() //任何其它请求
                .authenticated() //都需要身份认证
                .and()
                .formLogin() //使用表单认证方式
                .loginPage("/user/login") //默认登录页路径
                .loginProcessingUrl("/user/login")//配置默认登录入口,是登录页面中form action 的地址
                .successHandler(myLoginSuccessHandler)//使用自定义的成功结果处理器
                .failureHandler(myLoginFailureHandler)//使用自定义失败的结果处理器
                .and()
                .csrf().disable();
    }

    /**
     * 自定义认证策略
     * @return
     */
    @Autowired
    public void  configGlobal(AuthenticationManagerBuilder auth) throws Exception {
        String password = passwordEncoder().encode("ma123");

        logger.info("加密后的密码:" + password);

//        auth.inMemoryAuthentication().withUser("admin").password(password)
//                .roles("ADMIN").and();
        auth.inMemoryAuthentication().withUser("ma").password(password)
                .roles("USER").and();

    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }



}
