package com.jhkim593.example;

import com.jhkim593.example.redis.CouponRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CouponTest {
    @Autowired
    CouponRepository couponRepository;

    @AfterEach
    public void clear(){
        couponRepository.delete();
    }

    @Test
    public void couponTest() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        CountDownLatch count = new CountDownLatch(300000);
        for (int i = 0; i < 300000; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        int randomNum = ThreadLocalRandom.current().nextInt(1, 30001);
                        couponRepository.createCoupon(String.valueOf(randomNum));
                    } finally {
                        count.countDown();
                    }
                }
            });
        }
        count.await();
        assertThat(couponRepository.size()).isEqualTo(3000);
    }
}
