package com.shop.seckill.service;

import entity.SeckillStatus;

public interface SeckillOrderService {

    boolean addOrder(Long id,String time,String username);

    SeckillStatus checkOrderStatus(String username);
}
