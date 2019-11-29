package com.akali.business.admin.permission.api;

import com.akali.common.dto.admin.ClientWithPermissionDTO;
import com.akali.common.dto.admin.PermissionToClientDTO;
import com.akali.common.model.response.QueryResponseResult;
import com.akali.common.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @ClassName ClientControllerApi
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/26 0026
 * @Version V1.0
 **/
@Api(value = "服务管理接口")
public interface ClientControllerApi {

    @ApiOperation("添加服务接口的权限拦截")
    public ResponseResult<Void> addPermissionToClient(PermissionToClientDTO p2cDTO);

    @ApiOperation("获取服务接口权限拦截信息")
    public ResponseResult<ClientWithPermissionDTO> getClientWithPermission(Long clientId);

}
