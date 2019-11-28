package com.akali.business.admin.permission.api;

import com.akali.common.dto.admin.PermissionDTO;
import com.akali.common.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @ClassName PermissionControllerApi
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/26 0026
 * @Version V1.0
 **/
@Api(value = "权限crud接口")
public interface PermissionControllerApi {

    @ApiOperation("添加权限")
    public ResponseResult<Void> createPermission(PermissionDTO permissionDTO);
}
