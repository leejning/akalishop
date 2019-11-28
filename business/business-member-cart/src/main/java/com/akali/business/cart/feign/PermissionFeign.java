package com.akali.business.cart.feign;

import com.akali.common.dto.admin.ClientWithPermissionDTO;
import com.akali.common.model.response.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName PermissionFeign
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/26 0026
 * @Version V1.0
 **/
@FeignClient("business-admin-permission-manager")
@RequestMapping("client")
public interface PermissionFeign {
    @GetMapping
    public ResponseResult<String> getPermissions();
    @GetMapping("permissions")
    public ResponseResult<ClientWithPermissionDTO> getClientWithPermission(Long clientId);
}
