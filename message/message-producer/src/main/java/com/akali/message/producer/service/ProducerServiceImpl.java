package com.akali.message.producer.service;

import com.akali.message.producer.api.ProducerService;
import com.akali.message.producer.config.RabbitMqProducerConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName ProducerServiceImpl
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/15 0015
 * @Version V1.0
 **/
//@Service(version = "1.0.0")
@Service
@Slf4j
public class ProducerServiceImpl implements ProducerService {
    @Autowired
    private AmqpTemplate amqpTemplate;

    /**
     * 发送商品发布的消息
     * @param spuId
     */
    @Override
    public void publishGoods(Long spuId) {
        amqpTemplate.convertAndSend(RabbitMqProducerConfig.EXCHANGE_GOODS_PUBLISH,"goods.publish",spuId);
        log.info("商品发布消息发送成功！");
    }
}
