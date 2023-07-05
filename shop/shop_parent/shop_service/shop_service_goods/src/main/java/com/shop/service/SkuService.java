package com.shop.service;

import com.shop.goods.pojo.Sku;

import java.util.List;
import java.util.Map;

public interface SkuService {

    List<Sku> findAll();

    Sku findById(String id);

    void decrCount(Map<String, Integer> decrMap);
}
