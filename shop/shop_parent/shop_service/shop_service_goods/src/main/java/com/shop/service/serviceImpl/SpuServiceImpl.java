package com.shop.service.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.shop.dao.BrandMapper;
import com.shop.dao.CategoryMapper;
import com.shop.dao.SkuMapper;
import com.shop.dao.SpuMapper;
import com.shop.goods.pojo.*;
import com.shop.service.SpuService;
import entity.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class SpuServiceImpl implements SpuService {

    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private BrandMapper brandMapper;

    private IdWorker idWorker=new IdWorker();

    private Date date=new Date();


    @Override
    public Spu findById(String id) {
        Spu spu = spuMapper.selectByPrimaryKey(id);
        return spu;
    }

    @Override
    public boolean addGoods(Goods goods) {
        Spu spu = goods.getSpu();
        int i = 0;

        if(spu.getId()==null){
            spu.setId(Long.toString(idWorker.nextId()));
            i = spuMapper.insertSelective(spu);
        }else {
            //修改商品数据
            spuMapper.updateByPrimaryKey(spu);
            //由于商品详情信息过多，只能直接删除
            Sku sku=new Sku();
            sku.setSpuId(spu.getId());
            skuMapper.delete(sku);
        }


        Category category = categoryMapper.selectByPrimaryKey(spu.getCategory3Id());
        Brand brand = brandMapper.selectByPrimaryKey(spu.getBrandId());

        List<Sku> skuList = goods.getSkuList();
        for(Sku sku:skuList){
            sku.setId(Long.toString(idWorker.nextId()));
            if(StringUtils.isEmpty(sku.getSpec())){
                sku.setSpec("{}");
            }
            StringBuilder skuName=new StringBuilder(spu.getName());
            Map<String,String> map=JSON.parseObject(sku.getSpec(), Map.class);
            for(Map.Entry<String,String> entry:map.entrySet()){
                skuName.append(entry.getValue());
            }
            sku.setName(skuName.toString());
            sku.setCreateTime(date);
            sku.setUpdateTime(date);
            sku.setCategoryId(spu.getCategory3Id());
            sku.setCategoryName(category.getName());
            sku.setBrandName(brand.getName());
            skuMapper.insertSelective(sku);
        }
        return i==1;
    }

    @Override
    public Goods findGoodsById(Integer id) {
        Spu spu = spuMapper.selectByPrimaryKey(id);
        Sku sku=new Sku();
        sku.setSpuId(id.toString());
        List<Sku> select = skuMapper.select(sku);
        Goods goods = new Goods();
        goods.setSpu(spu);
        goods.setSkuList(select);
        return goods;
    }

    @Override
    public void audit(Integer id) {
        Spu spu = spuMapper.selectByPrimaryKey(id);
        if(spu.getIsDelete().equalsIgnoreCase("1")){
            //商品已删除
            return;
        }
        spu.setStatus("1");
        spu.setIsMarketable("1");
        spuMapper.updateByPrimaryKey(spu);
    }

    @Override
    public void pull(Integer id) {
        Spu spu = spuMapper.selectByPrimaryKey(id);
        if(spu.getIsDelete().equalsIgnoreCase("1")){
            //商品已删除
            return;
        }
        spu.setIsMarketable("0");
        spuMapper.updateByPrimaryKeySelective(spu);
    }

    @Override
    public void put(Integer id) {
        Spu spu = spuMapper.selectByPrimaryKey(id);
        if(spu.getIsDelete().equalsIgnoreCase("1")){
            //商品已删除
            return;
        }
        if(!spu.getStatus().equalsIgnoreCase("1")){
            //未审核通过
            return;
        }
        spu.setIsMarketable("1");
        spuMapper.updateByPrimaryKeySelective(spu);
    }

    @Override
    public void putMany(Integer[] ids) {
        Example example=new Example(Spu.class);
        Example.Criteria criteria=example.createCriteria();

        criteria.andIn("id", Arrays.asList(ids));
        criteria.andEqualTo("isDelete","0");
        criteria.andEqualTo("status","1");

        Spu spu=new Spu();
        spu.setIsMarketable("1");
        spuMapper.updateByPrimaryKeySelective(spu);
    }
}
