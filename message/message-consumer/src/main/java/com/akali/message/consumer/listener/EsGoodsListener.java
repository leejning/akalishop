package com.akali.message.consumer.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @ClassName EsGoodsListener
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/15 0015
 * @Version V1.0
 **/
@Component
public class EsGoodsListener {


    @RabbitListener(queues = {"queue_good_es_add"})
    public void addGoodsToEs(Long spuId){
        System.out.println("收到消息,商品id："+spuId);
    }
}
