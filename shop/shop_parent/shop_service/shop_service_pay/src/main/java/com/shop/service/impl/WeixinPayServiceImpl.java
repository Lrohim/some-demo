package com.shop.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.wxpay.sdk.WXPayUtil;
import com.shop.service.WeixinPayService;
import entity.HttpClientUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
public class WeixinPayServiceImpl implements WeixinPayService {

    @Value("${weixin.appid}")
    private String appid;
    @Value("${weixin.partner}")
    private String partner;
    @Value("${weixin.partnerkey}")
    private String partnerkey;
    @Value("${weixin.notifyurl}")
    private String notifyurl;

    @Override
    public Map createNative(Map<String, String> parameterMap) {
        try{
            Map<String,String> param=new HashMap<>();
            param.put("appid",appid);
            param.put("mch_id",partner);
            param.put("nonce_str", WXPayUtil.generateNonceStr());
            param.put("body","Test");
            param.put("out_trade_no",parameterMap.get("outtradeno"));
            param.put("total_fee",parameterMap.get("totalfee"));
            param.put("spbill_create_ip","127.0.0.1");
            param.put("notify_url",notifyurl);
            param.put("trade_type","NATIVE");

            String exchange=parameterMap.get("exchange");
            String routingKey=parameterMap.get("routingKey");
            Map<String, String> hashMap = new HashMap<>();
            hashMap.put("exchange", exchange);
            hashMap.put("routingKey", routingKey);
            param.put("attach", JSON.toJSONString(hashMap));

            String xml=WXPayUtil.generateSignedXml(param,partnerkey);
            String postXML = HttpClientUtil.postXML("httos://api.mch.weixin.qq.com/pay/unifiedorder", xml);
            Map<String, String> toMap = WXPayUtil.xmlToMap(postXML);
            return toMap;
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public Map queryStatus(String out_trade_no) {
        try{
            Map<String,String> param=new HashMap<>();
            param.put("appid",appid);
            param.put("mch_id",partner);
            param.put("nonce_str", WXPayUtil.generateNonceStr());
            param.put("body","Test");
            param.put("out_trade_no",out_trade_no);
            param.put("notify_url",notifyurl);
            String xml=WXPayUtil.generateSignedXml(param,partnerkey);
            String postXML = HttpClientUtil.postXML("httos://api.mch.weixin.qq.com/pay/orderquery", xml);
            Map<String, String> toMap = WXPayUtil.xmlToMap(postXML);
            return toMap;
        }catch (Exception e){
            return null;
        }
    }
}
