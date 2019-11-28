package com.akali.message.send.feign;

import com.akali.common.dto.EmailContextDTO;
import com.akali.common.model.response.ResponseResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName MqMessageSendFeignApi
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/25 0025
 * @Version V1.0
 **/
@RequestMapping("message/email")
public interface MqMessageSendFeignApi {
    @PostMapping("member/registry")
    public ResponseResult<Void> sendMemberRegistryEmailCode(@RequestBody EmailContextDTO emailContextDTO);
}
