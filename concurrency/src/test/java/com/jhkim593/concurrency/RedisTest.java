package com.jhkim593.concurrency;

import com.jhkim593.concurrency.lettuce.RedisRepository;
import com.jhkim593.concurrency.lettuce.RedisTransaction;
import com.jhkim593.concurrency.lettuce.TimeAttackOperation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTest {

    @Autowired
    RedisTransaction redisTransaction;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    RedisRepository redisRepository;

    @Test
    public void test(){
//        redisRepository.lock("ee");
        redisTransaction.execute(new TimeAttackOperation(),"key1","value");

    }
//        ExecutorService executorService = Executors.newCachedThreadPool();
//        executorService.execute(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        });
//    }
}
