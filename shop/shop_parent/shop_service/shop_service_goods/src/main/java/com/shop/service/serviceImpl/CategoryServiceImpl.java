package com.shop.service.serviceImpl;

import com.github.pagehelper.PageInfo;
import com.shop.dao.CategoryMapper;
import com.shop.goods.pojo.Category;
import com.shop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> findByParentId(Integer id) {
        Category category=new Category();
        category.setParentId(id);
        return categoryMapper.select(category);
    }

    @Override
    public boolean add(Category category) {
        return false;
    }

    @Override
    public List<Category> findAll() {
        return null;
    }

    @Override
    public Category findById(Integer id) {
        return null;
    }

    @Override
    public boolean update(Category category) {
        return false;
    }

    @Override
    public boolean deleteById(Integer id) {
        return false;
    }

    @Override
    public List<Category> findBySelective(Category category) {
        return null;
    }

    @Override
    public PageInfo<Category> findByPage(Integer pageNum, Integer pageSize) {
        return null;
    }

    @Override
    public PageInfo<Category> findByPageAndCategory(Category category, Integer pageNum, Integer pageSize) {
        return null;
    }
}
