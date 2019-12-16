package com.akali.provider.user.admin.service;

import com.akali.common.code.AdminCode;
import com.akali.common.code.CommonCode;
import com.akali.common.dto.admin.AdminLoginDTO;
import com.akali.common.dto.admin.AdminLoginInfoDTO;
import com.akali.common.model.response.DubboResponse;
import com.akali.provider.user.admin.bean.UmsAdminRole;
import com.akali.provider.user.admin.bean.UmsAdminUser;
import com.akali.provider.user.admin.dao.AdminRoleDao;
import com.akali.provider.user.admin.dao.AdminUserDao;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
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
        Optional<UmsAdminUser> opt = adminUserDao.findPasswordByAccountForLogin(account);
        if(!opt.isPresent()){
            DubboResponse.FAIL(AdminCode.HAS_NOT_ADMIN_PERMISSION);
        }
        UmsAdminUser user = opt.get();
        List<String> permissions = adminRoleDao.findById(user.getRoleId()).get().getPermissions().stream()
                .map(p -> p.getAuthoritySign()).collect(Collectors.toList());

        log.info(">>>>>>>查询登录管理员:{}的权限列表成功,权限{}",permissions.toString());

        AdminLoginDTO result = new AdminLoginDTO();
        BeanUtils.copyProperties(user,result);
        result.setPermissions(permissions);
        return DubboResponse.SUCCESS(result);
    }

    /**
     * 检查管理员账号是否存在
     *
     * @param adminAccount
     * @return
     */
    @Override
    public DubboResponse<Void> checkExistByAdminAccount(String adminAccount) {
        Integer cnt = adminUserDao.checkExistByMemberAccount(adminAccount);
        if(cnt==null||cnt!=1){
            return DubboResponse.FAIL(AdminCode.ADMIN_ACCOUNT_NOT_EXIST);
        }
        return DubboResponse.SUCCESS(CommonCode.SUCCESS);
    }

    /**
     * 获取管理员的登录信息，名称、头像、角色
     *
     * @param adminAccount
     * @return
     */
    @Override
    public DubboResponse<AdminLoginInfoDTO> getAdminLoginProfile(String adminAccount) {
        Optional<UmsAdminUser> opt = adminUserDao.findAdminLoginProfile(adminAccount);
        if (!opt.isPresent()) {
            return DubboResponse.FAIL(AdminCode.ADMIN_ACCOUNT_NOT_EXIST);
        }
        UmsAdminUser user = opt.get();
        UmsAdminRole role = adminRoleDao.findById(user.getId()).get();
        AdminLoginInfoDTO adminLoginInfoDTO = new AdminLoginInfoDTO();
        adminLoginInfoDTO.setName(user.getAdminName());
        adminLoginInfoDTO.setAvatar(user.getAvatar());
        adminLoginInfoDTO.setRoles(Lists.newArrayList(role.getRoleName()));
        adminLoginInfoDTO.setRoleId(role.getId());
        return DubboResponse.SUCCESS(adminLoginInfoDTO);
    }
}
