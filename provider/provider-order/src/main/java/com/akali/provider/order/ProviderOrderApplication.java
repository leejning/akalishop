package com.akali.provider.order;

import com.akali.config.DubboSentinelConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.jpa.repository.support.ExtendedJpaRepository;

/**
 * @ClassName ProviderOrderApplication
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/29 0029
 * @Version V1.0
 **/
@SpringBootApplication(scanBasePackageClasses={
        ProviderOrderApplication.class,
        DubboSentinelConfiguration.class
})
@EnableJpaRepositories(repositoryBaseClass = ExtendedJpaRepository.class)
public class ProviderOrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProviderOrderApplication.class,args);
    }
}
