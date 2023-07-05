package com.shop.service;

import com.shop.goods.pojo.Spec;

import java.util.List;

public interface SpecService {


    /**
     * 根据模板ID查询规格集合
     */
    List<Spec> findByCategory(Integer categoryId);
}
