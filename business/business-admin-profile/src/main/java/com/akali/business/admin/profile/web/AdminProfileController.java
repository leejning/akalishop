package com.akali.business.admin.profile.web;

import com.akali.business.admin.profile.api.AdminProfileControllerApi;
import com.akali.common.dto.admin.AdminLoginInfoDTO;
import com.akali.common.model.response.DubboResponse;
import com.akali.common.model.response.ResponseResult;
import com.akali.common.utils.ExceptionCast;
import com.akali.provider.user.admin.service.AdminUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName AdminProfileController
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/12/2 0002
 * @Version V1.0
 **/
@RestController
@RequestMapping("admin/profile")
@Slf4j
public class AdminProfileController implements AdminProfileControllerApi {
    @Reference(version = "1.0.0")
    private AdminUserService adminUserService;

    @GetMapping("login/info")
    @Override
    public ResponseResult<AdminLoginInfoDTO> getAdminProfile(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String adminAccount = authentication.getName();
        log.info(">>>>>>>获取管理员{}登录信息。",adminAccount);
        DubboResponse<AdminLoginInfoDTO> response = adminUserService.getAdminLoginProfile( adminAccount);
        if(!response.isSuccess()){
            ExceptionCast.cast(response.getResultCode());
        }
        return ResponseResult.SUCCESS(response.getData());
    }
}
