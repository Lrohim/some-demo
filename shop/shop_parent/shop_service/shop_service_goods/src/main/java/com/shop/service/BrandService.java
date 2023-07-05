package com.shop.service;

import com.github.pagehelper.PageInfo;
import com.shop.goods.pojo.Brand;

import javax.sql.rowset.BaseRowSet;
import java.util.List;

public interface BrandService {

    /**
     * 根据分类ID查询品牌信息
     */
    List<Brand> findByCategory(Integer id);

    /**
     * 新增品牌
     */
    boolean add(Brand brand);

    /**
     * 查询所有
     * @return
     */
    List<Brand> findAll();

    /**
     * 根据ID查询
     */
    Brand findById(Integer id);

    /**
     * 根据ID修改商品
     */
    boolean update(Brand brand);

    /**
     * 根据ID删除商品
     */
    boolean deleteById(Integer id);

    /**
     * 根据条件查询
     */
    List<Brand> findBySelective(Brand brand);

    /**
     * 分页查询
     */
    PageInfo<Brand> findByPage(Integer pageNum, Integer pageSize);

    /**
     * 根据条件分页查询
     */
    PageInfo<Brand> findByPageAndBrand(Brand brand,Integer pageNum, Integer pageSize);
}
