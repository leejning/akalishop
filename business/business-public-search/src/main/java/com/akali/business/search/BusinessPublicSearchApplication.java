package com.akali.business.search;

import com.akali.config.Swagger2ConfigurationAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @ClassName BusinessPublisSearchApplication
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/24 0024
 * @Version V1.0
 **/
@SpringBootApplication(
        scanBasePackageClasses = {
                BusinessPublicSearchApplication.class,
                Swagger2ConfigurationAdapter.class
        })
public class BusinessPublicSearchApplication {
    public static void main(String[] args) {
        SpringApplication.run(BusinessPublicSearchApplication.class,args);
    }
}
