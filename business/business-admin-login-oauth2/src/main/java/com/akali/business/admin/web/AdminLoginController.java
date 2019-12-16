package com.akali.business.admin.web;

import com.akali.business.admin.api.AdminLoginControllerApi;
import com.akali.business.admin.service.AdminLoginService;
import com.akali.common.dto.admin.AdminLoginForm;
import com.akali.common.dto.admin.AdminLoginInfoDTO;
import com.akali.common.dto.admin.AdminTokenDTO;
import com.akali.common.model.response.DubboResponse;
import com.akali.common.model.response.ResponseResult;
import com.akali.common.utils.ExceptionCast;
import com.akali.provider.user.admin.service.AdminUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName AdminLoginController
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/26 0026
 * @Version V1.0
 **/
@RestController
@RequestMapping
@Slf4j
public class AdminLoginController implements AdminLoginControllerApi {
    @Resource
    public TokenStore tokenStore;
    @Autowired
    private AdminLoginService adminLoginService;
    @Reference(version = "1.0.0")
    private AdminUserService adminUserService;

    @Override
    @PostMapping("login")
    public ResponseResult<AdminTokenDTO> login(@RequestBody AdminLoginForm adminLoginForm){
        AdminTokenDTO adminTokenDTO = adminLoginService.login(adminLoginForm);
        return ResponseResult.SUCCESS(adminTokenDTO).message("管理员登录成功");
    }

    @Override
    @PostMapping(value = "/logout")
    public ResponseResult<Void> logout(HttpServletRequest request) {
        // 获取 token
        String token = request.getHeader("Authorization");
        // 删除 token 以注销
        OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(token);
        tokenStore.removeAccessToken(oAuth2AccessToken);
        return ResponseResult.SUCCESS().message("管理员退出登录成功");
    }

    @GetMapping("login/info")
    @Override
    public ResponseResult<AdminLoginInfoDTO> getAdminProfile(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String adminAccount = authentication.getName();
        log.info(">>>>>>>获取管理员{}登录信息。",adminAccount);
        DubboResponse<AdminLoginInfoDTO> response = adminUserService.getAdminLoginProfile(adminAccount);
        if(!response.isSuccess()){
            ExceptionCast.cast(response.getResultCode());
        }
        return ResponseResult.SUCCESS(response.getData());
    }
}
