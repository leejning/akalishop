package com.akali.message.consumer.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName EsGoodsMqConsumerconfig
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/15 0015
 * @Version V1.0
 **/
@Configuration
public class EsGoodsMqConsumerConfig {
    public static final String QUEUE_GOODS_ES_PUB = "queue_good_es_add";
    public static final String EXCHANGE_GOODS="exchange_goods";

    public String routingKey = "goods.publish";


    /**
     * 商品消息交换机
     * @return
     */
    @Bean(EXCHANGE_GOODS)
    public Exchange goodsExchangeTopic(){
        return ExchangeBuilder.directExchange(EXCHANGE_GOODS).durable(true).build();
    }

    /**
     * elaticsearch，消费商品消息交换机信息的队列
     * @return
     */
    @Bean(QUEUE_GOODS_ES_PUB)
    public Queue goodsAddQueue(){
        return new Queue(QUEUE_GOODS_ES_PUB);
    }

    /**
     * 绑定队列与交换机
     * @param queue
     * @param exchange
     * @return
     */
    @Bean
    public Binding bindingQueueAndExchange(@Qualifier(QUEUE_GOODS_ES_PUB) Queue queue,
                                           @Qualifier(EXCHANGE_GOODS) Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(routingKey).noargs();
    }
}
