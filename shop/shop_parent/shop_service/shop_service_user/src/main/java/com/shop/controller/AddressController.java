package com.shop.controller;


import com.github.pagehelper.PageInfo;
import com.shop.service.AddressService;
import com.shop.user.pojo.Address;
import entity.Result;
import entity.StatusCode;
import entity.TokenDecode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping(value = "/user/list")
    public Result<List<Address>> list(){
        String username= TokenDecode.getUserInfo().get("username");
        List<Address> list = addressService.list(username);
        return new Result<List<Address>>(true, StatusCode.OK,"查询成功",list);
    }
}
