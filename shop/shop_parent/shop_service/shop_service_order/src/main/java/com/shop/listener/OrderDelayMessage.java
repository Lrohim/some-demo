package com.shop.listener;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "orderListenerQueue")
public class OrderDelayMessage {

    @RabbitHandler
    public void getDelayMessage(String message){

    }
}
