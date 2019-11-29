package com.akali.business.member.profile.api;

import com.akali.common.dto.member.ReceiveAddressAddDTO;
import com.akali.common.dto.member.ReceiveAddressDTO;
import com.akali.common.model.response.QueryResponseResult;
import com.akali.common.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName ReceiveAddressControllerApi
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/28 0028
 * @Version V1.0
 **/
@Api(value = "会员用户收货地址管理接口")
public interface ReceiveAddressControllerApi {

    @ApiOperation("添加新收货地址")
    public ResponseResult<Void> addNewReceiveAddress(ReceiveAddressAddDTO receieAddressAddDTO, HttpServletRequest request);

    @ApiOperation("查询所有收货地址")
    public QueryResponseResult<ReceiveAddressDTO> queryReceiveAddress(HttpServletRequest request);

}
