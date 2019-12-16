package com.akali.provider.user.admin.dao;

import com.akali.provider.user.admin.bean.UmsAdminUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * @ClassName AdminUserDao
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/26 0026
 * @Version V1.0
 **/
public interface AdminUserDao extends CrudRepository<UmsAdminUser,Long> {
    @Query("select new UmsAdminUser(u.id,u.roleId,u.password,u.avatar,u.adminName) from UmsAdminUser u where u.adminAccount = ?1")
    Optional<UmsAdminUser> findPasswordByAccountForLogin(String account);

    @Query("select count(m.adminAccount) from UmsAdminUser m where m.adminAccount = ?1")
    Integer checkExistByMemberAccount(String adminAccount);

    @Query("select new UmsAdminUser(u.id,u.roleId,u.avatar,u.adminName) from UmsAdminUser u where u.adminAccount = ?1")
    Optional<UmsAdminUser> findAdminLoginProfile(String adminAccount);
}
