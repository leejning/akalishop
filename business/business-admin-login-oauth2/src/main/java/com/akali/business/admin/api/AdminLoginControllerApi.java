package com.akali.business.admin.api;

import com.akali.common.dto.admin.AdminLoginForm;
import com.akali.common.dto.admin.AdminLoginInfoDTO;
import com.akali.common.dto.admin.AdminTokenDTO;
import com.akali.common.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName AdminLoginControllerApi
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/30 0030
 * @Version V1.0
 **/
@Api(value = "管理员登录接口")
public interface AdminLoginControllerApi {
    @ApiOperation("管理员登录")
    public ResponseResult<AdminTokenDTO> login(AdminLoginForm adminLoginForm);

    @ApiOperation("管理员退出登录")
    public ResponseResult<Void> logout(HttpServletRequest request);

    @ApiOperation("获取登录管理员的信息")
    public ResponseResult<AdminLoginInfoDTO> getAdminProfile(HttpServletRequest request);


}
