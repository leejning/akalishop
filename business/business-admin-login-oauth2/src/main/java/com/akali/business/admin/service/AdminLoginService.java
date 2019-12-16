package com.akali.business.admin.service;

import com.akali.common.code.AuthCode;
import com.akali.common.code.MemberCode;
import com.akali.common.dto.admin.AdminLoginForm;
import com.akali.common.dto.admin.AdminTokenDTO;
import com.akali.common.model.response.DubboResponse;
import com.akali.common.utils.ExceptionCast;
import com.akali.provider.user.admin.service.AdminUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

/**
 * @ClassName AdminLoginService
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/30 0030
 * @Version V1.0
 **/
@Service
@Slf4j
public class AdminLoginService {

    @Reference(version = "1.0.0")
    private AdminUserService adminUserService;
    @Value("${admin.auth.clientId}")
    private String clientId;
    @Value("${admin.auth.clientSecret}")
    private String clientSecret;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    private LoadBalancerClient loadBalancerClient;


    public AdminTokenDTO login(AdminLoginForm adminLoginForm) {
        log.info(">>>>>管理员：{}，请求登录", adminLoginForm.getUsername());
        //检查账号是否存在
        DubboResponse<Void> response = adminUserService.checkExistByAdminAccount(adminLoginForm.getUsername());
        //不存在或异常
        if (!response.isSuccess()) {
            log.info(">>>>>管理员：{}，并不存在", adminLoginForm.getUsername());
            ExceptionCast.cast(AuthCode.AUTH_ACCOUNT_NOTEXISTS);
        }

        //请求spring security申请令牌

        AdminTokenDTO adminTokenDTO = applyToken(adminLoginForm.getUsername(), adminLoginForm.getPassword(), clientId, clientSecret);
        if (adminTokenDTO == null) {
            ExceptionCast.cast(AuthCode.AUTH_LOGIN_APPLYTOKEN_FAIL);
        }
        return adminTokenDTO;
    }


    private AdminTokenDTO applyToken(String adminAccount, String password, String clientId, String clientSecret) {
        ServiceInstance serviceInstance = loadBalancerClient.choose("business-admin-login-oauth2");
        URI uri = serviceInstance.getUri();
        String authUrl = uri + "/oauth/token";
        //定义header
        LinkedMultiValueMap<String, String> header = new LinkedMultiValueMap<>();
        String httpBasic = getHttpBasic(clientId, clientSecret);
        header.add("Authorization", httpBasic);

        //定义body
        LinkedMultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "password");
        body.add("username", adminAccount);
        body.add("password", password);

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(body, header);

        //设置restTemplate远程调用时候，对400和401不让报错，后面手动抛异常
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
            @Override
            public void handleError(ClientHttpResponse response) throws IOException {
                if (response.getRawStatusCode() != 400 && response.getRawStatusCode() != 401) {
                    super.handleError(response);
                }
            }
        });

        ResponseEntity<Map> exchange = restTemplate.exchange(authUrl, HttpMethod.POST, httpEntity, Map.class);

        //检查是否成功获取token
        if (exchange.getStatusCodeValue() != 200) {
            log.info(">>>>>>管理员：{} 请求登录，但密码错误！", adminAccount);
            ExceptionCast.cast(MemberCode.MEMBER_PASSWORD_ERROR);
        }

        //判断是否成功获取token
        Map bodyMap = exchange.getBody();
        if (bodyMap == null || bodyMap.get("access_token") == null || bodyMap.get("refresh_token") == null) {
            if ("invalid_grant".equals(bodyMap.get("error"))) {
                log.info(">>>>>>管理员：{} 请求登录，但密码错误！", adminAccount);
                ExceptionCast.cast(AuthCode.AUTH_CREDENTIAL_ERROR);
            }
            log.info(">>>>>>管理员：{} 请求登录，发生异常，此段代码应该永不会执行！", adminAccount);
            ExceptionCast.cast(AuthCode.AUTH_LOGIN_ERROR);
        }

        //成功获取token,解析token
        log.info(">>>>>>管理员：{} 请求登录，登录成功，解析token！", adminAccount);
        AdminTokenDTO adminTokenDTO = new AdminTokenDTO();
        adminTokenDTO.setAccess_token((String) bodyMap.get("access_token"));
        return adminTokenDTO;
    }


    private String getHttpBasic(String clientId, String clientSecret) {
        String string = clientId + ":" + clientSecret;
        //将串进行base64编码
        byte[] encode = Base64Utils.encode(string.getBytes());
        return "Basic " + new String(encode);
    }
}
