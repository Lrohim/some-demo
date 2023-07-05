package com.thymeleaf.controller;

import com.thymeleaf.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@RequestMapping(value = "/test")
@Controller
public class TestController {

    @GetMapping(value = "/hello")
    public String hello(Model model){
        model.addAttribute("message","hello!");
        List<User> users=new ArrayList<>();
        users.add(new User(1,"zhangSan","Shenzhen"));
        model.addAttribute("users",users);
        return "demo1";
    }
}
