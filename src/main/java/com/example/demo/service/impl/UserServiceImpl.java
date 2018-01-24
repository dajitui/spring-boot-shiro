package com.example.demo.service.impl;

import com.example.demo.dao.userMapper;
import com.example.demo.entry.user;
import com.example.demo.service.UserService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "userService")
public class UserServiceImpl implements  UserService {
    @Autowired
    private userMapper userMapper;


    @Override
    public int insert(user u) {

        return userMapper.insertSelective(u);
    }

    @Override
    public List<user> findAll(int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        return userMapper.findAll();
    }
}
