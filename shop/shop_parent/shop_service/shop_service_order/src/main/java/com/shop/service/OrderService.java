package com.shop.service;

import com.shop.order.pojo.Order;

import java.text.ParseException;

public interface OrderService {

    boolean addOrder(Order order);

    void updateStatus(String outtradeno,String paytime,String transactionid) throws ParseException;

    void deleteOrder(String outtradeno);
}
