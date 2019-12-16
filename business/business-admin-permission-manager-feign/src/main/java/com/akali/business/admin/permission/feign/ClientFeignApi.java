package com.akali.business.admin.permission.feign;

import com.akali.common.dto.admin.ClientWithPermissionDTO;
import com.akali.common.model.response.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName ClientFeignApi
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/26 0026
 * @Version V1.0
 **/
@RequestMapping("client")
public interface ClientFeignApi {
    @GetMapping("permissions/{clientId}")
    public ResponseResult<ClientWithPermissionDTO> getClientWithPermission(@PathVariable Long clientId);

}
