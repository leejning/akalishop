package com.akali.common.dto.admin;

import lombok.Data;

import java.util.List;

/**
 * @ClassName AdminLoginInfoDTO
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/30 0030
 * @Version V1.0
 **/
@Data
public class AdminLoginInfoDTO {
    String name;
    String avatar;
    List<String> roles;
    Long roleId;
}
