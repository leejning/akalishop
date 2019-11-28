package com.akali.common.dto.admin;

import lombok.Data;

import java.util.List;

/**
 * @ClassName AdminLoginDTO
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/26 0026
 * @Version V1.0
 **/
@Data
public class AdminLoginDTO {
    private Long id;
    private String password;
    List<String> permissions;
}
