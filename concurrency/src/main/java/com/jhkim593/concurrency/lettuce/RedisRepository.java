package com.jhkim593.concurrency.lettuce;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

@RequiredArgsConstructor
@Repository
public class RedisRepository {

    private final RedisTemplate<String, String> redisTemplate;
    private final StringRedisTemplate stringRedisTemplate;

    @Transactional
    public void lock(Object key) {
        redisTemplate.opsForValue().increment("foo");
        Long key1 = redisTemplate.opsForSet().size("key1");
        String result = redisTemplate.opsForValue().get("foo");
        String result1 = redisTemplate.opsForValue().get("key");

//        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
//        valueOperations.set("key", "value");
    }

    public Boolean unlock(Object key) {
        return redisTemplate.delete(key.toString());
    }
}
