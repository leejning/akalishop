package com.akali.provider.goods.bean;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;

/**
 * @ClassName PmsBrand
 * @Description: TODO 品牌
 * @Author Administrator
 * @Date 2019/11/11 0011
 * @Version V1.0
 **/
@Entity
@Table(name = "pms_brand")
@Data
@NoArgsConstructor
public class PmsBrand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 品牌名
     */
    @Column(length = 50)
    private String name;
    /**
     * logo
     */
    @Column(length = 200)
    private String logo;
    /**
     * 品牌首字母
     */
    @Column(length = 1)
    private String firstLetter;

    public PmsBrand(Object object) {
        BeanUtils.copyProperties(object,this);
    }
}
