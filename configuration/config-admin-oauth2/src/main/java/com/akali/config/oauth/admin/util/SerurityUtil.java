package com.akali.config.oauth.admin.util;

import com.akali.common.dto.admin.PermissionDTO;
import com.akali.config.oauth.admin.handler.AdminAccessDeniedHandler;
import com.akali.config.oauth.admin.handler.AdminAuthenticationEntryPoint;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;

import java.util.List;

/**
 * @ClassName SerurityUtil
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/26 0026
 * @Version V1.0
 **/
public class SerurityUtil {
    public static void configHttpSecurity(HttpSecurity http, List<PermissionDTO> permissions) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config =
                http.exceptionHandling()
                        .and()
                        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)//采取永不存储session的政策
                        .and()
                        .authorizeRequests();

        for (PermissionDTO permission : permissions) {
            http.authorizeRequests()
                    .antMatchers(permission.getUri()).hasAuthority(permission.getAuthoritySign()).and();
        }

        http.exceptionHandling()
                .accessDeniedHandler(new AdminAccessDeniedHandler())
                .authenticationEntryPoint(new AdminAuthenticationEntryPoint())
                .and()
                .authorizeRequests()
                .antMatchers("/**").hasAuthority("admin")
                .and()
                .csrf().disable()
                .rememberMe().rememberMeParameter("remember-me").tokenValiditySeconds(300);

    }
}
