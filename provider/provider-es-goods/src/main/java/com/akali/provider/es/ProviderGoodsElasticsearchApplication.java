package com.akali.provider.es;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

/**
 * @ClassName ProviderGoodsElasticsearchApplication
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/16 0016
 * @Version V1.0
 **/
@SpringBootApplication
public class ProviderGoodsElasticsearchApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProviderGoodsElasticsearchApplication.class,args);
    }
    @PostConstruct
    void init() {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
    }

}
