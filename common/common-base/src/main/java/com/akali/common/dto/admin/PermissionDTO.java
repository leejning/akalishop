package com.akali.common.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName PermissionDTO
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/26 0026
 * @Version V1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PermissionDTO implements Serializable {
    private static final long serialVersionUID = 2073946936849050859L;
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
