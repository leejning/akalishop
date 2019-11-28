package com.akali.provider.user.admin.bean;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * @ClassName AmsClient
 * @Description: TODO 服务鉴权表
 * @Author Administrator
 * @Date 2019/11/26 0026
 * @Version V1.0
 **/
@Data
@Entity
@Table(name = "ams_client")
public class AmsClient {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    /**
     * 服务名
     */
    private String clientName;

    /**
     * 服务与权限的多对多联系
     */
    @ManyToMany(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    @JoinTable(name = "ams_client_permission")
    private List<AmsPermission> permissions;

}
