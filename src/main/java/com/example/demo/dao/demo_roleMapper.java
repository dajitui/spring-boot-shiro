package com.example.demo.dao;

import com.example.demo.entry.demo_role;

public interface demo_roleMapper {
    int insert(demo_role record);

    int insertSelective(demo_role record);
}