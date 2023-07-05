package com.shop.mq;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class MQConfig {

    @Autowired
    private Environment environment;

    @Bean
    public Queue orderQueue(){
        return new Queue(environment.getProperty("mq.pay.queue.order"));
    }

    @Bean
    public Exchange orderExchange(){
        return new DirectExchange(environment.getProperty("mq.pay.exchange.order"),true,false);
    }

    @Bean
    public Binding orderQueueExchange(Queue orderQueue,Exchange orderExchange){
        return BindingBuilder.bind(orderQueue).to(orderExchange).with(environment.getProperty("mq.pay.routing.key")).noargs();
    }
}
