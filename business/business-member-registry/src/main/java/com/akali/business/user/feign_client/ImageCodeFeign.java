package com.akali.business.user.feign_client;

import com.akali.cloud.image_code.feign.ImageCodeFeignApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @ClassName ImageCodeFeignApi
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/25 0025
 * @Version V1.0
 **/
@FeignClient("cloud-image-verify-code-service")
public interface ImageCodeFeign extends ImageCodeFeignApi {
}
