package com.shop.seckill.controller;

import com.shop.seckill.feign.SeckillGoods;
import com.shop.seckill.service.SeckillService;
import entity.DateUtil;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RequestMapping("/seckillGoods")
@RestController
public class SeckillGoodsController {

    @Autowired
    private SeckillService seckillService;

    @RequestMapping("/one")
    public Result<SeckillGoods> one(String time,Long id){
        SeckillGoods one = seckillService.one(time, id);
        return new Result<>(true,StatusCode.OK,"查询成功",one);
    }

    @RequestMapping("/list")
    public Result<List<SeckillGoods>> list(String time){
        return new Result<>(true, StatusCode.OK,"查询成功",seckillService.list(time));
    }

    @RequestMapping("/menu")
    public Result<List<Date>> menu(){
        List<Date> dateMenus = DateUtil.getDateMenus();
        return new Result<>(true, StatusCode.OK,"查询成功",dateMenus);
    }
}
