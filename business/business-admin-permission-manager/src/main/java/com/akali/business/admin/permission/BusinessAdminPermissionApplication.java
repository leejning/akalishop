package com.akali.business.admin.permission;

import com.akali.config.Swagger2ConfigurationAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @ClassName BusinessAdminPermissionApplication
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/26 0026
 * @Version V1.0
 **/
@SpringBootApplication(
        scanBasePackageClasses = {
                BusinessAdminPermissionApplication.class,
                Swagger2ConfigurationAdapter.class
        })
@EnableDiscoveryClient
public class BusinessAdminPermissionApplication {
    public static void main(String[] args) {
        SpringApplication.run(BusinessAdminPermissionApplication.class,args);
    }
}
