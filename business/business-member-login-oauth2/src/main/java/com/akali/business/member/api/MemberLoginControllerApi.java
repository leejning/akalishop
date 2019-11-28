package com.akali.business.member.api;

import com.akali.common.dto.member.MemberLoginRequestDTO;
import com.akali.common.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 * @ClassName MemberLoginControllerApi
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/27 0027
 * @Version V1.0
 **/
@Api(value = "会员登录服务接口")
public interface MemberLoginControllerApi {

    @ApiOperation("会员登录")
    public ResponseResult<Map<String,Object>> login(@RequestBody MemberLoginRequestDTO memberLoginRequestDTO);

}
