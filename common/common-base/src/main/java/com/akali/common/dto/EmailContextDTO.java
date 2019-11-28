package com.akali.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName EmailContextDTO
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/25 0025
 * @Version V1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailContextDTO implements Serializable {
    private static final long serialVersionUID = -4964644194051080329L;
    String email;
    String code;
}
