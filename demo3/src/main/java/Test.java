import com.example.demo.config.RedisConfig;
import com.example.demo.util.JedisUtil;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Test {
    public static void main(String[] args) {

//        method1();

//        method2();

//        method3();

//        method4();

//        method5();

        AtomicInteger a = new AtomicInteger(0);
        System.out.println(a.decrementAndGet());
        System.out.println(a.incrementAndGet());
    }

    private static void method5() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String ma123 = passwordEncoder.encode("ma123");

        System.out.println(ma123);
    }

    private static void method4() {
        Map map = new HashMap();
        RestTemplate restTemplate = new RestTemplate();
        String forObject = restTemplate.getForObject("http://localhost:8082/hello", String.class);
        HttpHeaders httpHeaders = new HttpHeaders();
        //请求头类型
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        //绑定请求体和头
        HttpEntity<Map> mapHttpEntity = new HttpEntity<>(map, httpHeaders);
        ResponseEntity<Map> postForEntity = restTemplate.postForEntity("http://localhost:8082/hello/post", mapHttpEntity, Map.class);
        System.out.println(postForEntity.getStatusCodeValue());
//        System.out.println(forObject);
    }

    private static void method3() {
        JedisUtil instance = JedisUtil.getInstance();
        boolean key1 = instance.exists("key1");
        System.out.println(key1);
    }

    private static void method2() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(RedisConfig.class);
        RedisTemplate bean = context.getBean(RedisTemplate.class);
        bean.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations redisOperations) throws DataAccessException {
                redisOperations.boundValueOps("c").set("789");
                redisOperations.opsForValue().set("b", "456");
                System.out.println(redisOperations.opsForValue().get("b"));
                BoundValueOperations string = redisOperations.boundValueOps("string");
                string.set("fadfa");
                string.set("dfgg");
                System.out.println(string.get());
                return null;
            }
        });
    }

    private static void method1() {
        Map<String, List<String>> map = new HashMap<>();

//      a --> g
        List<String> a = new ArrayList<>();
        a.add("b");
        a.add("c");
        a.add("d");
        a.add("e");
        a.add("f");
        map.put("a", a);


        List<String> b = new ArrayList<>();
        b.add("a");
        b.add("c");
        b.add("d");
        b.add("e");
        map.put("b", b);

        List<String> c = new ArrayList<>();
        c.add("a");
        c.add("c");
        c.add("d");
        c.add("e");
        c.add("g");
        map.put("c", c);
        if(a.contains("g")){
            return;
        }
        List<String> stringList = new LinkedList<>();
        stringList.add("a");
        String start = "a";
        String end = "g";
        method(start, end, map, stringList);
        System.out.println(stringList);
    }

    private static void method(String start, String end, Map<String, List<String>> map, List<String> stringList) {
        List<String> startlist = map.get(start);
        if(startlist.contains(end)){
            stringList.add(start);
            return;
        }
        for (String s : startlist) {
            List<String> expect = new ArrayList<>();
            expect.add(start);
            List<String> list = map.get(s);
            if(list.contains(end)){
                stringList.add(s);
                return;
            }
            for (String s1 : map.get(s)) {
                if(expect.contains(s1)){
                    continue;
                }
                List<String> list1 = map.get(s1);
                if(list1.contains(end)){
                    stringList.add(s1);
                    return;
                }
                expect.add(s1);
                method(s1, end, map, stringList);
            }
        }
    }
}
