package com.akali.business.member.service;

import com.akali.business.member.dto.JwtUser;
import com.akali.common.dto.member.MemberLoginResponseDTO;
import com.akali.common.model.response.DubboResponse;
import com.akali.provider.user.member.service.MemberService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @ClassName Oauth2UserServiceImpl
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/9/27 0027
 * @Version V1.0
 **/
@Service
public class Oauth2UserServiceImpl implements UserDetailsService {
    private static final String USERNAME = "member";
    private static final String PASSWORD = "$2a$10$DAAK/E3zAm5qyxMdJbGG9uaZNhISZeRDbXw0lQvcKXuOdHRZwUTma";
    private static final String USER_PERMISSION = "USER";
    @Reference(version = "1.0.0")
    private MemberService memberService;

    @Override
    public UserDetails loadUserByUsername(String memberAccount) throws UsernameNotFoundException {
        if (StringUtils.isBlank(memberAccount)) {
            return null;
        }
        DubboResponse<MemberLoginResponseDTO> response= memberService.getMemberByAccount(memberAccount);
        if(!response.isSuccess()){
            return null;
        }
        MemberLoginResponseDTO member = response.getData();
        JwtUser jwtUser = new JwtUser(memberAccount, member.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList(USER_PERMISSION));

        jwtUser.setMemberName(member.getUsername());
        jwtUser.setMemberId(member.getId());
        jwtUser.setIcon(member.getIcon());
        return jwtUser;
    }

}
