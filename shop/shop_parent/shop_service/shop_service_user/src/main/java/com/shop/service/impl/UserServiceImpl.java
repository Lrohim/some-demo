package com.shop.service.impl;

import com.shop.dao.UserMapper;
import com.shop.service.UserService;
import com.shop.user.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public User findById(String id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<User> findAll() {
        return userMapper.selectAll();
    }

    @Override
    public void addPoint(String username, Integer points) {
        userMapper.addPoint(username,points);
    }
}
