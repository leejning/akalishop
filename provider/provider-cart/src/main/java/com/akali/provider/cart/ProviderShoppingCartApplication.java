package com.akali.provider.cart;

import com.akali.config.mong.CustomSimpleMongoRespriatory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * @ClassName ProviderShoppingCartApplication
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/25 0025
 * @Version V1.0
 **/
@SpringBootApplication(scanBasePackageClasses={
        ProviderShoppingCartApplication.class,
//        DubboSentinelConfiguration.class
})
@EnableMongoRepositories(basePackages = "com.akali.provider.cart.dao",
        repositoryBaseClass = CustomSimpleMongoRespriatory.class)
public class ProviderShoppingCartApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProviderShoppingCartApplication.class,args);
    }

}
