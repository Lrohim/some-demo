package com.shop.controller;

import com.shop.goods.pojo.Para;
import com.shop.service.ParaService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/para")
@CrossOrigin //跨域
public class ParaController {

    @Autowired
    private ParaService paraService;

    @GetMapping(value = "/category/{id}")
    public Result<List<Para>> findByCategoryId(@PathVariable(value = "id")Integer id){
        List<Para> byCategory = paraService.findByCategory(id);
        return new Result<List<Para>>(true, StatusCode.OK,"获取商品参数成功",byCategory);
    }
}
