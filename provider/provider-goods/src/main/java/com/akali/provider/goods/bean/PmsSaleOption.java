package com.akali.provider.goods.bean;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;

/**
 * @ClassName PmsSaleOption
 * @Description: TODO 销售商品的用户可选项，用于决定某个sku
 * @Author Administrator
 * @Date 2019/11/12 0012
 * @Version V1.0
 **/
@Entity
@Table(name = "pms_base_sale_option")
@Data
@NoArgsConstructor
public class PmsSaleOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 三级分类id
     */
    private Long cateId;

    /**
     * 选项名
     */
    @Column(length = 20)
    private String name;

    public PmsSaleOption(Object object) {
        BeanUtils.copyProperties(object,this);
        this.id = null;
    }
}
