package com.akali.business.member.profile;

import com.akali.config.Swagger2ConfigurationAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @ClassName BusinessMemberProfileApplication
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/27 0027
 * @Version V1.0
 **/
@SpringBootApplication(scanBasePackageClasses = {
        BusinessMemberProfileApplication.class,
        Swagger2ConfigurationAdapter.class
})
public class BusinessMemberProfileApplication {
    public static void main(String[] args) {
        SpringApplication.run(BusinessMemberProfileApplication.class,args);
    }
}
