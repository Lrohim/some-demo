package com.shop.service.serviceImpl;

import com.shop.dao.SkuMapper;
import com.shop.goods.pojo.Sku;
import com.shop.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SkuServiceImpl implements SkuService {

    @Autowired
    private SkuMapper skuMapper;


    @Override
    public List<Sku> findAll() {
        List<Sku> skus = skuMapper.selectAll();
        return skus;
    }

    @Override
    public Sku findById(String id) {
        Sku sku=skuMapper.selectByPrimaryKey(id);
        return sku;
    }

    @Override
    public void decrCount(Map<String, Integer> decrMap) {
        for (Map.Entry<String, Integer> entry : decrMap.entrySet()) {
            String id = entry.getKey();
            Object object=entry.getValue();
            Integer num = Integer.valueOf(object.toString());
            //利用行级锁控制
            int i = skuMapper.decrCount(id, num);
            if(i<=0){
                throw new RuntimeException();
            }
        }
    }
}
