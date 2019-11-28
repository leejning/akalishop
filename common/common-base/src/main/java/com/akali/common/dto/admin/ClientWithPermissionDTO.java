package com.akali.common.dto.admin;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName ClientWithPermissionDTO
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/26 0026
 * @Version V1.0
 **/
@Data
public class ClientWithPermissionDTO implements Serializable {
    private static final long serialVersionUID = 6148938658754829880L;

    private Long clientId;

    private String clientName;

    private List<PermissionDTO> permissions;

}
