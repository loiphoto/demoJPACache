package com.example.demojpacache.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
public class RedisService {

    private final RedisTemplate redisTemplate;

    @Autowired
    public RedisService(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void setWithTTL(String key, Object value, long ttlInSeconds) {
        redisTemplate.opsForValue().set(key, value);
        redisTemplate.expire(key, ttlInSeconds, TimeUnit.SECONDS);
    }

    public Optional<Object> getValue(String key) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(key));
    }

    public void deleteKey(String key) {
        redisTemplate.delete(key);
    }
}

