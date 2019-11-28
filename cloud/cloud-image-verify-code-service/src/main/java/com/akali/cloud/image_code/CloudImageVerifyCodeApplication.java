package com.akali.cloud.image_code;

import com.akali.config.Swagger2ConfigurationAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @ClassName CloudImageVerifyCodeApplication
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/24 0024
 * @Version V1.0
 **/
@SpringBootApplication(
        scanBasePackageClasses = {
                CloudImageVerifyCodeApplication.class,
                Swagger2ConfigurationAdapter.class
        })
@EnableDiscoveryClient
public class CloudImageVerifyCodeApplication {
    public static void main(String[] args) {
        SpringApplication.run(CloudImageVerifyCodeApplication.class,args);
    }

}
