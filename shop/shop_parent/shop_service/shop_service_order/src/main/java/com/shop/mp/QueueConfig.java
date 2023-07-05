package com.shop.mp;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfig {

    @Bean
    public Queue orderDelayQueue(){
        return QueueBuilder.durable("orderDelayQueue").withArgument("x-dead-letter-exchange","orderListenExchange").withArgument("x-dead-letter-routing-key","orderListenerQueue").build();
    }

    @Bean
    public Queue orderListenerQueue(){
        return new Queue("orderListenerQueue",true);
    }

    @Bean
    public Binding orderListenBinding(Queue orderListenerQueue,Exchange orderListenExchange){
        return BindingBuilder.bind(orderListenerQueue).to(orderListenExchange).with("orderListenerQueue").noargs();
    }

    @Bean
    public Exchange orderListenExchange(){
        return new DirectExchange("orderListenExchange");
    }
}
