package com.akali.provider.user.admin.dao;

import com.akali.provider.user.admin.bean.AmsPermission;
import org.springframework.data.repository.CrudRepository;

/**
 * @ClassName PermissionDao
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/26 0026
 * @Version V1.0
 **/
public interface PermissionDao extends CrudRepository<AmsPermission,Long> {
}
