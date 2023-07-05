package com.shop.service;

import com.shop.user.pojo.User;

import java.util.List;

public interface UserService {

    User findById(String id);

    List<User> findAll();

    void addPoint(String username, Integer points);
}
