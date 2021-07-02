package com.example.demo.exception;


import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: demo
 * @description: 异常通知
 * @author: ma
 * @create: 2021-06-29 08:53
 */

//控制器通知
//定义一个控制器的通知类，允许定义一些关于增强控制器的各类通知和限定增强哪些控制器功能等 。
@ControllerAdvice(
        // 指定拦截包的控制器
        basePackages = { "com.example.demo.controller.*" },
        // 限定被标注为@Controller或者@RestController的类才被拦截
        annotations = { Controller.class, RestController.class })
public class VoControllerAdvice {
    // 异常处理，可以定义异常类型进行拦截处理
    @ExceptionHandler(value = NotFoundException.class)
    // 以JSON表达方式响应
    @ResponseBody
    // 定义为服务器错误状态码
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> exception(HttpServletRequest request, NotFoundException ex) {
        Map<String, Object> msgMap = new HashMap<>();
        // 获取异常信息
        msgMap.put("code", ex.getCode());
        msgMap.put("message", ex.getCustomMsg());
        return msgMap;
    }
}

