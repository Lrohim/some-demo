package com.shop.seckill.dao;

import com.shop.seckill.feign.SeckillGoods;
import com.shop.seckill.feign.SeckillOrder;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface SeckillOrderMapper  extends Mapper<SeckillOrder> {
}
