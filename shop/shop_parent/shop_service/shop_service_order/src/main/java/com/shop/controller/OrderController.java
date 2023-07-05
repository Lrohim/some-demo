package com.shop.controller;

import com.shop.order.pojo.Order;
import com.shop.service.OrderService;
import entity.Result;
import entity.StatusCode;
import entity.TokenDecode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public Result add(@RequestBody Order order){
        order.setUsername(TokenDecode.getUserInfo().get("username"));
        boolean b = orderService.addOrder(order);
        return new Result(true, StatusCode.OK,"添加成功");
    }
}
