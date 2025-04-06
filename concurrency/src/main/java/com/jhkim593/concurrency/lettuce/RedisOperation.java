package com.jhkim593.concurrency.lettuce;

import org.springframework.data.redis.core.RedisOperations;

import java.time.Duration;

public interface RedisOperation {

    Long count(RedisOperations<String, Object> operations, String key);

    Long add(RedisOperations<String, Object> operations, String key, String value);

//    Boolean expire(RedisOperations<String, Object> operations, String t, Duration duration);

    void execute(RedisOperations<String, Object> operations, String key, String value);
}
