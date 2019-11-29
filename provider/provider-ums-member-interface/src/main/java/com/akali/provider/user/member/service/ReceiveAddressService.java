package com.akali.provider.user.member.service;

import com.akali.common.dto.member.ReceiveAddressAddDTO;
import com.akali.common.dto.member.ReceiveAddressDTO;
import com.akali.common.model.response.DubboResponse;
import com.akali.common.model.response.QueryResult;

/**
 * @ClassName ReceiveAddressService
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/28 0028
 * @Version V1.0
 **/
public interface ReceiveAddressService {
    /**
     * 查询会员用户的收货地址
     * @param memberId
     * @return
     */
    DubboResponse<QueryResult<ReceiveAddressDTO>> queryReceiveAddress(Long memberId);

    /**
     * 添加新的收货地址
     * @param memberId
     * @param receiveAddressAddDTO
     * @return
     */
    DubboResponse<Void> addNewReceiveAddress(Long memberId, ReceiveAddressAddDTO receiveAddressAddDTO);
}
