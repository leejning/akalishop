package com.akali.provider.user.member;

import com.akali.config.DubboSentinelConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.jpa.repository.support.ExtendedJpaRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @ClassName ProviderUmsMemberApplication
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/16 0016
 * @Version V1.0
 **/
@SpringBootApplication(scanBasePackageClasses={
        ProviderUmsMemberApplication.class,
        DubboSentinelConfiguration.class
})
@EnableJpaRepositories(repositoryBaseClass = ExtendedJpaRepository.class)
public class ProviderUmsMemberApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProviderUmsMemberApplication.class,args);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
