package com.example.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @program: demo3
 * @description: 登录成功逻辑
 * @author: ma
 * @create: 2021-07-05 10:14
 */
@Component("myLoginSuccessHandler")
public class MyLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");

        logger.info("登录成功");
        //这里写你登录成功后的逻辑
//        String data = "登录成功";
//        OutputStream outputStream = response.getOutputStream();// 获取输出流
//        // 通过设置响应头控制浏览器以UTF-8的编码显示数据，如果不加这句话，那么浏览器显示的将是乱码
//        response.setHeader("content-type", "text/html;charset=UTF-8");
//        // 将字符转换成字节数组，指定以UTF-8编码进行转换
//        byte[] dataByteArr = data.getBytes("UTF-8");
//        //使用OutputStream流向客户端输出字节数组
//        outputStream.write(dataByteArr);
//        outputStream.flush();
//        outputStream.close();
        String  redirectUrl = "index"; //缺省的登陆成功页面
        SavedRequest savedRequest = (SavedRequest) request.getSession().getAttribute("SPRING_SECURITY_SAVED_REQUEST");
        if(savedRequest != null) {
            redirectUrl =   savedRequest.getRedirectUrl();
            request.getSession().removeAttribute("SPRING_SECURITY_SAVED_REQUEST");
        }
        response.sendRedirect(redirectUrl);
    }
}
