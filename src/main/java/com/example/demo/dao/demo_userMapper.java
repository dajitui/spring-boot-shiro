package com.example.demo.dao;

import com.example.demo.entry.demo_power;
import com.example.demo.entry.demo_user;

public interface demo_userMapper {
    int insert(demo_user record);

    int insertSelective(demo_user record);

    demo_power selectPowerByName(String name);

    demo_user selectByName(String name);
}