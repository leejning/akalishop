package com.akali.business.member.profile.config;

import com.akali.config.member.oauth2.CustomResourceServerConfigAdapter;
import com.akali.config.member.oauth2.handler.AdminAuthenticationEntryPoint;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * @ClassName ResouceServerConfiguration
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/9/29 0029
 * @Version V1.0
 **/
@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class MemberResouceServerConfiguration extends CustomResourceServerConfigAdapter {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.exceptionHandling().authenticationEntryPoint(new AdminAuthenticationEntryPoint()).and()
                .authorizeRequests()
                .anyRequest().authenticated();
    }
}
