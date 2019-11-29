package com.akali.common.dto.member;

import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

/**
 * @ClassName ReceiveAddressDTO
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/28 0028
 * @Version V1.0
 **/
@Data
public class ReceiveAddressDTO implements Serializable {
    private static final long serialVersionUID = -8458700070832637753L;
    private Long id;
    /**
     * 收货人
     */
    private String receiver;
    /**
     * 电话
     */
    private String phone;
    /**
     * 省
     */
    private String provine;
    /**
     * 市
     */
    private String city;
    /**
     * 区
     */
    private String region;
    /**
     * 详细地址
     */
    private String detailAddress;

    public ReceiveAddressDTO(Object object) {
        BeanUtils.copyProperties(object,this);
    }
}
