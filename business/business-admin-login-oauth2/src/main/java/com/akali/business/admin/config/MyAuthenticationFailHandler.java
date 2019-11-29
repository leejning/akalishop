package com.akali.business.admin.config;

import com.akali.common.code.AdminCode;
import com.akali.common.model.response.QueryResponseResult;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName MyAuthenticationFailHandler
 * @Description: TODO 登录失败回调 json
 * @Author Administrator
 * @Date 2019/11/26 0026
 * @Version V1.0
 **/
@Component
public class MyAuthenticationFailHandler extends SimpleUrlAuthenticationFailureHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        logger.info("登录失败");
        //设置状态码
        response.setStatus(500);
        response.setContentType("application/json;charset=UTF-8");
        //将 登录失败 信息打包成json格式返回
        response.getWriter().write(JSON.toJSONString(QueryResponseResult.FAIL(AdminCode.ADMIN_LOGIN_FAIL)));
    }
}
