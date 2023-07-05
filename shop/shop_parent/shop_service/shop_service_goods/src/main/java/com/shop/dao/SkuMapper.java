package com.shop.dao;

import com.shop.goods.pojo.Sku;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface SkuMapper extends Mapper<Sku> {

    @Update("update tb_sku set num=num-#{num} where id=#{id} and num>=#{num}")
    int decrCount(@Param(value = "id") String id , @Param(value = "num") Integer num);
}
