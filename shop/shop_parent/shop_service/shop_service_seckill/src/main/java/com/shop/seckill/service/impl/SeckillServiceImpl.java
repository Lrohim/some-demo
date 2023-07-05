package com.shop.seckill.service.impl;

import com.shop.seckill.feign.SeckillGoods;
import com.shop.seckill.service.SeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeckillServiceImpl implements SeckillService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<SeckillGoods> list(String time) {
        List<SeckillGoods> values = redisTemplate.boundHashOps("SeckillGoods_" + time).values();
        return values;
    }

    @Override
    public SeckillGoods one(String time, Long id) {
        SeckillGoods seckillGoods = (SeckillGoods) redisTemplate.boundHashOps("SeckillGoods_" + time).get(id);
        return seckillGoods;
    }
}
