package com.akali.business.user;

import com.akali.config.Swagger2ConfigurationAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @ClassName BusinessMemberRegistryApplication
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/16 0016
 * @Version V1.0
 **/
@SpringBootApplication(
        scanBasePackageClasses = {
                BusinessMemberRegistryApplication.class,
                Swagger2ConfigurationAdapter.class
        })
@EnableFeignClients
@EnableDiscoveryClient
public class BusinessMemberRegistryApplication {
    public static void main(String[] args) {
        SpringApplication.run(BusinessMemberRegistryApplication.class, args);
    }

}
