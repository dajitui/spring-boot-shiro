package com.example.demo.service;

import com.example.demo.entry.user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RedisService {
    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    RedisTemplate redisTemplate;

    public String redis(){
        stringRedisTemplate.opsForValue().set("dajitui","good");
        String s=stringRedisTemplate.opsForValue().get("dajitui");
        return s;
    }

    public user hhh(user u){
        redisTemplate.opsForValue().set("user",u);
        user u1= (user) redisTemplate.opsForValue().get("user");
        return u1;
    }
}
