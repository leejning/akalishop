package com.akali.business.user.feign_client;

import com.akali.message.send.feign.MqMessageSendFeignApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @ClassName MqMessageSendFeign
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/25 0025
 * @Version V1.0
 **/
@FeignClient("cloud-send-mq-message-service")
public interface MqMessageSendFeign extends MqMessageSendFeignApi {
}
