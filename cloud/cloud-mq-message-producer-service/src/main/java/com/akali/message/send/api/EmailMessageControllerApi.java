package com.akali.message.send.api;

import com.akali.common.dto.EmailContextDTO;
import com.akali.common.model.response.QueryResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @ClassName EmailMessageControllerApi
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/25 0025
 * @Version V1.0
 **/
@Api(value = "发送邮箱相关的MQ消息接口")
public interface EmailMessageControllerApi {

    @ApiOperation("发送邮箱消息")
    public QueryResponseResult<Void> sendMemberRegistryEmailCode(EmailContextDTO emailContextDTO);
}
