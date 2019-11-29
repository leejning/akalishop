package com.akali.business.admin.goods.feign_client;

import com.akali.business.admin.permission.feign.ClientFeignApi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName ClientFeign
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/26 0026
 * @Version V1.0
 **/
@FeignClient("business-admin-permission-manager")
@RequestMapping("client")
public interface ClientFeign extends ClientFeignApi {

}
