package com.shop;


import entity.FeignInterceptor;
import entity.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import tk.mybatis.spring.annotation.MapperScan;


@EnableEurekaClient
@SpringBootApplication
@MapperScan(basePackages = {"com.shop.dao"})
@EnableFeignClients(basePackages = {"com.shop.goods.feign","com.shop.user.feign"})
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class,args);
    }

    @Bean
    public FeignInterceptor feignInterceptor(){
        return new FeignInterceptor();
    }

    @Bean
    public IdWorker idWorker(){
        return new IdWorker(0,0);
    }
}
