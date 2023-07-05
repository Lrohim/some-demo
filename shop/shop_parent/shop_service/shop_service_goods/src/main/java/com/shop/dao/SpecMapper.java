package com.shop.dao;

import com.shop.goods.pojo.Spec;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface SpecMapper extends Mapper<Spec> {
}
