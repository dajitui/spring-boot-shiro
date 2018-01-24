package com.example.demo.dao;


import com.example.demo.entry.user;

import java.util.List;

public interface userMapper {
    int insert(user record);

    int insertSelective(user record);

    List<user> findAll();
}