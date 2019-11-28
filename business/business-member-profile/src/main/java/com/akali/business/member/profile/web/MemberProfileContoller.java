package com.akali.business.member.profile.web;

import com.akali.business.member.profile.api.MemberProfileControllerApi;
import com.akali.common.dto.member.MemberProfileDTO;
import com.akali.common.model.response.ResponseResult;
import com.akali.config.member.oauth2.Oauth2JwtUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName MemberProfileContoller
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/27 0027
 * @Version V1.0
 **/
@RestController
@RequestMapping("member/profile")
public class MemberProfileContoller implements MemberProfileControllerApi {


    @GetMapping
    @Override
    public ResponseResult<MemberProfileDTO> getMemberProfile(HttpServletRequest request) {
        Long memberId = Oauth2JwtUtil.getLoginMemberId(request);
        MemberProfileDTO data = new MemberProfileDTO();
        data.setMemberName("测试用户cha");
        data.setIcon("11111");
        data.setMemberId(memberId);
        return ResponseResult.SUCCESS(data).message("查询会员个人信息成功");
    }
}
