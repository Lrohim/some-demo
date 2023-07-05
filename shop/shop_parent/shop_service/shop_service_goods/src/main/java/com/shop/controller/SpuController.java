package com.shop.controller;

import com.shop.goods.pojo.Goods;
import com.shop.goods.pojo.Spu;
import com.shop.service.SpuService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(value = "/spu")
public class SpuController {

    @Autowired
    private SpuService spuService;

    @GetMapping(value = "/goods/{id}")
    public Result<Goods> findGoodsById(@PathVariable(value = "id")Integer id){
        Goods goodsById = spuService.findGoodsById(id);
        return new Result<>(true, StatusCode.OK,"查询商品成功",goodsById);
    }

    @GetMapping(value = {"/{id}"})
    public Result<Spu> findById(@PathVariable String id){
       Spu spu=spuService.findById(id);
       return new Result<>(true,StatusCode.OK,"查询成功",spu);
    }

    @PutMapping(value = "/audit/{id}")
    public Result audit(@PathVariable(value = "id")Integer id){
        spuService.audit(id);
        return new Result(true,StatusCode.OK,"审核成功");
    }

    @PutMapping(value = "/pull/{id}")
    public Result pull(@PathVariable(value = "id")Integer id){
        spuService.pull(id);
        return new Result(true,StatusCode.OK,"下架成功");
    }

    @PutMapping(value = "/put/{id}")
    public Result put(@PathVariable(value = "id")Integer id){
        spuService.put(id);
        return new Result(true,StatusCode.OK,"下架成功");
    }

    @PutMapping(value = "/put/putMany")
    public Result putMany(@RequestBody Integer[] ids){
        spuService.putMany(ids);
        return new Result(true,StatusCode.OK,"下架成功");
    }
}
