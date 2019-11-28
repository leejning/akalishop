package com.akali.provider.goods.config;

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
public class RabbitMqConfig {
    /**
     * 商品消息交换机的名称
     */
    public static final String EXCHANGE_GOODS="exchange_goods";

    /**
     * 商品消息交换机
     * @return
     */
    @Bean(EXCHANGE_GOODS)
    public Exchange goodsExchangeTopic(){
        return ExchangeBuilder.topicExchange(EXCHANGE_GOODS).durable(true).build();
    }
}
