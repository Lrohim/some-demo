package com.shop.service;

import com.shop.goods.pojo.Goods;
import com.shop.goods.pojo.Spu;

public interface SpuService {

    Spu findById(String id);

    boolean addGoods(Goods goods);

    Goods findGoodsById(Integer id);

    void audit(Integer id);

    void pull(Integer id);

    void put(Integer id);

    void putMany(Integer[] ids);
}
