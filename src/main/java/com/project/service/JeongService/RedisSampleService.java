package com.project.service.JeongService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RedisSampleService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public void getRedisStringValue(String key) {
        ValueOperations<String, String> stringValueOperations = stringRedisTemplate.opsForValue();
        log.info("redis key -> {}", key);
        log.info("redis value -> {}", stringValueOperations.get(key));
        // System.out.println("Redis key : " + key);
        // System.out.println("Redis value : " + stringValueOperations.get(key));
    }

    public void setRedisStringValue(String key, String value) {		
        ValueOperations<String, String> stringValueOperations = stringRedisTemplate.opsForValue();
        stringValueOperations.set(key, value);
        log.info("redis key -> {}", key);
        log.info("redis value -> {}", stringValueOperations.get(key));
        // System.out.println("Redis key : " + key);
        // System.out.println("Redis value : " + stringValueOperations.get(key));
    }

}
