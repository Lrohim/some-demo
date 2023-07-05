package com.shop.controller;

import com.shop.goods.pojo.Sku;
import com.shop.service.SkuService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/sku")
public class SkuController {

    @Autowired
    private SkuService skuService;

    @GetMapping
    public Result<List<Sku>> findAll(){
        List<Sku> all = skuService.findAll();
        return new Result<List<Sku>>(true, StatusCode.OK,"查询全部商品详情信息成功",all);
    }

    @GetMapping({"/{id}"})
    public Result<Sku> findById(@PathVariable String id){
        Sku sku = skuService.findById(id);
        return new Result<>(true,StatusCode.OK,"查询成功",sku);
    }

    @GetMapping(value = "/decr/count")
    public Result decrCount(@RequestParam Map<String,Integer> decrMap){
        skuService.decrCount(decrMap);
        return new Result(true,StatusCode.OK,"商品库存递减成功");
    }
}
