package com.akali.business.member.profile.web;

import com.akali.business.member.profile.api.ReceiveAddressControllerApi;
import com.akali.common.dto.member.ReceiveAddressAddDTO;
import com.akali.common.dto.member.ReceiveAddressDTO;
import com.akali.common.model.response.DubboResponse;
import com.akali.common.model.response.QueryResponseResult;
import com.akali.common.model.response.QueryResult;
import com.akali.common.model.response.ResponseResult;
import com.akali.common.utils.ExceptionCast;
import com.akali.config.member.oauth2.Oauth2JwtUtil;
import com.akali.provider.user.member.service.ReceiveAddressService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName ReceiveAddressController
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/28 0028
 * @Version V1.0
 **/
@RestController
@RequestMapping("member/address")
public class ReceiveAddressController implements ReceiveAddressControllerApi {

    @Reference(version = "1.0.0")
    private ReceiveAddressService addressService;


    @PostMapping
    @Override
    public ResponseResult<Void> addNewReceiveAddress(@RequestBody ReceiveAddressAddDTO receiveAddressAddDTO, HttpServletRequest request) {
        Long memberId = Oauth2JwtUtil.getLoginMemberId(request);
        DubboResponse<Void> response = addressService.addNewReceiveAddress(memberId,receiveAddressAddDTO);
        if (!response.isSuccess()) {
            ExceptionCast.cast(response.getResultCode());
        }
        return ResponseResult.SUCCESS().message("添加新收货地址成功");
    }

    /**
     * 查询会员用户的收货地址
     * @param request
     * @return
     */
    @GetMapping
    @Override
    public QueryResponseResult<ReceiveAddressDTO> queryReceiveAddress(HttpServletRequest request) {
        Long memberId = Oauth2JwtUtil.getLoginMemberId(request);
        DubboResponse<QueryResult<ReceiveAddressDTO>> response = addressService.queryReceiveAddress(memberId);
        if (!response.isSuccess()) {
            ExceptionCast.cast(response.getResultCode());
        }
        return QueryResponseResult.SUCCESS(response.getData()).message("获取会员用户的所有收货地址成功");
    }
}
