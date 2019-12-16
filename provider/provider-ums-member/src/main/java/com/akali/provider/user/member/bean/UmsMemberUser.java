package com.akali.provider.user.member.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * @ClassName UmsMemberUser
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/16 0016
 * @Version V1.0
 **/
@Entity
@Table(name = "ums_member_user")
@Data
@NoArgsConstructor
public class UmsMemberUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 会员账号
     */
    @Column(unique = true)
    private String memberAccount;
    /**
     * 会员名称
     */
    @Column(length = 64,unique = true)
    private String username;
    /**
     * 密码
     */
    @Column(length = 64)
    private String password;
    /**
     * 昵称
     */
    @Column(length = 64)
    private String nickname;
    /**
     * 手机号码
     */
    @Column(length = 64,unique = true)
    private String phone;
    /**
     * 手机号码
     */
    @Column(length = 64,unique = true)
    private String email;
    /**
     * 帐号启用状态:false->禁用；true->启用
     */
    @Column(length = 1)
    private Boolean status;
    /**
     * 注册时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Date createTime;
    /**
     * 头像
     */
    @Column(length = 300)
    private String icon;
    /**
     * 性别 ，0->未知，1->男，2->女
     */
    @Column(length = 1)
    private Integer gender;
    /**
     * 所在城市
     */
    @Column(length = 64)
    private String city;
    /**
     * 职业
     */
    @Column(length = 64)
    private String job;
    /**
     * 个性签名
     */
    @Column(length = 600)
    private String personalizedSignature;
    /**
     * 购物积分
     */
    @Column(length = 11)
    private Integer integration;

    public UmsMemberUser(Long id, String memberAccount, String username, String password, String icon) {
        this.id = id;
        this.memberAccount = memberAccount;
        this.username = username;
        this.password = password;
        this.icon = icon;
    }
}
