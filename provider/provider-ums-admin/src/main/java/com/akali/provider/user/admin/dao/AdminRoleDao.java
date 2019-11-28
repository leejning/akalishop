package com.akali.provider.user.admin.dao;

import com.akali.provider.user.admin.bean.UmsAdminRole;
import org.springframework.data.repository.CrudRepository;

/**
 * @ClassName AdminRoleDao
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/26 0026
 * @Version V1.0
 **/
public interface AdminRoleDao extends CrudRepository<UmsAdminRole,Long> {
}
