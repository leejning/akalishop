package com.akali.business.member.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @ClassName AuthToken
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/9/27 0027
 * @Version V1.0
 **/
@Data
@ToString
@NoArgsConstructor
public class AuthToken {
    String jti;//访问token
    String refresh_token;//刷新token
    String jwt_token;//jwt令牌
}
