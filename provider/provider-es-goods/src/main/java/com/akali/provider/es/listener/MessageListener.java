package com.akali.provider.es.listener;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @ClassName MessageListener
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/24 0024
 * @Version V1.0
 **/

@Component
public class MessageListener {
    public static final String QUEUE_GOODS_ES_PUB = "goods_publish";
    public static final String EXCHANGE_GOODS="exchange_goods";
    public static final String routingKey = "goods.publish";

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = QUEUE_GOODS_ES_PUB, durable = "true"),
            exchange = @Exchange(value = EXCHANGE_GOODS,
                    type = ExchangeTypes.TOPIC),
            key = {routingKey}
    ))
    public void listenProductPublish(Long spuId){
        if (spuId == null) {
            return;
        }
        System.out.println("收到消息,商品id："+spuId);
    }
}
