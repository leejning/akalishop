package com.akali.business.free.goods;

import com.akali.config.Swagger2ConfigurationAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @ClassName BusinessPublicGoodsApplication
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/28 0028
 * @Version V1.0
 **/
@SpringBootApplication(scanBasePackageClasses = {
            BusinessPublicGoodsApplication.class,
            Swagger2ConfigurationAdapter.class
})
public class BusinessPublicGoodsApplication {
    public static void main(String[] args) {
        SpringApplication.run(BusinessPublicGoodsApplication.class,args);
    }
}
