package com.changgou.oauth.controller;

import com.changgou.oauth.service.UserLoginService;
import com.changgou.oauth.util.AuthToken;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
public class UserLoginController {

    @Value("${auth.clientId}")
    private String clientId;

    @Value("${auth.clientSecret}")
    private String clientSecret;

    @Autowired
    private UserLoginService userLoginService;

    @RequestMapping(value = "/login")
    public Result login(String username,String password){
        String grant_type="password";
        AuthToken login = userLoginService.login(username, password, clientId, clientSecret, grant_type);
        if(login!=null){
            return new Result(true, StatusCode.OK,"登录成功！",login);
        }
        return new Result(false,StatusCode.LOGINERROR,"登录失败！");
    }
}
