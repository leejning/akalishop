package com.akali.config.oauth.admin.handler;

import com.akali.common.code.AdminCode;
import com.akali.common.model.response.ResponseResult;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class AdminAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        log.info(">>>>>>请求拦截："+e.getMessage());
        ResponseResult<Object> fail = ResponseResult.FAIL(AdminCode.DENY_REQUEST);
        httpServletResponse.getWriter().write(JSON.toJSONString(fail));
    }
}
