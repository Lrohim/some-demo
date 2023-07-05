package com.changgou.oauth.service.impl;

import com.changgou.oauth.service.UserLoginService;
import com.changgou.oauth.util.AuthToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.Map;

@Service
public class UserLoginServiceImpl implements UserLoginService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Override
    public AuthToken login(String username, String password, String clientId, String clientSecret, String grantType) {
        ServiceInstance choose = loadBalancerClient.choose("user-auth");
        String url="http://"+choose.getUri()+"/oauth/token";
        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("Authorization","Basic "+ Base64.getEncoder().encode((clientId+":"+clientSecret).getBytes()));
        MultiValueMap<String,String> multiValueMap=new LinkedMultiValueMap();
        multiValueMap.add("username",username);
        multiValueMap.add("password",password);
        multiValueMap.add("grant_type",grantType);
        HttpEntity httpEntity=new HttpEntity(multiValueMap,httpHeaders);
        ResponseEntity<Map> exchange = restTemplate.exchange("", HttpMethod.POST, httpEntity, Map.class);
        Map<String,String> map=exchange.getBody();
        AuthToken authToken=new AuthToken();
        authToken.setAccessToken(map.get("access_token"));
        authToken.setJti(map.get("jti"));
        authToken.setRefreshToken(map.get("refresh_token"));
        return authToken;
    }
}
