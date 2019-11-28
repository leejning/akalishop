package com.akali.provider.user.admin.bean;

import lombok.Data;

import javax.persistence.*;

/**
 * @ClassName AmsPermission
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/26 0026
 * @Version V1.0
 **/
@Data
@Entity
@Table(name = "ams_permission")
public class AmsPermission {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    /**
     * 权限名
     */
    private String permissionName;

    /**
     * uri 可访问uri
     */
    private String uri;
    /**
     * 权限标识
     */
    private String authoritySign;

}
