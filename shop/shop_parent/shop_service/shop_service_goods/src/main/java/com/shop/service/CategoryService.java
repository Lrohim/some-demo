package com.shop.service;

import com.github.pagehelper.PageInfo;
import com.shop.goods.pojo.Category;

import java.util.List;

public interface CategoryService {

    List<Category> findByParentId(Integer id);

    /**
     * 新增品牌
     */
    boolean add(Category category);

    /**
     * 查询所有
     * @return
     */
    List<Category> findAll();

    /**
     * 根据ID查询
     */
    Category findById(Integer id);

    /**
     * 根据ID修改商品
     */
    boolean update(Category category);

    /**
     * 根据ID删除商品
     */
    boolean deleteById(Integer id);

    /**
     * 根据条件查询
     */
    List<Category> findBySelective(Category category);

    /**
     * 分页查询
     */
    PageInfo<Category> findByPage(Integer pageNum, Integer pageSize);

    /**
     * 根据条件分页查询
     */
    PageInfo<Category> findByPageAndCategory(Category category,Integer pageNum, Integer pageSize);
}
