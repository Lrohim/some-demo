package com.shop.controller;

import com.shop.order.pojo.OrderItem;
import com.shop.service.CartService;
import entity.Result;
import entity.StatusCode;
import entity.TokenDecode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping(value = "/add")
    public Result add(Integer num,String id,String username){
        cartService.add(num,id,username);
        return new Result(true, StatusCode.OK,"添加购物车成功");
    }

    @GetMapping(value = "/list")
    public Result<List<OrderItem>> findAll(){
        Map<String, String> userInfo = TokenDecode.getUserInfo();
        String username=userInfo.get("username");
        List<OrderItem> allCart = cartService.findAllCart(username);
        return new Result<>(true,StatusCode.OK,"查询列表成功",allCart);
    }
}
