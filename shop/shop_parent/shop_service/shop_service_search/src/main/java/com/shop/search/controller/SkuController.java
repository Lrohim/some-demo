package com.shop.search.controller;


import com.shop.search.service.SkuService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "/search")
public class SkuController {

    @Autowired
    private SkuService skuService;

    @GetMapping(value = "/import")
    public Result importData(){
        skuService.importDate();
        return new Result(true, StatusCode.OK,"导入成功");
    }

    @GetMapping
    public Map search(@RequestParam(required = false) Map<String,String> searchMap){
        return skuService.search(searchMap);
    }
}
