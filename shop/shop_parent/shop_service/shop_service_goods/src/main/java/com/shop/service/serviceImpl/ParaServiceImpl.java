package com.shop.service.serviceImpl;

import com.shop.dao.CategoryMapper;
import com.shop.dao.ParaMapper;
import com.shop.goods.pojo.Category;
import com.shop.goods.pojo.Para;
import com.shop.service.ParaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParaServiceImpl implements ParaService {

    @Autowired
    private ParaMapper paraMapper;
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<Para> findByCategory(Integer id) {
        Category category = categoryMapper.selectByPrimaryKey(id);
        Para para = new Para();
        para.setTemplateId(category.getTemplateId());
        List<Para> select = paraMapper.select(para);
        return select;
    }
}
