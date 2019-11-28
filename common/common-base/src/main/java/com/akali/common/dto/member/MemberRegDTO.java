package com.akali.common.dto.member;

import lombok.Data;

/**
 * @ClassName MemberRegDTO
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/16 0016
 * @Version V1.0
 **/
@Data
public class MemberRegDTO {
    /**
     * 图片验证码id
     */
    private String photoValidCodeId;
    /**
     * 图片验证码
     */
    private String photoValidCode;
    /**
     * 注册邮箱
     */
    private String email;
    /**
     * 邮箱验证码
     */
    private String emailValidCode;
    /**
     * 用户昵称
     */
    private String nickname;
    /**
     * 密码
     */
    private String password;
    /**
     * 确认密码
     */
    private String passwordComfirm;


}
