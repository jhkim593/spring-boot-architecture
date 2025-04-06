package com.jhkim593.concurrency.lettuce;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RedisTransaction {
    private final StringRedisTemplate redisTemplate;
    public Object execute(RedisOperation operation, String key, String value) {

        return redisTemplate.execute(
                new SessionCallback<Object>() {
                    @Override
                    public Object execute(RedisOperations callbackOperations) throws DataAccessException {

                        // [1] REDIS 트랜잭션 Start
                        callbackOperations.multi();
                        //왜 트랜잭션내 읽기작업 안됨?
                        Object foo = callbackOperations.opsForValue().get("foo");

                        // [2] Operation 실행
                        operation.execute(callbackOperations, key,value);

                        // [3] REDIS 트랜잭션 End
                        return callbackOperations.exec();
                    }
                });
    }
}