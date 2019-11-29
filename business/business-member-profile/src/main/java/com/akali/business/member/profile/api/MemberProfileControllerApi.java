package com.akali.business.member.profile.api;

import com.akali.common.dto.member.MemberProfileDTO;
import com.akali.common.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName MemberProfileControllerApi
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/27 0027
 * @Version V1.0
 **/
@Api(value = "会员信息服务接口")
public interface MemberProfileControllerApi {

    @ApiOperation("查询会员用户详细信息")
    public ResponseResult<MemberProfileDTO> getMemberProfile(HttpServletRequest request);
}
