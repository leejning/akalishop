package com.akali.provider.goods;

import com.akali.provider.goods.jpa.ExtendedJpaRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @ClassName ProviderGoodsApplication
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/11 0011
 * @Version V1.0
 **/
@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = ExtendedJpaRepository.class)
public class ProviderGoodsApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProviderGoodsApplication.class,args);
    }
}
