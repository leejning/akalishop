package com.akali.config.member.oauth2.handler;

import com.akali.common.code.AdminCode;
import com.akali.common.model.response.ResponseResult;
import com.alibaba.fastjson.JSON;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AdminAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        ResponseResult<Object> fail = ResponseResult.FAIL(AdminCode.DENY_REQUEST);
        httpServletResponse.getWriter().write(JSON.toJSONString(fail));

    }
}
