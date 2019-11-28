package com.akali.config.member.oauth2;

import com.akali.common.code.AuthCode;
import com.akali.common.utils.ExceptionCast;
import com.akali.common.utils.MapperUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


/**
 * @ClassName Oauth2JwtUtil
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/9/28 0028
 * @Version V1.0.0
 **/
public class Oauth2JwtUtil {
    public static Long getLoginMemberId(HttpServletRequest request){
        Map<String, Object> jwtClaimsFromHeader = getJwtClaimsFromHeader(request);
        String memberId = (String) jwtClaimsFromHeader.get("member_id").toString();
        return new Long(memberId);
    }

    public static Map<String, Object> getJwtClaimsFromHeader(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        //取出头信息
        String authorization = request.getHeader("Authorization");
        if (StringUtils.isEmpty(authorization) || authorization.indexOf("Bearer") < 0) {
            return null;
        }
        //从Bearer 后边开始取出token
        String token = authorization.substring(7);
        Map<String, Object> map = null;
        try {
            //解析jwt
            Jwt decode = JwtHelper.decode(token);
            //得到 jwt中的用户信息
            String claims = decode.getClaims();
            //将jwt转为Map

            map = MapperUtils.json2map(claims);
        } catch (Exception e) {
            ExceptionCast.cast(AuthCode.AUTH_TOKEN_PASER_ERROR);
        }
        return map;
    }

    public static Boolean checkAuthorization(String authorization){
        if (StringUtils.isEmpty(authorization) || authorization.indexOf("Bearer") < 0) {
            return null;
        }
        String token = authorization.substring(7);
        try {
            //解析jwt
            Jwt decode = JwtHelper.decode(token);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
