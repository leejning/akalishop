package com.akali.business.member;

import com.akali.config.Swagger2ConfigurationAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @ClassName BusinessMemberLoginOauth2Application
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/27 0027
 * @Version V1.0
 **/
@SpringBootApplication(
        scanBasePackageClasses = {
            BusinessMemberLoginOauth2Application.class,
            Swagger2ConfigurationAdapter.class}
)
public class BusinessMemberLoginOauth2Application {
    public static void main(String[] args) {
        SpringApplication.run(BusinessMemberLoginOauth2Application.class,args);
    }


    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate(new OkHttp3ClientHttpRequestFactory());
    }
}
