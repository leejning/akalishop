package com.akali.provider.user.admin.service;

import com.akali.common.dto.admin.ClientWithPermissionDTO;
import com.akali.common.dto.admin.PermissionToClientDTO;
import com.akali.common.model.response.DubboResponse;


/**
 * @ClassName ClientService
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/26 0026
 * @Version V1.0
 **/
public interface ClientService {
    /**
     * 添加服务接口的权限拦截
     * @param p2cDTO
     * @return
     */
    DubboResponse<Void> addPermissionToClient(PermissionToClientDTO p2cDTO);

    /**
     * 获取服务接口权限拦截信息
     * @param clientId
     * @return
     */
    DubboResponse<ClientWithPermissionDTO> getClientWithPermission(Long clientId);
}
