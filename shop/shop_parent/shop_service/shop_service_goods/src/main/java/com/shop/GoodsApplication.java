package com.shop;

import entity.FeignInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableEurekaClient
@EnableSwagger2
@MapperScan(basePackages = {"com.shop.dao"}) //开启Mapper解析扫描
public class GoodsApplication {
    public static void main(String[] args) {
        SpringApplication.run(GoodsApplication.class,args);
    }

    @Bean
    public FeignInterceptor feignInterceptor(){
        return new FeignInterceptor();
    }
}
