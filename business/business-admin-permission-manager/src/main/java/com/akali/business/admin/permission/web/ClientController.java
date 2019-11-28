package com.akali.business.admin.permission.web;

import com.akali.business.admin.permission.api.ClientControllerApi;
import com.akali.common.dto.admin.ClientWithPermissionDTO;
import com.akali.common.dto.admin.PermissionToClientDTO;
import com.akali.common.model.response.DubboResponse;
import com.akali.common.model.response.ResponseResult;
import com.akali.common.utils.ExceptionCast;
import com.akali.provider.user.admin.service.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName ClientController
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/26 0026
 * @Version V1.0
 **/
@RestController
@RequestMapping("client")
@Slf4j
public class ClientController implements ClientControllerApi {
    @Reference(version = "1.0.0")
    private ClientService clientService;


    /**
     * 添加服务接口的权限拦截
     * @param p2cDTO
     * @return
     */
    @PostMapping
    @Override
    public ResponseResult<Void> addPermissionToClient(PermissionToClientDTO p2cDTO) {
        DubboResponse<Void> response = clientService.addPermissionToClient(p2cDTO);
        if (!response.isSuccess()) {
            ExceptionCast.cast(response.getResultCode());
        }
        return ResponseResult.SUCCESS().message("添加服务接口的权限拦截成功！");
    }

    /**
     * 获取服务接口权限拦截信息
     * @param clientId
     * @return
     */
    @GetMapping("permissions/{clientId}")
    @Override
    public ResponseResult<ClientWithPermissionDTO> getClientWithPermission(@PathVariable Long clientId) {
        log.info(">>>>>>获取客户端：{} 的权限拦截信息",clientId);
        DubboResponse<ClientWithPermissionDTO> response = clientService.getClientWithPermission(clientId);
        if (!response.isSuccess()) {
            ExceptionCast.cast(response.getResultCode());
        }
        return ResponseResult.SUCCESS(response.getData()).message("获取服务接口权限拦截信息成功！");
    }
}
