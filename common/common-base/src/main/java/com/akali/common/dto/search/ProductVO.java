package com.akali.common.dto.search;

import com.akali.common.dto.goods.sku.SkuEsVO;
import com.akali.common.utils.MapperUtils;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName ProductVO
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/24 0024
 * @Version V1.0
 **/
@Data
@NoArgsConstructor
public class ProductVO implements Serializable {

    private static final long serialVersionUID = -8431753836826888542L;
    private Long id;
    /**
     *
     */
    private String title;
    /**
     * 卖点
     */
    private String subTitle;
    /**
     * 分类全名
     */
    private String categoryFullName;
    private List<SkuEsVO> skus;

    public ProductVO(Object product,String jsonSkus) {
        BeanUtils.copyProperties(product,this);
        try {
            this.skus = MapperUtils.json2list(jsonSkus, SkuEsVO.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
