package com.akali.provider.goods.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @ClassName PmsBaseAttrValue
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/12 0012
 * @Version V1.0
 **/
@Entity
@Table(name = "pms_base_attr_value")
@Data
@NoArgsConstructor
public class PmsBaseAttrValue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 属性id
     */
    private Long attrId;
    /**
     * 商品id
     */
    private Long spuId;
    /**
     * 属性值
     */
    @Column(length=32)
    private String value;
    /**
     * 选项型属性值 对应选项的id
     */
    private Long optionId;

    public PmsBaseAttrValue(Long attrId, Long spuId, String value) {
        this.attrId = attrId;
        this.spuId = spuId;
        this.value = value;
    }
}
