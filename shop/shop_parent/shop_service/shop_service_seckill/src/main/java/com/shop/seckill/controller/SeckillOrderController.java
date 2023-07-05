package com.shop.seckill.controller;

import com.shop.seckill.service.SeckillOrderService;
import entity.Result;
import entity.SeckillStatus;
import entity.StatusCode;
import entity.TokenDecode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seckillOrder")
public class SeckillOrderController {

    @Autowired
    private SeckillOrderService orderService;

    @PutMapping("/add")
    public Result add(Long id,String time){
        String username = TokenDecode.getUserInfo().get("username");
        orderService.addOrder(id,time,username);
        return new Result(true, StatusCode.OK,"添加成功");
    }

    @PostMapping("/checkOrderStatus")
    public Result<SeckillStatus> checkOrderStatus(){
        String username = TokenDecode.getUserInfo().get("username");
        SeckillStatus seckillStatus=orderService.checkOrderStatus(username);
        return new Result<>(true,StatusCode.OK,"查询订单状态成功");
    }
}
