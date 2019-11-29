package com.akali.business.admin.goods.config;

import com.akali.business.admin.goods.feign_client.ClientFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
@Slf4j
public class RescourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private ClientFeign clientFeign;


    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().permitAll();


//        QueryResponseResult<ClientWithPermissionDTO> response = clientFeign.getClientWithPermission(1L);
//        if (!response.isSuccess()) {
//            ExceptionCast.cast(CommonCode.APP_UP_FAIL);
//        }
//        ClientWithPermissionDTO client = response.getData();
//        SerurityUtil.configHttpSecurity(http, client.getPermissions());
    }
}
