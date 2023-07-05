package com.shop.search.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@RequestMapping(value = "/search")
@FeignClient(name = "search")
public interface SkuFeign {

    @GetMapping
    Map search(@RequestParam(required = false) Map<String,String> searchMap);
}
