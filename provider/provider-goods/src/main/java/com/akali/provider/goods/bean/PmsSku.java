package com.akali.provider.goods.bean;

import com.akali.common.dto.goods.sku.SkuCreateDTO;
import com.akali.common.utils.MapperUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.util.Date;

/**
 * @ClassName PmsSku
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/12 0012
 * @Version V1.0
 **/
@Entity
@Table(name = "pms_sku")
@Data
@NoArgsConstructor
public class PmsSku {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 所属spu
     */
    private Long spuId;
    /**
     * sku标题
     */
    private String title;
    /**
     * 主图片
     */
    @Column(length=200)
    private String mainImage;
    /**
     * sku图片
     */
    @Column(length=1600)
    private String images;
    /**
     * 价格
     */
    @Column(length=15)
    private Long price;
    /**
     * 销售选项下标,用于选择不同的销售选项组合时，标识唯一的sku
     */
    @Column(length=30)
    private String indexes;
    /**
     * sku特有属性，Map<Long,Long> 的json格式
     * {
     *     attrId : attrValueId,
     * }
     *
     */
    @Column(length=1000)
    private String ownSpec;
    /**
     * 可销售标志
     */
    @Column(columnDefinition="tinyint",length=2)
    private Boolean enable;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Date createTime;
    /**
     * 最后修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Date lastUpdateTime;


    public PmsSku(SkuCreateDTO object) {
        BeanUtils.copyProperties(object,this);
        this.createTime = new Date();
        this.ownSpec = MapperUtils.mapToJson(object.getOwnSpec());
        this.enable = false;
    }
}
