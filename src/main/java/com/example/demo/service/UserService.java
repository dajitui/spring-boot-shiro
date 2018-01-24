package com.example.demo.service;

import com.example.demo.entry.user;

import java.util.List;

public interface UserService {
		public int insert(user u);

		public List<user> findAll(int pageNo, int pageSize);
}
