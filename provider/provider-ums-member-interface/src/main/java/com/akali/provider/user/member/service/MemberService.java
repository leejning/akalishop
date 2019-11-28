package com.akali.provider.user.member.service;

import com.akali.common.dto.member.MemberLoginResponseDTO;
import com.akali.common.dto.member.MemberRegDTO;
import com.akali.common.model.response.DubboResponse;

/**
 * @ClassName MemberService
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/16 0016
 * @Version V1.0
 **/
public interface MemberService {
    /**
     * 会员注册
     * @param memberRegDTO
     * @return
     */
    DubboResponse<Void> registry(MemberRegDTO memberRegDTO);

    /**
     * 校验邮箱
     * @param email
     * @return
     */
    DubboResponse<Void> checkEmail(String email);

    /**
     * 根据账号检查用户是否存在
     * @param account
     * @return
     */
    DubboResponse<Void> checkExistByMemberAccount(String account);

    /**
     *
     * @param account
     * @return
     */
    DubboResponse<MemberLoginResponseDTO> getMemberByAccount(String account);
}
