package com.akali.common.dto.admin;

import lombok.Data;

/**
 * @ClassName ClientDTO
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/26 0026
 * @Version V1.0
 **/
@Data
public class ClientDTO {
    private Long id;
    /**
     * 服务名
     */
    private String clientName;
}
