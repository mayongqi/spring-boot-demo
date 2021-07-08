package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TestController {

    @Autowired
    private RedisTemplate redisTemplate;

    private static final Logger log = LoggerFactory.getLogger(TestController.class);

    @RequestMapping("/hello")
    public String test(){
        log.info("hello");
        return "hello";
    }

    @GetMapping("/test")
    public String getTest(@RequestParam(value = "name") List<String> names){
//        System.out.println(Arrays.toString(names));
        System.out.println(names.get(0));
        return "test";
    }


    @RequestMapping("/testRedis")
    public Map<String, Object> testPipeline(){

        Map<String, Object>  map = new HashMap<>();
        long start = System.currentTimeMillis();
        redisTemplate.executePipelined(new SessionCallback<Object>() {
            @Override
            public Object execute(RedisOperations redisOperations) throws DataAccessException {
                for (int i = 0; i < 10000; i++) {
                    redisOperations.opsForValue().set("key" + i,"value" + i);
                }
                return null;
            }
        });
        long end = System.currentTimeMillis();
        System.out.println("总耗时： " + (end-start));
        map.put("success", true);
        return map;
    }

}
