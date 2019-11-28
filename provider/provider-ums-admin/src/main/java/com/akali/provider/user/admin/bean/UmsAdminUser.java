package com.akali.provider.user.admin.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @ClassName UmsAdminUser
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/26 0026
 * @Version V1.0
 **/
@Data
@Entity
@Table(name = "ums_admin_user")
public class UmsAdminUser {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    /**
     * 管理员角色
     */
    private Long roleId;
    /**
     * 登录账号对应oauth2的username；
     */
    @Column(length = 32)
    private String loginAccount;
    /**
     * 登录密码
     */
    @Column(length = 128)
    private String password;
    /**
     * 管理员名称
     */
    @Column(length = 32)
    private String adminName;

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


    public UmsAdminUser(Long id,Long roleId, String password) {
        this.id = id;
        this.roleId = roleId;
        this.password = password;
    }
}
