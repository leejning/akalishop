package com.akali.provider.user.admin.service;

import com.akali.common.dto.admin.AdminLoginDTO;
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
     * 查询要登录的管理员用户
     * @param account
     * @return
     */
    DubboResponse<AdminLoginDTO> queryAdminUserWithPermissionByAccount(String account);
}
