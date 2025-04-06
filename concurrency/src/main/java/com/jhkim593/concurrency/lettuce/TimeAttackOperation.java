package com.jhkim593.concurrency.lettuce;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Set;

@Slf4j
@Component
public class TimeAttackOperation implements RedisOperation {
    @Override
    public Long count(RedisOperations<String, Object> operations, String key) {
        operations.opsForValue().increment("foo");
        Object foo = operations.opsForValue().get("foo");
        Set<String> keys = operations.keys("*");
        String result = (String )operations.opsForValue().get("foo");
        Long size = operations.opsForSet().size("key1");
        log.debug("[TimeAttackOperation] [count] key ::: {}, size ::: {}", key, size);
        return size;
    }

    @Override
    public Long add(RedisOperations<String, Object> operations, String key, String value) {
        Long result = operations.opsForSet().add(key, "value2");
        log.debug(
                "[TimeAttackOperation] [add] key ::: {}, value ::: {}, result ::: {}", key, value, result);
        return result;
    }

//    @Override
//    public Boolean expire(
//            RedisOperations<String, Object> operations, String key, Duration duration) {
//        Boolean result = operations.expire(key, duration);
//        log.debug(
//                "[TimeAttackOperation] [expire] key ::: {}, expire ::: {}, result ::: {}",
//                key,
//                duration,
//                result);
//        return result;
//    }

    @Override
    public void execute(RedisOperations<String, Object> operations, String key, String value) {
        this.count(operations, key);
        this.add(operations, key,value);
    }
}
