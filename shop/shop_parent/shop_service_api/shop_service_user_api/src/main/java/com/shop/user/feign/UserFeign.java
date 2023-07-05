package com.shop.user.feign;

import com.shop.user.pojo.User;
import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "/user")
@FeignClient(name = "user")
public interface UserFeign {

    @GetMapping({"/{id}","/load/{id}"})
    public Result<User> findById(@PathVariable String id);

    @PutMapping(value = "/point/add")
    public Result addPoints(Integer points);
}
