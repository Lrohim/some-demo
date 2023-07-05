package com.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@EnableFeignClients(basePackages = {"com.shop.goods.feign"})
@EnableElasticsearchRepositories(basePackages = {"com.shop.search.dao"})
@EnableEurekaClient
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class SearchApplication {
    public static void main(String[] args) {
        System.setProperty("es.set.netty.runtime.available.processors","false");
        SpringApplication.run(SearchApplication.class,args);
    }
}
