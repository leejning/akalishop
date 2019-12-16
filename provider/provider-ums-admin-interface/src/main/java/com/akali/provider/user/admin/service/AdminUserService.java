package com.akali.provider.user.admin.service;

import com.akali.common.dto.admin.AdminLoginDTO;
import com.akali.common.dto.admin.AdminLoginInfoDTO;
import com.akali.common.model.response.DubboResponse;

/**
 * @ClassName AdminUserService
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/26 0026
 * @Version V1.0
 **/
public interface AdminUserService {

    /**
     * 根据账号查询要登录的管理员密码
     * @param account
     * @return
     */
    DubboResponse<AdminLoginDTO> queryAdminUserWithPermissionByAccount(String account);

    /**
     * 检查管理员账号是否存在
     * @param adminAccount
     * @return
     */
    DubboResponse<Void> checkExistByAdminAccount(String adminAccount);

    /**
     * 获取管理员的登录信息，名称、头像、角色
     * @param adminAccount
     * @return
     */
    DubboResponse<AdminLoginInfoDTO> getAdminLoginProfile(String adminAccount);
}
