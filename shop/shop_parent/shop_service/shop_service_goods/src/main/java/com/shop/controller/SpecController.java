package com.shop.controller;

import com.shop.goods.pojo.Spec;
import com.shop.service.SpecService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RequestMapping(value = "/spec")
@RestController
public class SpecController {

    @Autowired
    private SpecService specService;

    @GetMapping(value = "/category/{id}")
    public Result<List<Spec>> findByCategoryId(@PathVariable(value = "id") Integer id){
        List<Spec> byCategory = specService.findByCategory(id);
        return new Result<List<Spec>>(true, StatusCode.OK,"查询规格成功",byCategory);
    }

}
