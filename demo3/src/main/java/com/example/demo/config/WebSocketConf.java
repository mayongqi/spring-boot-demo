package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @program: demo3
 * @description: websocket配置
 * @author: ma
 * @create: 2021-07-08 08:34
 */
@Configuration
public class WebSocketConf {

    //创建服务器端点,有了这个Bean,就可以使用＠ServerEndpoint 定义一个端点服务类
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

}
