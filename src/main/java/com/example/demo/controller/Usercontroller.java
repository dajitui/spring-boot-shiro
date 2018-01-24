package com.example.demo.controller;


import com.alibaba.fastjson.JSON;
import com.example.demo.entry.PageInfo;
import com.example.demo.entry.user;
import com.example.demo.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;
import java.util.List;

@RestController
public class Usercontroller{

    @Autowired
    public UserServiceImpl userService;

    @RequestMapping(value = "/user")
    public int user(){
        user u=new user();
        u.setAge(1);
        u.setName("h");
        return userService.insert(u);
    }

    @RequestMapping(value = "/hello")
    public String hi(){
        return "hello";

    }

    @RequestMapping (value="/findAll/{No}/{Size}")
    public String find(@PathVariable(value = "No")int No,@PathVariable(value = "Size")int Size){
        List<user> list=userService.findAll(No,Size);
        System.out.println("list:"+list.toString());
        PageInfo<user> pageInfo = new PageInfo<>(list);
        System.out.println(JSON.toJSONString(pageInfo));
        Iterator<user> i=list.iterator();
        String s="";
        while(i.hasNext()){
            user u=i.next();
            s+=u.getName()+" "+u.getAge()+" ";
        }
        return s;
    }
}