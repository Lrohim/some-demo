package com.shop.seckill.dao;

import com.shop.seckill.feign.SeckillGoods;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface SeckillMapper extends Mapper<SeckillGoods> {
}
