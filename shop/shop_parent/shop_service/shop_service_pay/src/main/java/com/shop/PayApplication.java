package com.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import tk.mybatis.spring.annotation.MapperScan;

@EnableEurekaClient
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class PayApplication {

    public static void main(String[] args) {
        SpringApplication.run(PayApplication.class,args);
    }
}
