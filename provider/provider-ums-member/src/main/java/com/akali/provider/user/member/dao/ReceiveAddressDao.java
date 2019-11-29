package com.akali.provider.user.member.dao;

import com.akali.common.dto.member.ReceiveAddressDTO;
import com.akali.provider.user.member.bean.UmsMemberReceiveAddress;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @ClassName ReceiveAddressDao
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/28 0028
 * @Version V1.0
 **/
public interface ReceiveAddressDao extends CrudRepository<UmsMemberReceiveAddress,Long> {
    @Query("select new com.akali.common.dto.member.ReceiveAddressDTO(a) " +
            "from UmsMemberReceiveAddress a where a.memberId = ?1")
    List<ReceiveAddressDTO> findByMemberId(Long memberId);
}
