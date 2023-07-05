package com.shop.filter;

import entity.JwtUtil;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class AuthorizeFilter implements GlobalFilter, Ordered {
    
    private static final String AUTHORIZE_TOKEN = "Authorization";
    
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        boolean inHeader=true;

        String uri=request.getURI().toString();
        if(!URLFilter.hasAuthorize(uri)){
            return chain.filter(exchange);
        }

        String first = request.getHeaders().getFirst(AUTHORIZE_TOKEN);
        if(StringUtils.isEmpty(first)){
            inHeader=false;
            first = request.getQueryParams().getFirst(AUTHORIZE_TOKEN);
        }
        if(StringUtils.isEmpty(first)){
            HttpCookie httpCookie = request.getCookies().getFirst(AUTHORIZE_TOKEN);
            if(httpCookie!=null){
                first = httpCookie.getValue();
            }
        }
        if(StringUtils.isEmpty(first)){
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
//        try {
//            Map<String, Object> verify = JwtUtil.verify(first);
//            if(inHeader){
//                request.mutate().header(AUTHORIZE_TOKEN,first);
//            }
//            return chain.filter(exchange);
//        } catch (Exception e) {
//            response.setStatusCode(HttpStatus.UNAUTHORIZED);
//            return response.setComplete();
//        }
        if(StringUtils.isEmpty(first)){
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }else {
            if(!inHeader){
                if(!first.startsWith("bearer ") && !first.startsWith("Bearer ")){
                    first="bearer "+first;
                }
                request.mutate().header(AUTHORIZE_TOKEN,first);
            }
        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
