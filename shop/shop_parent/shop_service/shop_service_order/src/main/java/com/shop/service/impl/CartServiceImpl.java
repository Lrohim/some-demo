package com.shop.service.impl;

import com.shop.dao.CartMapper;
import com.shop.goods.feign.SkuFeign;
import com.shop.goods.feign.SpuFeign;
import com.shop.goods.pojo.Sku;
import com.shop.goods.pojo.Spu;
import com.shop.order.pojo.OrderItem;
import com.shop.service.CartService;
import entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SkuFeign skuFeign;

    @Autowired
    private SpuFeign spuFeign;

    /**
     *
     * @param num
     * @param id
     */
    @Override
    public void add(Integer num, String id,String username) {
        if(num<=0){
            redisTemplate.boundHashOps("Cart_"+username).delete(id);
            Long size = redisTemplate.boundHashOps("Cart_" + username).size();
            if(size==null || size<=0){
                redisTemplate.delete("Cart_" + username);
            }
            return;
        }
        Result<Sku> skuResult = skuFeign.findById(id);
        Result<Spu> spuResult = spuFeign.findById(id);
        Sku sku = skuResult.getData();
        Spu spu = spuResult.getData();
        OrderItem orderItem = createOrderItem(num, id, sku, spu);

        redisTemplate.boundHashOps("Cart"+username).put(id,orderItem);
    }

    @Override
    public List<OrderItem> findAllCart(String username) {
        List<OrderItem> allCart=redisTemplate.boundHashOps("Cart_"+username).values();
        return allCart;
    }

    private OrderItem createOrderItem(Integer num, String id, Sku sku, Spu spu) {
        OrderItem orderItem=new OrderItem();
        orderItem.setCategoryId1(spu.getCategory1Id());
        orderItem.setCategoryId2(spu.getCategory2Id());
        orderItem.setCategoryId3(spu.getCategory3Id());
        orderItem.setSkuId(id);
        orderItem.setName(sku.getName());
        orderItem.setNum(num);
        orderItem.setPrice(sku.getPrice());
        orderItem.setMoney(num*orderItem.getPrice());
        orderItem.setImage(sku.getImage());
        return orderItem;
    }
}
