package com.shop.dao;

import com.shop.order.pojo.Order;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface OrderMapper extends Mapper<Order> {
}
