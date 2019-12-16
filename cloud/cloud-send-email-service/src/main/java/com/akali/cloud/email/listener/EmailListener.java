package com.akali.cloud.email.listener;

import com.akali.cloud.email.service.EmailService;
import com.akali.common.dto.EmailContextDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class EmailListener {
    public static final String QUEUE_EMAIL_REGISTRY_VERIFY = "email_registry_verfiy";
    public static final String EXCHANGE_MEAIL="exchange_email";
    public static final String routingKey = "email.registry.verify.code";


	@Autowired
	private EmailService emailService;

	@RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = QUEUE_EMAIL_REGISTRY_VERIFY, durable = "true"),
            exchange = @Exchange(value = EXCHANGE_MEAIL,
                    type = ExchangeTypes.TOPIC),
            key = {routingKey}))
    public void listenSms(EmailContextDTO emailContextDTO) throws Exception {
		if (emailContextDTO == null) {
            // 放弃处理
            return;
        }
        String email = emailContextDTO.getEmail();
        String code = emailContextDTO.getCode();

        if (StringUtils.isBlank(email) || StringUtils.isBlank(code)) {
            // 放弃处理
            return;
        }
        System.out.println("收到发送邮件消息："+email);
        emailService.sendSimpleMail(email,"阿卡丽商城","您的注册验证码是："+code);
	}
	

}
