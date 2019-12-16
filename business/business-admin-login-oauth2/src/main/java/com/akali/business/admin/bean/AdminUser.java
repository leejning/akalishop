package com.akali.business.admin.bean;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * @ClassName AdminUser
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/30 0030
 * @Version V1.0
 **/
@Data
public class AdminUser extends User {
    private String avatar;
    private String adminName;
    public AdminUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }


}
