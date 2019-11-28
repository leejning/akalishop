package com.akali.cloud;

import com.akali.config.Swagger2ConfigurationAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @ClassName CloudImageServiceApplication
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/13 0013
 * @Version V1.0
 **/
@SpringBootApplication(
        scanBasePackageClasses = {
                CloudImageServiceApplication.class,
                Swagger2ConfigurationAdapter.class
        })
public class CloudImageServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CloudImageServiceApplication.class,args);
    }
}
