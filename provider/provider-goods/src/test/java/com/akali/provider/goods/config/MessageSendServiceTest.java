package com.akali.provider.goods.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MessageSendServiceTest {
    @Autowired
    public MessageSendService messageSendService;

    @Test
    public void productPublishMsg() {
        messageSendService.productPublishMsg(1L);
    }
}