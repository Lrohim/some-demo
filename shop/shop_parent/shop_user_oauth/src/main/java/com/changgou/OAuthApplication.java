package com.changgou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import tk.mybatis.spring.annotation.MapperScan;

import java.nio.charset.StandardCharsets;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(basePackages = "com.changgou.auth.dao")
@EnableFeignClients(basePackages = {"com.shop.user.feign"})
public class OAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(OAuthApplication.class,args);
    }

    @Bean
    public RestTemplate restTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        return restTemplate;
    }
}