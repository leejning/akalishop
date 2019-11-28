package com.akali.config.oauth.admin.handler;

import com.akali.common.code.AdminCode;
import com.akali.common.model.response.ResponseResult;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class AdminAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        log.info(">>>>>>请求拦截："+e.getMessage());
        ResponseResult<Object> fail = ResponseResult.FAIL(AdminCode.DENY_REQUEST);
        httpServletResponse.getWriter().write(JSON.toJSONString(fail));

    }
}
