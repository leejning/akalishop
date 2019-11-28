package com.akali.business.user.web;

import com.akali.business.user.feign_client.MqMessageSendFeign;
import com.akali.common.dto.EmailContextDTO;
import com.akali.common.model.response.ResponseResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MemberRegistryControllerTest {
    @Autowired
    MqMessageSendFeign mqMessageSendFeign;

    @Test
    public void sendEmailValidCode() {
        String code = UUID.randomUUID().toString().substring(0, 6);
        EmailContextDTO dto = new EmailContextDTO("1317714631@qq.com", code);
        ResponseResult<Void> res = mqMessageSendFeign.sendMemberRegistryEmailCode(dto);
    }
}