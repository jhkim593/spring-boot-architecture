package com.jhkim593.example.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class CouponRepository {
    private final RedisTemplate redisTemplate;
    private static final String couponKey = "coupon";
    private static final String couponLimit = "3000";

    public Boolean createCoupon(String userId){
        return (Boolean) redisTemplate.execute(getCountAndAddValue(), Collections.singletonList(couponKey), userId, couponLimit);
    }
    public Long size(){
        return redisTemplate.opsForSet().size(couponKey);
    }
    public Boolean delete(){
        return redisTemplate.delete(couponKey);
    }

    public RedisScript<Boolean> getCountAndAddValue() {
        String script =
                "local key = KEYS[1] \n" +
                "local value = ARGV[1]\n" +
                "local limit = tonumber(ARGV[2])\n" +
                "local size = redis.call('SCARD', key)\n" +
                "\n" +
                "if size < limit then\n" +
                "    local result = redis.call('SADD', key, value)\n" +
                "    if result == 1 then\n" +
                "        return 1 \n" +
                "    else\n" +
                "        return 0\n" +
                "    end\n" +
                "else\n" +
                "    return 0\n" +
                "end\n";
        return RedisScript.of(script, Boolean.class);
    }
}
