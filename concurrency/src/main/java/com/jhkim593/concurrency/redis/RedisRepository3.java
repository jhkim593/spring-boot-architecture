package com.jhkim593.concurrency.redis;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class RedisRepository3 {
    private final RedisTemplate redisTemplate;
    private String couponKey = "coupon";
    private String couponLimit = "1000";
    private String couponUserKey = "user";

    @Getter
    private Map<String ,String> map = new HashMap<>();
    public void test(String userId){
        Boolean execute = (Boolean)redisTemplate.execute(countAndAddValue(), Collections.singletonList(couponKey), userId, couponLimit);
        if(execute) map.put(userId,"");

    }

    public RedisScript<Boolean> countAndAddValue() {
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
