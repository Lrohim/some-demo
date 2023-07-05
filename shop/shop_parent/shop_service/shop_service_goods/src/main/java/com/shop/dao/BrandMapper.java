package com.shop.dao;

import com.shop.goods.pojo.Brand;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 使用通用Mapper
 */
@Repository
public interface BrandMapper extends Mapper<Brand> {

    @Select("SELECT TB.* FROM tb_brand TB,tb_category_brand TCB WHERE TB.ID=TCB.BRAND_ID AND TCB.CATEGORY_ID=#{id}")
    List<Brand> findByCategory(@Param(value = "id") Integer id);
}
