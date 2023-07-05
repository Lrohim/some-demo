package com.shop.controller;

import com.shop.goods.pojo.Category;
import com.shop.service.CategoryService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/category")
@RestController
@CrossOrigin
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping(value = "/list/{pid}")
    public Result<Category> findByParentId(@PathVariable(value = "pid")Integer id){
        List<Category> byParentId = categoryService.findByParentId(id);
        return new Result<Category>(true, StatusCode.OK,"查询子节点成功",byParentId);
    }

}
