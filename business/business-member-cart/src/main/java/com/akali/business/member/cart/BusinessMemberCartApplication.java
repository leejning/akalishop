package com.akali.business.member.cart;

import com.akali.config.Swagger2ConfigurationAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @ClassName BusinessMemberCartApplication
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/26 0026
 * @Version V1.0
 **/
@SpringBootApplication(
        scanBasePackageClasses = {
                BusinessMemberCartApplication.class,
                Swagger2ConfigurationAdapter.class
        })
@EnableFeignClients
@EnableDiscoveryClient
public class BusinessMemberCartApplication {
    public static void main(String[] args) {
        SpringApplication.run(BusinessMemberCartApplication.class,args);
    }
}
