package com.akali.cloud.image_code.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @ClassName KaptchProperties
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/24 0024
 * @Version V1.0
 **/
@Data
@ConfigurationProperties(prefix = "kaptcha")
public class KaptchProperties {
    /**
     * properties.setProperty("kaptcha.border", "no");
     *         properties.setProperty("kaptcha.border.color", "105,179,90");
     *         properties.setProperty("kaptcha.textproducer.font.color", "red");
     *         properties.setProperty("kaptcha.image.width", "110");
     *         properties.setProperty("kaptcha.image.height", "40");
     *         properties.setProperty("kaptcha.textproducer.font.size", "40");
     *         properties.setProperty("kaptcha.session.key", "code");
     *         properties.setProperty("kaptcha.textproducer.char.length", "4");
     *         properties.setProperty("kaptcha.textproducer.font.names", "宋体,楷体,微软雅黑");
     */
    String border;
    String borderColor;
    String fontColor;
    String width;
    String height;
    String fontSize;
    String key;
    String length;
    List<String> fontNames;

}
