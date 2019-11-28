package com.akali.common.dto.member;

import lombok.Data;

/**
 * @ClassName MemberLoginRequestDTO
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/27 0027
 * @Version V1.0
 **/
@Data
public class MemberLoginRequestDTO {
    String memberAccount;
    String password;
}
