package com.akali.common.dto.member;

import lombok.Data;

/**
 * @ClassName MemberLoginResponseDTO
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/27 0027
 * @Version V1.0
 **/
@Data
public class MemberLoginResponseDTO {
    private Long id;
    /**
     * 会员账号
     */
    private String memberAccount;
    /**
     * 会员名称
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 头像
     */
    private String icon;
}
