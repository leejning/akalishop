package com.akali.business.goods;

import com.akali.config.Swagger2ConfigurationAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @ClassName BusinessAdminGoodsManageApplication
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/11 0011
 * @Version V1.0
 **/
@SpringBootApplication(
        scanBasePackageClasses = {
                BusinessAdminGoodsManageApplication.class,
                Swagger2ConfigurationAdapter.class
})
public class BusinessAdminGoodsManageApplication {
    public static void main(String[] args) {
        SpringApplication.run(BusinessAdminGoodsManageApplication.class,args);
    }
}
