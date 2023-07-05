package com.shop.service;

import com.shop.order.pojo.OrderItem;

import java.util.List;

public interface CartService {

    void add(Integer num, String id,String username);

    List<OrderItem> findAllCart(String username);
}
