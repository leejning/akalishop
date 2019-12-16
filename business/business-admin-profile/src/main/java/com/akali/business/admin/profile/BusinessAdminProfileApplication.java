package com.akali.business.admin.profile;

import com.akali.config.Swagger2ConfigurationAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @ClassName BusinessAdminProfileApplication
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/30 0030
 * @Version V1.0
 **/
@SpringBootApplication(scanBasePackageClasses = {
        BusinessAdminProfileApplication.class,
        Swagger2ConfigurationAdapter.class
})
public class BusinessAdminProfileApplication {
    public static void main(String[] args) {
        SpringApplication.run(BusinessAdminProfileApplication.class,args);
    }
}
