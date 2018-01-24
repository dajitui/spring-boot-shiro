package com.example.demo.dao;

import com.example.demo.entry.demo_power;

public interface demo_powerMapper {
    int insert(demo_power record);

    int insertSelective(demo_power record);
}