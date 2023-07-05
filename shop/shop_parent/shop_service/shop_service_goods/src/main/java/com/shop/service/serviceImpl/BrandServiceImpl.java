package com.shop.service.serviceImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.dao.BrandMapper;
import com.shop.goods.pojo.Brand;
import com.shop.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandMapper brandMapper;

    @Override
    public List<Brand> findByCategory(Integer id) {
        List<Brand> byCategory = brandMapper.findByCategory(id);
        return byCategory;
    }

    @Override
    public boolean add(Brand brand) {
        int result=brandMapper.insertSelective(brand);
        return result==1;
    }

    @Override
    public List<Brand> findAll() {
        return brandMapper.selectAll();
    }

    @Override
    public Brand findById(Integer id) {
        Brand brand=brandMapper.selectByPrimaryKey(id);
        return brand;
    }

    @Override
    public boolean update(Brand brand) {
        int update=brandMapper.updateByPrimaryKeySelective(brand);
        return update==1;
    }

    @Override
    public boolean deleteById(Integer id) {
        int delete=brandMapper.deleteByPrimaryKey(id);
        return delete==1;
    }

    @Override
    public List<Brand> findBySelective(Brand brand) {
        Example example=createBrand(brand);
        List<Brand> brandList=brandMapper.selectByExample(example);
        return brandList;
    }

    @Override
    public PageInfo<Brand> findByPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Brand> brands=brandMapper.selectAll();
        return new PageInfo<Brand>(brands);
    }

    @Override
    public PageInfo<Brand> findByPageAndBrand(Brand brand, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Brand> list=brandMapper.selectByExample(createBrand(brand));
        return new PageInfo<Brand>(list);
    }

    private Example createBrand(Brand brand){
        Example example=new Example(Brand.class);
        Example.Criteria criteria=example.createCriteria();
        if(brand!=null){
            if(!StringUtil.isEmpty(brand.getName())){
                criteria.andLike("name","%"+brand.getName()+"%");
            }
            if(!StringUtil.isEmpty(brand.getLetter())){
                criteria.andEqualTo("letter",brand.getLetter());
            }
        }
        return example;
    }
}
