package com.shop.seckill.controller;

import com.shop.seckill.dao.SeckillMapper;
import com.shop.seckill.feign.SeckillGoods;
import com.shop.seckill.feign.SeckillOrder;
import entity.IdWorker;
import entity.SeckillStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MultThreadingCreateOrder {

    @Autowired
    private SeckillMapper seckillMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private IdWorker idWorker;

    @Async
    @Bean
    public boolean createOrder(){
        SeckillStatus seckillStatus = (SeckillStatus) redisTemplate.boundListOps("SeckillOrderQueue").rightPop();

        if(seckillStatus==null){
            return false;
        }

        Long card= (Long) redisTemplate.boundListOps("SeckillGoodsCountList_"+seckillStatus.getGoodsId()).rightPop();
        if(card==null){
            clearUserQueue(seckillStatus.getUsername());
            return false;
        }

        Long id=seckillStatus.getGoodsId();
        String time=seckillStatus.getTime();
        String username=seckillStatus.getUsername();

        SeckillGoods seckillGoods = (SeckillGoods) redisTemplate.boundHashOps("SeckillGoods_" + time).get(id);
        if(seckillGoods==null || seckillGoods.getStockCount()<=0 ){
            return false;
        }
        SeckillOrder seckillOrder=new SeckillOrder();
        seckillOrder.setId(idWorker.nextId());
        seckillOrder.setSeckillId(id);
        seckillOrder.setMoney(seckillGoods.getPrice());
        seckillOrder.setUserId(username);
        seckillOrder.setSellerId(seckillGoods.getSellerId());
        seckillOrder.setCreateTime(new Date());
        seckillOrder.setStatus("0");

        redisTemplate.boundHashOps("SeckillOrder").put(username,seckillOrder);

        Long size = redisTemplate.boundListOps("SeckillGoodsCountList_" + seckillStatus.getGoodsId()).size();
        if(seckillGoods.getStockCount()<=0 || size<=0 ){
            seckillGoods.setStockCount(seckillGoods.getStockCount()-1);
            seckillMapper.updateByPrimaryKeySelective(seckillGoods);
            redisTemplate.boundHashOps("SeckillGoods_"+time).delete(id);
        }else {
            redisTemplate.boundHashOps("SeckillGoods_"+time).put(id,seckillGoods);
            seckillStatus.setOrderId(seckillOrder.getId());
            seckillStatus.setMoney(Float.valueOf(seckillOrder.getMoney().intValue()));
            seckillStatus.setStatus(2);
            redisTemplate.boundHashOps("UserQueueStatus").put(username,seckillStatus);
        }

        return true;
    }

    private void clearUserQueue(String username){
        redisTemplate.boundHashOps("UserQueueCount").delete(username);
        redisTemplate.boundHashOps("UserQueueStatus").delete(username);
    }
}
