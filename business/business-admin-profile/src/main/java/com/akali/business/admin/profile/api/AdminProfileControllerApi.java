package com.akali.business.admin.profile.api;

import com.akali.common.dto.admin.AdminLoginInfoDTO;
import com.akali.common.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName AdminProfileControllerApi
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/12/2 0002
 * @Version V1.0
 **/
@Api(value = "管理员信息服务接口")
public interface AdminProfileControllerApi {
    @ApiOperation("获取登录管理员的信息")
    public ResponseResult<AdminLoginInfoDTO> getAdminProfile(HttpServletRequest request);

}
