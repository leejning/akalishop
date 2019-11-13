package com.akali.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @ClassName Swagger2ConfigProperties
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/13 0013
 * @Version V1.0
 **/
@Data
@ConfigurationProperties(prefix = "swagger2.doc")
public class Swagger2ConfigProperties {
    private String docTitle;
    private String docDesc;
    private String basePackage;
}
