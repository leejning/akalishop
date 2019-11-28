package com.akali.provider.user.member.dao;

import com.akali.config.jpa.ExtendedJpaRepositoryApi;
import com.akali.provider.user.member.bean.UmsMember;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * @ClassName MemberDao
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/16 0016
 * @Version V1.0
 **/
public interface MemberDao extends ExtendedJpaRepositoryApi<UmsMember,Long> {


    @Query("select new UmsMember(m.id,m.memberAccount,m.username,m.password,m.icon) from UmsMember m where m.memberAccount = ?1")
    Optional<UmsMember> findMemberForLogin(String account);

    @Query("select count(m.memberAccount) from UmsMember m where m.memberAccount = ?1")
    Integer checkExistByMemberAccount(String account);

    @Query("select count(m.email) from UmsMember m where m.email = ?1")
    Integer checkEmailExist(String email);
}
