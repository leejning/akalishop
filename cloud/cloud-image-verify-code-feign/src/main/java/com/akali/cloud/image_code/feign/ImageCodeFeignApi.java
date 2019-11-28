package com.akali.cloud.image_code.feign;

import com.akali.cloud.image_code.dto.ImageCodeDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName ImageCodeFeignApi
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/25 0025
 * @Version V1.0
 **/
@RequestMapping("image/code")
public interface ImageCodeFeignApi {

    @PostMapping
    @ResponseBody
    public boolean imgvrifyControllerDefaultKaptcha(@RequestBody ImageCodeDTO imageCodeDTO);

}
