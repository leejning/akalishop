package com.akali.provider.user.admin.service;

import com.akali.provider.user.admin.dao.PermissionDao;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName PermissionServiceImpl
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/26 0026
 * @Version V1.0
 **/
@Service(version = "1.0.0")
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionDao permissionDao;



}
