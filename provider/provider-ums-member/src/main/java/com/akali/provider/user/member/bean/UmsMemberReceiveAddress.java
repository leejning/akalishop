package com.akali.provider.user.member.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @ClassName UmsMemberReceiveAddress
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/28 0028
 * @Version V1.0
 **/
@Entity
@Table(name = "ums_member_receive_address")
@Data
@NoArgsConstructor
public class UmsMemberReceiveAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 会员id
     */
    @Column
    private Long memberId;
    /**
     * 收货人
     */
    @Column(length = 32)
    private String receiver;
    /**
     * 电话
     */
    @Column(length = 32)
    private String phone;
    /**
     * 省
     */
    @Column(length = 32)
    private String provine;
    /**
     * 市
     */
    @Column(length = 32)
    private String city;
    /**
     * 区
     */
    @Column(length = 32)
    private String region;
    /**
     * 详细地址
     */
    @Column(length = 255)
    private String detailAddress;


}
