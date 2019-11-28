package com.akali.cloud.email.service.impl;

import com.akali.cloud.email.listener.EmailListener;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class EmailServiceImplTest {

    @Autowired
    AmqpTemplate amqpTemplate;

    @Test
    public void sendSimpleMail() {
        Map<String, String> msg = new HashMap<>();
        msg.put("email", "1317714631@qq.com");
        msg.put("code", "123666");
        amqpTemplate.convertAndSend(EmailListener.EXCHANGE_MEAIL,EmailListener.routingKey,msg);
    }
}