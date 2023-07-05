package com.shop.seckill;


import entity.FeignInterceptor;
import entity.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

@EnableAsync
@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
@MapperScan(basePackages = {"com.shop.seckill.dao"}) //开启Mapper解析扫描
@EnableScheduling
public class SeckillApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeckillApplication.class,args);
    }

    @Bean
    public IdWorker idWorker(){
        return new IdWorker(1,1);
    }
}
