package com.akali.provider.user.admin.bean;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * @ClassName UmsAdminRole
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/26 0026
 * @Version V1.0
 **/
@Data
@Entity
@Table(name = "ums_admin_role")
public class UmsAdminRole {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    /**
     * 角色名
     */
    private String roleName;

    /**
     * 角色与权限的多对多联系
     */
    @ManyToMany(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    @JoinTable(name = "ums_admin_role_permission")
    private List<AmsPermission> permissions;
}
