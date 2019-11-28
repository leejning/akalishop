package com.akali.business.admin.config;

import com.akali.common.dto.admin.AdminLoginDTO;
import com.akali.common.model.response.DubboResponse;
import com.akali.provider.user.admin.service.AdminUserService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

/**
 * @ClassName Oauth2UserServiceImpl
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/26 0026
 * @Version V1.0
 **/
public class Oauth2UserServiceImpl implements UserDetailsService {
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "$2a$10$YaFb6AWld01TEEe88PTEzujJrXUp7bPYWZL0SnZWvlt3ofnWykPYi";
    private static final String USER_PERMISSION = "queryProductSkus,querySpuDetail,queryProductAllAttrValue";

    @Reference(version = "1.0.0")
    private AdminUserService adminUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        DubboResponse<AdminLoginDTO> response = adminUserService.queryAdminUserWithPermissionByAccount(username);
        if (response.isSuccess()) {
            AdminLoginDTO user = response.getData();
            List<String> permissions = user.getPermissions();
            List<GrantedAuthority> authorityList = AuthorityUtils.createAuthorityList(permissions.toArray(new String[permissions.size()]));
            return new User(username, user.getPassword(), authorityList);
        }
        return null;
    }
}
