package com.akali.business.admin;

import com.akali.config.Swagger2ConfigurationAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @ClassName BusinessAdminLoginOauth2Application
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/26 0026
 * @Version V1.0
 **/
@SpringBootApplication(scanBasePackageClasses = {
        BusinessAdminLoginOauth2Application.class,
        Swagger2ConfigurationAdapter.class
})
public class BusinessAdminLoginOauth2Application {
    public static void main(String[] args) {
        SpringApplication.run(BusinessAdminLoginOauth2Application.class,args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate(new OkHttp3ClientHttpRequestFactory());
    }
}
