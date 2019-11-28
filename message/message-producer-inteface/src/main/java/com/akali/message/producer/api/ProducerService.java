package com.akali.message.producer.api;

/**
 * @ClassName ProducerService
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/15 0015
 * @Version V1.0
 **/
public interface ProducerService {
    /**
     * 发送商品发布的消息
     * @param spuId
     */
    public void publishGoods(Long spuId);
}
