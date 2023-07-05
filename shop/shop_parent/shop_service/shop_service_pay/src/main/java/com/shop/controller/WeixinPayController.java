package com.shop.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.wxpay.sdk.WXPayUtil;
import com.shop.service.WeixinPayService;
import entity.Result;
import entity.StatusCode;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

@RequestMapping("/weixin/pay")
@RestController
public class WeixinPayController {


    @Autowired
    private WeixinPayService weixinPayService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping(value = "/create/native")
    public Result<Map> createNative(@RequestParam Map<String,String> paramMap){
        Map map = weixinPayService.createNative(paramMap);
        return new Result<>(true, StatusCode.OK,"创建成功",map);
    }

    @GetMapping(value = "/status/query")
    public Result queryStatus(String outtradeno){
        Map map = weixinPayService.queryStatus(outtradeno);
        return new Result<>(true, StatusCode.OK,"查询成功",map);
    }

    @PostMapping(value = "/notify/url")
    public String notifyUrl(HttpServletRequest httpServletRequest) throws Exception {
        ServletInputStream inputStream = httpServletRequest.getInputStream();
        ByteArrayOutputStream os=new ByteArrayOutputStream();
        byte[] bytes=new byte[1024];
        int len = 0;
        while ((len=inputStream.read(bytes))!=-1){
            os.write(bytes,0,len);
        }
        byte[] a = os.toByteArray();
        String xmlResult=new String(a,"UTF-8");
        Map<String, String> map = WXPayUtil.xmlToMap(xmlResult);
        Map<String,String> mq = (Map<String, String>) JSON.parse(map.get("attach"));
        rabbitTemplate.convertAndSend(mq.get("exchange"),mq.get("routingKey"), JSON.toJSONString(map));
        String result="<xml></xml>";
        return result;
    }
}
