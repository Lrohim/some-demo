package com.shop.goods.feign;

import com.shop.goods.pojo.Spu;
import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "goods")
@RequestMapping(value = "/spu")
public interface SpuFeign {

    @GetMapping(value = {"/{id}"})
    public Result<Spu> findById(@PathVariable String id);
}
