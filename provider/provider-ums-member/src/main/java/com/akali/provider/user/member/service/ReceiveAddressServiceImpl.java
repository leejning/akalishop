package com.akali.provider.user.member.service;

import com.akali.common.code.CommonCode;
import com.akali.common.dto.member.ReceiveAddressAddDTO;
import com.akali.common.dto.member.ReceiveAddressDTO;
import com.akali.common.model.response.DubboResponse;
import com.akali.common.model.response.QueryResult;
import com.akali.provider.user.member.bean.UmsMemberReceiveAddress;
import com.akali.provider.user.member.dao.ReceiveAddressDao;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @ClassName ReceiveAddressServiceImpl
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/28 0028
 * @Version V1.0
 **/
@Service(version = "1.0.0")
public class ReceiveAddressServiceImpl implements ReceiveAddressService{
    @Autowired
    private ReceiveAddressDao receiveAddressDao;


    /**
     * 查询会员用户的收货地址
     *
     * @param memberId
     * @return
     */
    @Override
    public DubboResponse<QueryResult<ReceiveAddressDTO>> queryReceiveAddress(Long memberId) {
        List<ReceiveAddressDTO> data = receiveAddressDao.findByMemberId(memberId);
        return DubboResponse.SUCCESS(QueryResult.create(data, (long) data.size()));
    }

    /**
     * 添加新的收货地址
     *
     * @param memberId
     * @param receiveAddressAddDTO
     * @return
     */
    @Override
    public DubboResponse<Void> addNewReceiveAddress(Long memberId, ReceiveAddressAddDTO receiveAddressAddDTO) {
        UmsMemberReceiveAddress address = new UmsMemberReceiveAddress();
        BeanUtils.copyProperties(receiveAddressAddDTO,address);
        address.setMemberId(memberId);
        receiveAddressDao.save(address);
        return DubboResponse.SUCCESS(CommonCode.SUCCESS);
    }
}
