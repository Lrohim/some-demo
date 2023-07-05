package com.shop.controller;

import com.alibaba.fastjson.JSON;
import com.netflix.client.http.HttpResponse;
import com.shop.service.UserService;
import com.shop.user.pojo.User;
import entity.JwtUtil;
import entity.Result;
import entity.StatusCode;
import entity.TokenDecode;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/login")
    public Result login(String name, String password, HttpServletResponse httpServletResponse){
        User user = userService.findById(name);
        if(BCrypt.checkpw(password,user.getPassword())){
            Map<String,Object> tokenMap=new HashMap<>();
            tokenMap.put("role","USER");
            tokenMap.put("success","SUCCESS");
            tokenMap.put("username",name);
            String sign = JwtUtil.sign(JSON.toJSONString(tokenMap));
            Cookie cookie= new Cookie("Authorization", sign);
            cookie.setDomain("localhost");
            cookie.setPath("/");
            httpServletResponse.addCookie(cookie);
            new Result(true, StatusCode.OK,"登录成功",sign);
        }
        return new Result(false, StatusCode.LOGINERROR,"账号或者密码有误");
    }

    @PreAuthorize("hasAnyRole('user','vip')")
    @GetMapping
    public Result<List<User>> findAll(){
        List<User> list=userService.findAll();
        return new Result<List<User>>(true,StatusCode.OK,"查询成功",list);
    }

    @GetMapping({"/{id}","/load/{id}"})
    public Result<User> findById(@PathVariable String id){
        User user = userService.findById(id);
        return new Result<User>(true,StatusCode.OK,"查询成功",user);
    }

    @PutMapping(value = "/point/add")
    public Result addPoints(Integer points){
        String username= TokenDecode.getUserInfo().get("username");
        userService.addPoint(username,points);
        return new Result(true,StatusCode.OK,"积分添加成功");
    }
}
