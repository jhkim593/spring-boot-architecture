package com.jhkim593.concurrency;

import com.jhkim593.concurrency.redis.RedisRepository3;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Map;
import java.util.concurrent.*;

@SpringBootTest
public class RedisTest {
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    RedisRepository3 redisRepository;

    @Test
    public void test() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(1000);
        CountDownLatch count = new CountDownLatch(300000);
        for (int i = 0; i < 300000; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        int randomNum = ThreadLocalRandom.current().nextInt(300000001, 300002001);
                        redisRepository.test(String.valueOf(randomNum));
                    } finally {
                        count.countDown();
                    }
                }
            });
        }
        count.await();
        Map<String, String> map = redisRepository.getMap();
    }
}
