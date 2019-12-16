package com.akali.provider.user.member.dao;

import com.akali.config.jpa.ExtendedJpaRepositoryApi;
import com.akali.provider.user.member.bean.UmsMemberUser;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * @ClassName MemberDao
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/16 0016
 * @Version V1.0
 **/
public interface MemberDao extends ExtendedJpaRepositoryApi<UmsMemberUser,Long> {


    @Query("select new UmsMemberUser(m.id,m.memberAccount,m.username,m.password,m.icon) from UmsMemberUser m where m.memberAccount = ?1")
    Optional<UmsMemberUser> findMemberForLogin(String account);

    @Query("select count(m.memberAccount) from UmsMemberUser m where m.memberAccount = ?1")
    Integer checkExistByMemberAccount(String account);

    @Query("select count(m.email) from UmsMemberUser m where m.email = ?1")
    Integer checkEmailExist(String email);
}
