package com.akali.business.member.web;

import com.akali.business.member.api.MemberLoginControllerApi;
import com.akali.business.member.service.MemberLoginService;
import com.akali.common.dto.member.MemberLoginRequestDTO;
import com.akali.common.model.response.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @ClassName MemberLoginController
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/9/27 0027
 * @Version V1.0
 **/
@RestController
public class MemberLoginController implements MemberLoginControllerApi {
    @Autowired
    MemberLoginService memberLoginService;

    @Override
    @PostMapping("user/login")
    public ResponseResult<Map<String,Object>> login(@RequestBody MemberLoginRequestDTO memberLoginRequestDTO){
        Map<String, Object> login = memberLoginService.login(memberLoginRequestDTO);

        return ResponseResult.SUCCESS(login).message("会员登录成功");
    }

    @PostMapping("user/logout")
    public ResponseEntity<String> logout(){
//        return oauthService.logout("李四");
        return ResponseEntity.ok("退出登录");
    }

    @GetMapping("/user/echo/{s}")
    public ResponseEntity<String> echo(@PathVariable String s){
        return ResponseEntity.ok("hello"+s);
    }




}
