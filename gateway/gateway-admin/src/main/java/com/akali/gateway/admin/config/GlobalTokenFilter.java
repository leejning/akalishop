package com.akali.gateway.admin.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @ClassName GlobalTokenFilter
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/10/8 0008
 * @Version V1.0
 **/
public class GlobalTokenFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String authorization = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (!checkAuthorization(authorization)) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -100;
    }

    public static Boolean checkAuthorization(String authorization){
        if (StringUtils.isEmpty(authorization) || authorization.indexOf("Bearer") < 0) {
            return false;
        }
        String token = authorization.substring(7);
        try {
            //解析jwt
            Jwt decode = JwtHelper.decode(token);
        } catch (Exception e) {
            System.out.println("未授权");
            return false;
        }
        return true;
    }
}