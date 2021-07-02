package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfig {

   private RedisConnectionFactory redisConnectionFactory;

   @Bean(name = "RedisConnectionFactory")
   public RedisConnectionFactory initRedisConnectionFactory() {
      if(this.redisConnectionFactory != null){
         return redisConnectionFactory;
      }
      JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
      //最大空闲数
      jedisPoolConfig.setMaxIdle(30);
      //最大连接数
      jedisPoolConfig.setMaxTotal(50);
      //最大等待毫秒数
      jedisPoolConfig.setMaxWaitMillis(2000);
      JedisConnectionFactory connectionFactory = new JedisConnectionFactory(jedisPoolConfig);
      RedisStandaloneConfiguration standaloneConfiguration = connectionFactory.getStandaloneConfiguration();
      standaloneConfiguration.setHostName("127.0.0.1");
      standaloneConfiguration.setPort(6379);
      standaloneConfiguration.setPassword("");
      this.redisConnectionFactory = connectionFactory;
      return redisConnectionFactory;
   }

   @Bean(name = "redisTemplate")
   public RedisTemplate<Object,Object> initRedisTemplate(){
      RedisTemplate<Object,Object> redisTemplate = new RedisTemplate<>();
      // RedisTemplate 会自动初始化 StringRedisSerializer，所以这里直接获取
      RedisSerializer<String> stringSerializer = redisTemplate.getStringSerializer();
      //设置字符串序列化器，这样 Spring 就会把 Redis 的 key 当作字符串处理了
      redisTemplate.setKeySerializer (stringSerializer) ;
      redisTemplate.setHashKeySerializer(stringSerializer) ;
      redisTemplate.setConnectionFactory(initRedisConnectionFactory());
      return redisTemplate;
   }

}
