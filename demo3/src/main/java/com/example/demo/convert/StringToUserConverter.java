package com.example.demo.convert;

import com.example.demo.model.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author ma
 * @version 1.0.0
 * @ClassName StringToUserConverter.java
 * @Description user转换器
 * @createTime 2021年06月24日 17:01:00
 */
@Component
public class StringToUserConverter implements Converter<String, User> {

    @Override
    public User convert(String s) {
        Objects.requireNonNull(s, "参数异常");
        String[] split = s.split("-", 3);
        User user = new User();
        user.setName(split[0]);
        user.setAge(Byte.parseByte(split[1].toString()));
        user.setAddress(split[2]);
        return user;
    }
}
