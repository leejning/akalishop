package com.akali.common.dto.member;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName MemberProfileDTO
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/27 0027
 * @Version V1.0
 **/
@Data
public class MemberProfileDTO implements Serializable {
    private static final long serialVersionUID = 4952424118888939659L;

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
     * 昵称
     */
    private String nickname;
    /**
     * 手机号码
     */
    private String phone;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 注册时间
     */
    private Date createTime;
    /**
     * 头像
     */
    private String icon;
    /**
     * 性别 ，0->未知，1->男，2->女
     */
    private Integer gender;
    /**
     * 所在城市
     */
    private String city;
    /**
     * 职业
     */
    private String job;
    /**
     * 个性签名
     */
    private String personalizedSignature;
    /**
     * 购物积分
     */
    private Integer integration;
}
