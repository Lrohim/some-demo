package com.shop.service.serviceImpl;

import com.shop.dao.CategoryMapper;
import com.shop.dao.SpecMapper;
import com.shop.goods.pojo.Category;
import com.shop.goods.pojo.Spec;
import com.shop.service.SpecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecServiceImpl implements SpecService {

    @Autowired
    private SpecMapper specMapper;

    @Autowired
    private CategoryMapper categoryMapper;


    @Override
    public List<Spec> findByCategory(Integer categoryId) {
        //获取Template_id
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        //获取template_id查询规格集合
        Spec spec=new Spec();
        spec.setTemplateId(category.getTemplateId());
        specMapper.select(spec);
        return null;
    }
}
