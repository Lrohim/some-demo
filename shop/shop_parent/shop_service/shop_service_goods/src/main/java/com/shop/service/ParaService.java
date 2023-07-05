package com.shop.service;

import com.shop.goods.pojo.Para;

import java.util.List;

public interface ParaService {

    List<Para> findByCategory(Integer id);
}
