package com.akali.common.dto.admin;

import lombok.Data;

/**
 * @ClassName AdminTokenDTO
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/30 0030
 * @Version V1.0
 **/
@Data
public class AdminTokenDTO {
    private String refresh_token;
    private String access_token;

}
