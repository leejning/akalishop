package com.akali.business.admin.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName ResourceController
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/26 0026
 * @Version V1.0
 **/
@RestController
@RequestMapping("re")
public class ResourceController {

    @GetMapping
    public String withAdmin(){
        return "admin authorize";
    }
}
