package com.akali.provider.goods;


import com.akali.config.DubboSentinelConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.jpa.repository.support.ExtendedJpaRepository;

/**
 * @ClassName ProviderGoodsApplication
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/11 0011
 * @Version V1.0
 **/
@SpringBootApplication(scanBasePackageClasses={
        ProviderGoodsApplication.class,
        DubboSentinelConfiguration.class
})
@EnableJpaRepositories(repositoryBaseClass = ExtendedJpaRepository.class)
public class ProviderGoodsApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProviderGoodsApplication.class,args);
    }
}
