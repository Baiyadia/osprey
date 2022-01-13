package com.kaiqi.osprey.common.redis;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

/**
 * @author wangs
 * @data 30/03/2018
 */
@Slf4j
@Configuration
public class REDIS {

    @Autowired
    private LettuceConnectionFactory lettuceConnectionFactory;

    private static LettuceConnectionFactory factory;

    @PostConstruct
    public void init() {
        factory = lettuceConnectionFactory;
    }

//    @Autowired
//    private static RedisTemplate redisTemplate;
//
//    //配置RedisTemplate
//    @Bean
//    public RedisTemplate<String, Serializable> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory){
//        RedisTemplate<String, Serializable> redisTemplate = new RedisTemplate<String, Serializable>();
//        //设置key的存储方式为字符串
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        //设置为value的存储方式为JDK二进制序列化方式，还有jackson序列化方式（Jackson2JsonRedisSerialize）
//        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
//        //注入Lettuce连接工厂
////        lettuceConnectionFactory.setShareNativeConnection(false);
//        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
//        return redisTemplate;
//    }

    public static String get(String key) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }

        byte[] resultBytes;
        RedisConnection connection = factory.getConnection();
        try {
            resultBytes = connection.get(key.getBytes());
        } finally {
            connection.close();
        }

        if (resultBytes == null) {
            return null;
        }
        return new String(resultBytes);
    }

    public static Boolean set(String key, String value) {
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(value)) {
            return false;
        }

        Boolean result;
        RedisConnection connection = factory.getConnection();
        try {
            result = connection.set(key.getBytes(), value.getBytes());
        } finally {
            connection.close();
        }
        return result;
    }

    public static Boolean set(String key, int value) {
        return set(key, String.valueOf(value));
    }

    public static Boolean setEx(String key, String value, long time) {
        Boolean result;
        RedisConnection connection = factory.getConnection();
        try {
            result = connection.setEx(key.getBytes(), time, value.getBytes());
        } finally {
            connection.close();
        }
        return result;
    }

    public static Boolean setEx(String key, int value, long time) {
        return setEx(key, String.valueOf(value), time);
    }

    public static boolean expire(String key, long seconds) {
        Boolean result;
        RedisConnection connection = factory.getConnection();
        try {
            result = connection.expire(key.getBytes(), seconds);
        } finally {
            connection.close();
        }
        return result;
    }

    public static Set<String> zRangeByScore(String key, double min, double max) {
        Set<String> result = zRangeByScore(key, new RedisZSetCommands.Range().gte(min).lte(max));
        return result;
    }

    public static boolean zAdd(String key, double score, String value) {
        Boolean result;
        RedisConnection connection = factory.getConnection();
        try {
            result = connection.zAdd(key.getBytes(), score, value.getBytes());
        } finally {
            connection.close();
        }
        return result;
    }

    public static Set<String> zRangeByScore(String key, RedisZSetCommands.Range range) {
        Set<byte[]> resultBytes;
        RedisConnection connection = factory.getConnection();
        try {
            resultBytes = connection.zRangeByScore(key.getBytes(), range);
        } finally {
            connection.close();
        }
        Set<String> result = null;
        if (resultBytes != null) {
            result = new HashSet<>();
            for (byte[] r : resultBytes) {
                result.add(new String(r));
            }
        }
        return result;
    }

    public static Long delete(String key) {
        Long result;
        RedisConnection connection = factory.getConnection();
        try {
            result = connection.del(key.getBytes());
        } finally {
            connection.close();
        }
        return result;
    }

}
