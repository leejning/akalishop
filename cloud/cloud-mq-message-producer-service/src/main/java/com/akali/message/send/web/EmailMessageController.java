package com.akali.message.send.web;

import com.akali.common.dto.EmailContextDTO;
import com.akali.common.model.response.ResponseResult;
import com.akali.message.send.api.EmailMessageControllerApi;
import com.akali.message.send.exchange.EmailExchange;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName EmailMessageController
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/25 0025
 * @Version V1.0
 **/
@RestController
@RequestMapping("message/email")
public class EmailMessageController implements EmailMessageControllerApi {
    @Autowired
    private AmqpTemplate amqpTemplate;


    @PostMapping("member/registry")
    @Override
    public ResponseResult<Void> sendMemberRegistryEmailCode(@RequestBody EmailContextDTO emailContextDTO) {
        amqpTemplate.convertAndSend(EmailExchange.EXCHANGE_MEAIL,EmailExchange.routingKey,emailContextDTO);
        return ResponseResult.SUCCESS();
    }
}
