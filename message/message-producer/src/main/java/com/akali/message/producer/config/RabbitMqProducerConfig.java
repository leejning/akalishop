package com.akali.message.producer.config;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName RabbitMqProducerConfig
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/15 0015
 * @Version V1.0
 **/
@Configuration
public class RabbitMqProducerConfig {
    /**
     * 商品消息交换机的名称
     */
    public static final String EXCHANGE_GOODS_PUBLISH="exchange_goods_publish";

    /**
     * 商品消息交换机
     * @return
     */
    @Bean(EXCHANGE_GOODS_PUBLISH)
    public Exchange goodsExchangeTopic(){
        return ExchangeBuilder.directExchange(EXCHANGE_GOODS_PUBLISH).durable(true).build();
    }
}
