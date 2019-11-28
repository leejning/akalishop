package com.akali.provider.user.admin.service;

import com.akali.common.code.AdminCode;
import com.akali.common.dto.admin.AdminLoginDTO;
import com.akali.common.model.response.DubboResponse;
import com.akali.provider.user.admin.bean.UmsAdminUser;
import com.akali.provider.user.admin.dao.AdminRoleDao;
import com.akali.provider.user.admin.dao.AdminUserDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @ClassName AdminUserServiceImpl
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/26 0026
 * @Version V1.0
 **/
@Service(version = "1.0.0")
@Slf4j
public class AdminUserServiceImpl implements AdminUserService {
    @Autowired
    private AdminUserDao adminUserDao;
    @Autowired
    private AdminRoleDao adminRoleDao;


    /**
     * 查询管理员用户的所有权限
     *
     * @param account
     * @return
     */
    @Override
    public DubboResponse<AdminLoginDTO> queryAdminUserWithPermissionByAccount(String account) {
        Optional<UmsAdminUser> opt = adminUserDao.findUserByAccountForLogin(account);
        if(!opt.isPresent()){
            DubboResponse.FAIL(AdminCode.HAS_NOT_ADMIN_PERMISSION);
        }
        UmsAdminUser user = opt.get();
        List<String> permissions = adminRoleDao.findById(user.getRoleId()).get().getPermissions().stream()
                .map(p -> p.getAuthoritySign()).collect(Collectors.toList());

        log.info(">>>>>>>查询登录管理员:{}的权限列表成功,权限{}",permissions.toString());

        AdminLoginDTO result = new AdminLoginDTO();
        result.setId(user.getId());
        result.setPassword(user.getPassword());
        result.setPermissions(permissions);
        return DubboResponse.SUCCESS(result);
    }
}
