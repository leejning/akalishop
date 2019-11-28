package com.akali.cloud.image_code.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @ClassName KaptchaConfig
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/24 0024
 * @Version V1.0
 **/
@Configuration
@EnableConfigurationProperties(KaptchProperties.class)
public class KaptchaConfig {

    @Bean
    public DefaultKaptcha getDefaultKaptcha(KaptchProperties kaptchProperties){
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        Properties properties = new Properties();
        properties.setProperty("kaptcha.border", kaptchProperties.getBorder());
        properties.setProperty("kaptcha.border.color", kaptchProperties.getBorderColor());
        properties.setProperty("kaptcha.textproducer.font.color", kaptchProperties.getFontColor());
        properties.setProperty("kaptcha.image.width", kaptchProperties.getWidth());
        properties.setProperty("kaptcha.image.height", kaptchProperties.getHeight());
        properties.setProperty("kaptcha.textproducer.font.size", kaptchProperties.getFontSize());
        properties.setProperty("kaptcha.session.key", kaptchProperties.getKey());
        properties.setProperty("kaptcha.textproducer.char.length", kaptchProperties.getLength());
        properties.setProperty("kaptcha.textproducer.font.names",
                StringUtils.join(kaptchProperties.getFontNames().toArray(), ","));

        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}
