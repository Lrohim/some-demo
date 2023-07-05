package com.shop.service;

import java.util.Map;

public interface WeixinPayService {

    Map createNative(Map<String,String> parameterMap);

    Map queryStatus(String out_trade_no);
}
