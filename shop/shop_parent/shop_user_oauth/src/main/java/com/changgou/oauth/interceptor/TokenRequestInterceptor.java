package com.changgou.oauth.interceptor;

import com.changgou.oauth.util.AdminToken;
import feign.RequestInterceptor;
import feign.RequestTemplate;

public class TokenRequestInterceptor implements RequestInterceptor {


    @Override
    public void apply(RequestTemplate requestTemplate) {
        String jwt = AdminToken.createJWT();
        requestTemplate.header("Authorization","bearer "+jwt);
    }
}
