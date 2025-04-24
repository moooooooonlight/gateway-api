package com.nhnacademy.gatewayapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class LoginFailCountService {
    private final String HASH_NAME = "LOGIN_FAIL_COUNT";
    private final Duration TTL = Duration.ofMinutes(3);
    private final RedisTemplate<String, Object> redisTemplate;


    public int increase(String id){
        int cnt = 0;

        Object o = redisTemplate.opsForValue().get(HASH_NAME + id);
        if (Objects.isNull(o)) {
            redisTemplate.opsForValue().set(HASH_NAME + id, 1,  TTL);
        }else{
            cnt = (int) o + 1;
            redisTemplate.opsForValue().set(HASH_NAME+id, cnt, TTL);
        }

        return cnt;
    }

    public void reset(String id){
        redisTemplate.delete(HASH_NAME + id);
    }


    public boolean check5count(String id){
        Object o = redisTemplate.opsForValue().get(HASH_NAME + id);
        if (Objects.isNull(o)) {
            return false;
        }

        return (int) o >= 5;
    }
}
