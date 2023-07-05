package com.shop.seckill.service.impl;

import com.shop.seckill.controller.MultThreadingCreateOrder;
import com.shop.seckill.dao.SeckillMapper;
import com.shop.seckill.service.SeckillOrderService;
import entity.SeckillStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class SeckillOrderServiceImpl implements SeckillOrderService {

    @Autowired
    private SeckillMapper seckillMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private MultThreadingCreateOrder multThreadingCreateOrder;

    @Override
    public boolean addOrder(Long id, String time,String username) {
        Long userQueueCount=redisTemplate.boundHashOps("UserQueueCount").increment(username,1);
        if(userQueueCount>1){
            return false;
        }
        redisTemplate.boundHashOps("UserQueueCount").getOperations().expire(username, 35, TimeUnit.MINUTES);
        SeckillStatus seckillStatus = new SeckillStatus(username, new Date(), 1, id, time);
        redisTemplate.boundListOps("SeckillOrderQueue").leftPush(seckillStatus);
        redisTemplate.boundHashOps("UserQueueStatus").put(username,seckillStatus);
        multThreadingCreateOrder.createOrder();
        return true;
    }

    @Override
    public SeckillStatus checkOrderStatus(String username) {
        SeckillStatus userQueueStatus = (SeckillStatus) redisTemplate.boundHashOps("UserQueueStatus").get(username);
        return userQueueStatus;
    }
}
