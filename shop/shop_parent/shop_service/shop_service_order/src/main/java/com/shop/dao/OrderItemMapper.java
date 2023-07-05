package com.shop.dao;

import com.shop.order.pojo.OrderItem;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface OrderItemMapper extends Mapper<OrderItem> {

}
