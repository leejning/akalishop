package com.akali.provider.user.member.service;

import com.akali.common.dto.member.MemberLoginResponseDTO;
import com.akali.common.dto.member.MemberRegDTO;
import com.akali.common.model.response.DubboResponse;
import org.apache.dubbo.config.annotation.Reference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MemberServiceImplTest {

    @Reference(version = "1.0.0")
    private MemberService memberService;
    @Test
    public void getMemberByAccount() {
        DubboResponse<MemberLoginResponseDTO> respone = memberService.getMemberByAccount("769394");
        System.out.println();
    }
    @Test
    public void checkExistByMemberAccount(){
        DubboResponse<Void> response = memberService.checkExistByMemberAccount("88888");
        System.out.println();
    }

    @Test
    public void registry(){
        MemberRegDTO memberRegDTO = new MemberRegDTO();
        memberRegDTO.setEmail("1317714631@qq.com");
        memberRegDTO.setNickname("ljn");
        memberRegDTO.setPassword("123456");
        memberService.registry(memberRegDTO);
    }

}