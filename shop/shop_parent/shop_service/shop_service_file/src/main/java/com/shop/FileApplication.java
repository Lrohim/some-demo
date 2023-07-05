package com.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//@EnableSwagger2
@EnableEurekaClient
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class) //排除掉数据库自动加载
public class FileApplication {
    public static void main(String[] args) {
        SpringApplication.run(FileApplication.class,args);
    }
}
