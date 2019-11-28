package com.akali.message.send.exchange;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName EmailExchange
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/25 0025
 * @Version V1.0
 **/
@Configuration
public class EmailExchange {
    public static final String EXCHANGE_MEAIL="exchange_email";
    public static final String routingKey = "email.registry.verify.code";
    /**
     * 邮件消息交换机
     * @return
     */
    @Bean(EXCHANGE_MEAIL)
    public Exchange goodsExchangeTopic(){
        return ExchangeBuilder.topicExchange(EXCHANGE_MEAIL).durable(true).build();
    }
}
