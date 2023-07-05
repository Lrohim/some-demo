package com.shop.goods.feign;

import com.shop.goods.pojo.Sku;
import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(value = "goods")
@RequestMapping(value = "/sku")
public interface SkuFeign {

    @GetMapping
    Result<List<Sku>> findAll();

    @GetMapping({"/{id}"})
    public Result<Sku> findById(@PathVariable String id);

    @GetMapping(value = "/decr/count")
    public Result decrCount(@RequestParam Map<String,Integer> decrMap);
}
