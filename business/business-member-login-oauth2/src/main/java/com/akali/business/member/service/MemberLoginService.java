package com.akali.business.member.service;


import com.akali.business.member.dto.AuthToken;
import com.akali.common.code.AuthCode;
import com.akali.common.code.MemberCode;
import com.akali.common.dto.member.MemberLoginRequestDTO;
import com.akali.common.model.response.DubboResponse;
import com.akali.common.utils.ExceptionCast;
import com.akali.provider.user.member.service.MemberService;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.data.redis.core.StringRedisTemplate;
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
import java.util.concurrent.TimeUnit;

/**
 * @ClassName LoginService
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/9/27 0027
 * @Version V1.0
 **/
@Service
@Slf4j
public class MemberLoginService {
    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Value("${member.auth.tokenValiditySeconds}")
    private Long tokenValiditySeconds;

    @Value("${member.auth.clientId}")
    private String clientId;

    @Value("${member.auth.clientSecret}")
    private String clientSecret;

    private static String USER_TOKEN_INFO_PREFIX = "ic_token_user_id:";

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    RestTemplate restTemplate;

    @Reference(version = "1.0.0")
    private MemberService memberService;

    public Map<String, Object> login(MemberLoginRequestDTO memberLoginRequestDTO) {
        log.info(">>>>>会员：{}，请求登录", memberLoginRequestDTO.getMemberAccount());
        //检查账号是否存在
        DubboResponse<Void> response = memberService.checkExistByMemberAccount(memberLoginRequestDTO.getMemberAccount());
        //不存在或异常
        if(!response.isSuccess()){
            log.info(">>>>>会员：{}，并不存在", memberLoginRequestDTO.getMemberAccount());
            ExceptionCast.cast(AuthCode.AUTH_ACCOUNT_NOTEXISTS);
        }

        //请求spring security申请令牌

        AuthToken authToken = applyToken(memberLoginRequestDTO.getMemberAccount(), memberLoginRequestDTO.getPassword(), clientId, clientSecret);
        if (authToken == null) {
            ExceptionCast.cast(AuthCode.AUTH_LOGIN_APPLYTOKEN_FAIL);
        }

        //保存令牌到redis
        boolean res = saveToken2Redis(authToken, memberLoginRequestDTO.getMemberAccount());
        if (!res) {
            ExceptionCast.cast(AuthCode.AUTH_LOGIN_TOKEN_SAVEFAIL);
        }

        //包装结果
        Map<String, Object> map = Maps.newHashMap();
        map.put("jti", authToken.getJti());
        map.put("token", authToken.getJwt_token());
        return map;
    }

    private boolean saveToken2Redis(AuthToken authToken, String username) {
        String key = USER_TOKEN_INFO_PREFIX + username;
        redisTemplate.boundValueOps(key).set(authToken.getJwt_token(),tokenValiditySeconds, TimeUnit.SECONDS);
        Long expire = redisTemplate.getExpire(key, TimeUnit.SECONDS);
        return expire>0;
    }


    /**
     * 发送http请求，申请令牌
     *
     * @MethodName: applyToken
     * @Description: TODO
     * @Param: [username, password, clientId, clientSecret]
     * @Return: com.online.icourse.business.dto.AuthToken
     * @Author: Administrator
     * @Date: 2019/9/28 0028
     **/
    private AuthToken applyToken(String username, String password, String clientId, String clientSecret) {
        ServiceInstance serviceInstance = loadBalancerClient.choose("business-member-login-oauth2");
        URI uri = serviceInstance.getUri();
        String authUrl = uri + "/oauth/token";
        //定义header
        LinkedMultiValueMap<String, String> header = new LinkedMultiValueMap<>();
        String httpBasic = getHttpBasic(clientId, clientSecret);
        header.add("Authorization", httpBasic);

        //定义body
        LinkedMultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "password");
        body.add("username", username);
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
        if(exchange.getStatusCodeValue()!=200){
            log.info(">>>>>>会员：{} 请求登录，但密码错误！",username);
            ExceptionCast.cast(MemberCode.MEMBER_PASSWORD_ERROR);
        }

        //成功获取token,解析token
        log.info(">>>>>>会员：{} 请求登录,密码正确，成功获取到token！",username);
        Map bodyMap = exchange.getBody();
        if (bodyMap == null ||bodyMap.get("jti") == null||
                bodyMap.get("access_token") == null ||
                bodyMap.get("refresh_token") == null) {
            if ("invalid_grant".equals(bodyMap.get("error"))) {
                ExceptionCast.cast(AuthCode.AUTH_CREDENTIAL_ERROR);
            } else if ("unauthorized".equals(bodyMap.get("error"))) {
                ExceptionCast.cast(AuthCode.AUTH_ACCOUNT_NOTEXISTS);
            }

            return null;
        }
        AuthToken authToken = new AuthToken();
        authToken.setRefresh_token((String) bodyMap.get("refresh_token"));//刷新令牌
        authToken.setJwt_token((String) bodyMap.get("access_token"));
        authToken.setJti((String) bodyMap.get("jti"));//jti
        return authToken;
    }

    //获取httpbasic的串
    private String getHttpBasic(String clientId, String clientSecret) {
        String string = clientId + ":" + clientSecret;
        //将串进行base64编码
        byte[] encode = Base64Utils.encode(string.getBytes());
        return "Basic " + new String(encode);
    }

    public boolean logout(String username) {

        return true;
    }
}
