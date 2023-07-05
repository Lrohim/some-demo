package com.shop.seckill.service;

import com.shop.seckill.feign.SeckillGoods;

import java.util.List;

public interface SeckillService {

    List<SeckillGoods> list(String time);

    SeckillGoods one(String time,Long id);
}
