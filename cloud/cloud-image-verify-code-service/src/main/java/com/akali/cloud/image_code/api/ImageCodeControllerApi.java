package com.akali.cloud.image_code.api;

import com.akali.cloud.image_code.dto.ImageCodeDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName ImageCodeControllerApi
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/25 0025
 * @Version V1.0
 **/
@Api(value = "图片验证码api")
public interface ImageCodeControllerApi {
    @ApiOperation("获取验证码")
    public void defaultKaptcha(String key, HttpServletResponse response) throws IOException;

    @ApiOperation("校验图片验证码")

    public boolean imgvrifyControllerDefaultKaptcha(ImageCodeDTO imageCodeDTO);

}
