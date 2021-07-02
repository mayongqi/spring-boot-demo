package com.example.demo.service;

import com.example.demo.util.JedisUtil;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Set;

/**
 * @author ma
 * @version 1.0.0
 * @ClassName RedisService.java
 * @Description redis服务
 * @createTime 2021年06月22日 17:50:00
 */
@Service("redisService")
public class RedisService {

        /**
         * 添加SortSet型数据
         * @param key
         * @param value
         */
        public void addSortSet(String key, String value) {
            double score = new Date().getTime();
            JedisUtil jedisUtil = JedisUtil.getInstance();
            jedisUtil.zadd(key, value, score);
        }

        /**
         * 获取倒序的SortSet型的数据
         * @param key
         * @return
         */
        public Set<String> getDescSortSet(String key) {
            JedisUtil jedisUtil = JedisUtil.getInstance();
            return jedisUtil.zrevrange(key, 0, -1);
        }

        /**
         * 删除SortSet型数据
         * @param key
         * @param value
         */
        public void deleteSortSet(String key, String value) {
            JedisUtil jedisUtil = JedisUtil.getInstance();
            jedisUtil.zrem(key, value);
        }

        /**
         * 批量删除SortSet型数据
         * @param key
         * @param value
         */
        public void deleteSortSetBatch(String key, String[] value) {
            JedisUtil jedisUtil = JedisUtil.getInstance();
            jedisUtil.zrem(key, value);
        }

        /**
         * 范围获取倒序的SortSet型的数据
         * @param key
         * @return
         */
        public Set<String> getDescSortSetPage(String key,int start, int end) {
            JedisUtil jedisUtil = JedisUtil.getInstance();
            return jedisUtil.zrevrange(key, start, end);
        }

        /**
         * 获取SortSet型的总数量
         * @param key
         * @return
         */
        public long getSortSetAllCount(String key) {
            JedisUtil jedisUtil = JedisUtil.getInstance();
            return jedisUtil.zcard(key);
        }

        /**
         * 检查KEY是否存在
         * @param key
         * @return
         */
        public boolean checkExistsKey(String key) {
            JedisUtil jedisUtil = JedisUtil.getInstance();
            return jedisUtil.exists(key);
        }

        /**
         * 重命名KEY
         * @param oldKey
         * @param newKey
         * @return
         */
        public String renameKey(String oldKey, String newKey) {
            JedisUtil jedisUtil = JedisUtil.getInstance();
            return jedisUtil.rename(oldKey, newKey);
        }

        /**
         * 删除KEY
         * @param key
         */
        public void deleteKey(String key) {
            JedisUtil jedisUtil = JedisUtil.getInstance();
            jedisUtil.del(key);
        }

        /**
         * 设置失效时间
         * @param key
         * @param seconds 失效时间，秒
         */
        public void setExpireTime(String key, int seconds) {
            JedisUtil jedisUtil = JedisUtil.getInstance();
            jedisUtil.expire(key, seconds);
        }

        /**
         * 删除失效时间
         * @param key
         */
        public void deleteExpireTime(String key) {
            JedisUtil jedisUtil = JedisUtil.getInstance();
            jedisUtil.persist(key);
        }

}
