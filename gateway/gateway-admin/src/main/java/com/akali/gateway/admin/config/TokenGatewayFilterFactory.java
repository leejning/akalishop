package com.akali.gateway.admin.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

/**
 * @ClassName TokenGatewayFilterFactory
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/10/8 0008
 * @Version V1.0
 **/
public class TokenGatewayFilterFactory extends AbstractGatewayFilterFactory<TokenGatewayFilterFactory.Config> {

    private static final Log log = LogFactory.getLog(GatewayFilter.class);
    private static final String ELAPSED_TIME_BEGIN = "elapsedTimeBegin";
    private static final String KEY = "withParams";

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList(KEY);
    }

    public TokenGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String authorization = exchange.getRequest().getHeaders().getFirst("Authorization");
            if (!GlobalTokenFilter.checkAuthorization(authorization)) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
            return chain.filter(exchange);
        };
    }


    public static class Config {

        private boolean withParams;

        public boolean isWithParams() {
            return withParams;
        }

        public void setWithParams(boolean withParams) {
            this.withParams = withParams;
        }

    }
}