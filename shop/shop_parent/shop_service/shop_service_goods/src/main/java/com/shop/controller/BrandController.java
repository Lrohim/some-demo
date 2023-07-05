package com.shop.controller;

import com.github.pagehelper.PageInfo;
import com.shop.goods.pojo.Brand;
import com.shop.service.BrandService;
import entity.Result;
import entity.StatusCode;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "商品控制类")
@RestController
@RequestMapping(value = "/brand")
@CrossOrigin //跨域
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping(value = "/category/{id}")
    public Result<List<Brand>> findBrandByCategory(@PathVariable(value = "id")Integer categoryId){
        List<Brand> byCategory = brandService.findByCategory(categoryId);
        return new Result<List<Brand>>(true,StatusCode.OK,"查询品牌成功",byCategory);
    }

    @PostMapping
    public Result add(Brand brand){
        boolean add=brandService.add(brand);
        return new Result(true,StatusCode.OK,"新增商品成功");
    }

    @GetMapping
    public Result<List<Brand>> findAll(){
        List<Brand> list=brandService.findAll();
        return new Result<List<Brand>>(true, StatusCode.OK,"查询全部商品成功",list);
    }


    @GetMapping(value = "/{id}")
    public Result<Brand> findById(@PathVariable(value = "id")Integer id){
        Brand brand=brandService.findById(id);
        return new Result<Brand>(true,StatusCode.OK,"根据ID查询商品成功",brand);
    }

    @PutMapping(value = "/{id}")
    public Result update(@PathVariable(value = "id")Integer id,@RequestBody Brand brand){
        brand.setId(id);
        boolean update=brandService.update(brand);
        return new Result(true,StatusCode.OK,"修改商品成功",null);
    }

    @DeleteMapping(value = "/{id}")
    public Result delete(@PathVariable(value = "id")Integer id){
        boolean delete=brandService.deleteById(id);
        return new Result(true,StatusCode.OK,"删除商品成功",null);
    }

    @PostMapping(value = "/search")
    public Result<List<Brand>> findList(@RequestBody Brand brand){
        List<Brand> brandList=brandService.findBySelective(brand);
        return new Result<List<Brand>>(true, StatusCode.OK,"查询商品成功",brandList);
    }

    @GetMapping(value = "/search/{pageNum}/{pageSize}")
    public Result<PageInfo<Brand>> findPage(@PathVariable(value = "pageNum")Integer pageNum,@PathVariable(value = "pageSize")Integer pageSize){
        PageInfo<Brand> byPage = brandService.findByPage(pageNum, pageSize);
        return new Result<PageInfo<Brand>>(true,StatusCode.OK,"查询分页成功",byPage);
    }

    @PostMapping(value = "/search/{pageNum}/{pageSize}")
    public Result<PageInfo<Brand>> findPage(@PathVariable(value = "pageNum")Integer pageNum,@PathVariable(value = "pageSize")Integer pageSize,@RequestBody Brand brand){
        PageInfo<Brand> byPage = brandService.findByPageAndBrand(brand,pageNum, pageSize);
        return new Result<PageInfo<Brand>>(true,StatusCode.OK,"查询条件分页成功",byPage);
    }
}
