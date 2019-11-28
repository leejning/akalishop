package com.akali.provider.goods.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName MessageSendService
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/24 0024
 * @Version V1.0
 **/
@Component
public class MessageSendService {
    @Autowired
    private AmqpTemplate amqpTemplate;

    public void productPublishMsg(Long spuId){
        amqpTemplate.convertAndSend(RabbitMqConfig.EXCHANGE_GOODS,"goods.publish",spuId);
    }


}
