package com.akali.business.admin.permission.web;

import com.akali.common.model.response.ResponseResult;
import com.akali.provider.user.admin.service.PermissionService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName PermissionController
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/26 0026
 * @Version V1.0
 **/
@RestController
@RequestMapping("permission")
public class PermissionController {

    @Reference(version = "1.0.0")
    private PermissionService permissionService;

    @GetMapping
    public ResponseResult<String> getPermissions(){
        return ResponseResult.SUCCESS("user,admin");
    }

}
