package com.akali.business.member.profile.web;

import com.akali.business.member.profile.api.MemberProfileControllerApi;
import com.akali.common.dto.member.MemberProfileDTO;
import com.akali.common.model.response.DubboResponse;
import com.akali.common.model.response.ResponseResult;
import com.akali.common.utils.ExceptionCast;
import com.akali.config.member.oauth2.Oauth2JwtUtil;
import com.akali.provider.user.member.service.MemberService;
import org.apache.dubbo.config.annotation.Reference;
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

    @Reference(version = "1.0.0")
    private MemberService memberService;

    @GetMapping
    @Override
    public ResponseResult<MemberProfileDTO> getMemberProfile(HttpServletRequest request) {
        Long memberId = Oauth2JwtUtil.getLoginMemberId(request);
       DubboResponse<MemberProfileDTO> response = memberService.getMemberProfile(memberId);
       if(!response.isSuccess()){
           ExceptionCast.cast(response.getResultCode());
       }
        return ResponseResult.SUCCESS(response.getData()).message("查询会员用户详细信息成功");
    }
}
