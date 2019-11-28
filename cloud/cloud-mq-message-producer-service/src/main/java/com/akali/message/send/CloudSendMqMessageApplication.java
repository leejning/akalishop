package com.akali.message.send;

import com.akali.config.Swagger2ConfigurationAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @ClassName CloudSendMessageApplication
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/25 0025
 * @Version V1.0
 **/
@SpringBootApplication(
        scanBasePackageClasses = {
                CloudSendMqMessageApplication.class,
                Swagger2ConfigurationAdapter.class
        })
@EnableDiscoveryClient
public class CloudSendMqMessageApplication {
    public static void main(String[] args) {
        SpringApplication.run(CloudSendMqMessageApplication.class, args);
    }
}
