package com.akali.business.user.api;

import com.akali.cloud.image_code.dto.ImageCodeDTO;
import com.akali.common.dto.member.MemberRegDTO;
import com.akali.common.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @ClassName MemberRegistryApi
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/16 0016
 * @Version V1.0
 **/

@Api(value = "会员用户注册接口",tags = "会员用户注册接口")
public interface MemberRegistryApi {
    @ApiOperation("会员注册")
    public ResponseResult<Void> registry(MemberRegDTO memberRegDTO);

    @ApiOperation("邮箱校验")
    public ResponseResult<Void> checkEmail(String email);

    @ApiOperation("发送邮箱验证码")
    public ResponseResult<Void> sendEmailValidCode(String email);

    @ApiOperation("验证图片验证码")
    public ResponseResult<Boolean> verifyImageCode(ImageCodeDTO imageCodeDTO);
}
