package com.shop.service.impl;

import com.shop.dao.OrderItemMapper;
import com.shop.dao.OrderMapper;
import com.shop.goods.feign.SkuFeign;
import com.shop.goods.pojo.Sku;
import com.shop.order.pojo.Order;
import com.shop.order.pojo.OrderItem;
import com.shop.service.OrderService;
import com.shop.user.feign.UserFeign;
import entity.IdWorker;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private SkuFeign skuFeign;

    @Autowired
    private UserFeign userFeign;

    @Autowired
    private OrderMapper orderMapper;
    
    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public boolean addOrder(Order order) {

        List<OrderItem> list = redisTemplate.boundHashOps("Cart_" + order.getUsername()).values();
        for (Long skuId : order.getSkuIds()) {
            redisTemplate.boundHashOps("Cart_" + order.getUsername()).delete(skuId);
        }
        order.setId(String.valueOf(idWorker.nextId()));
        int totalNum=0;
        int totalMoney=0;
        Map<String,Integer> decrMap=new HashMap<>();
        for(OrderItem orderItem:list){
            if(order.getSkuIds().contains(orderItem.getSkuId())){
                Sku sku=skuFeign.findById(orderItem.getSkuId()).getData();
                if(sku.getPrice()!=orderItem.getPrice()){
                    orderItem.setPrice(sku.getPrice());
                }
                totalMoney+=orderItem.getPrice();
                totalNum+=orderItem.getNum();
                orderItem.setId(String.valueOf(idWorker.nextId()));
                orderItem.setOrderId(order.getId());
                orderItem.setIsReturn("0");
                decrMap.put(orderItem.getSkuId(),orderItem.getNum());
            }
        }
        order.setTotalMoney(totalMoney);
        order.setTotalNum(totalNum);
        order.setPayMoney(totalMoney);
        order.setCreateTime(new Date());
        order.setUpdateTime(order.getCreateTime());
        order.setSourceType("1");
        order.setOrderStatus("0");
        order.setPayStatus("0");
        order.setIsDelete("0");

        int insert = orderMapper.insertSelective(order);
        for (OrderItem orderItem : list) {
            if(order.getSkuIds().contains(orderItem.getSkuId())){
                int i = orderItemMapper.insertSelective(orderItem);
            }
        }
        skuFeign.decrCount(decrMap);
        userFeign.addPoints(1);
        rabbitTemplate.convertAndSend("orderDelayQueue", (Object) order.getId(), new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setExpiration("10000");
                return null;
            }
        });
        return true;
    }

    @Override
    public void updateStatus(String outtradeno, String paytime, String transactionid) throws ParseException {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = simpleDateFormat.parse(paytime);
        Order order = orderMapper.selectByPrimaryKey(outtradeno);
        order.setPayTime(date);
        order.setPayStatus("1");
        order.setTransactionId(transactionid);
        orderMapper.updateByPrimaryKey(order);
    }

    @Override
    public void deleteOrder(String outtradeno) {
        Order order=orderMapper.selectByPrimaryKey(outtradeno);
        order.setUpdateTime(new Date());
        order.setPayStatus("2");
        orderMapper.updateByPrimaryKeySelective(order);

    }


}
