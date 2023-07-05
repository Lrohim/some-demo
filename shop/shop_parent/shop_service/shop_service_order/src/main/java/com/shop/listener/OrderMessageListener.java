package com.shop.listener;

import com.alibaba.fastjson.JSON;
import com.shop.service.OrderService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Map;

@Component
@RabbitListener(queues = "${mq.pay.queue.order}")
public class OrderMessageListener {

    @Autowired
    private OrderService orderService;

    @RabbitHandler
    public void getMessage(String message) throws ParseException {
        Map<String,String> messageMap= JSON.parseObject(message,Map.class);
        String return_code=messageMap.get("return_code");
        if(return_code.equals("SUCCESS")){
            String result_code=messageMap.get("result_code");
            String out_trade_no=messageMap.get("out_trade_no");
            if(result_code.equals("SUCCESS")){
                String transaction_id=messageMap.get("transaction_id");
                orderService.updateStatus(out_trade_no,messageMap.get("time_out"),transaction_id);
            }else {
                //关闭订单
                orderService.deleteOrder(out_trade_no);
            }
        }
    }
}
