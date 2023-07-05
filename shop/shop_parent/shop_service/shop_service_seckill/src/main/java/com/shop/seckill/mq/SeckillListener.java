package com.shop.seckill.mq;

import com.alibaba.fastjson.JSON;
import com.shop.seckill.service.SeckillOrderService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@RabbitListener(queues = "${mq.pay.queue.seckillorder}")
public class SeckillListener {

    @Autowired
    private SeckillOrderService seckillOrderService;

    @RabbitHandler
    public void getMessage(String message){
        Map<String,String> messageMap= JSON.parseObject(message,Map.class);
        String return_code=messageMap.get("return_code");
        if(return_code.equals("SUCCESS")){
            String result_code=messageMap.get("result_code");
            String out_trade_no=messageMap.get("out_trade_no");
            if(result_code.equals("SUCCESS")){
                String transaction_id=messageMap.get("transaction_id");
                seckillOrderService.updateStatus(out_trade_no,messageMap.get("time_out"),transaction_id);
            }else {
                //关闭订单
                seckillOrderService.deleteOrder(out_trade_no);
            }
        }
    }
}
