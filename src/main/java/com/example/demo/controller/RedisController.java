package com.example.demo.controller;

import com.example.demo.entry.user;
import com.example.demo.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisController {
    @Autowired
    private RedisService redisService;

    @RequestMapping (value="/redis")
    public String redis(){
        return redisService.redis();
    }

    @RequestMapping(value = "/redisObject")
    public String redisObject(){
        user u=new user();
        u.setName("大鸡腿");
        u.setAge(26);
        user u1=redisService.hhh(u);
        return u1.getName()+" "+u1.getAge();
    }


}
