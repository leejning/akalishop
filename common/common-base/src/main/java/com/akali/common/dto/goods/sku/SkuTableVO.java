package com.akali.common.dto.goods.sku;

import com.akali.common.utils.MapperUtils;
import lombok.Data;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

/**
 * @ClassName SkuTableVO
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/12/8 0008
 * @Version V1.0
 **/
@Data
public class SkuTableVO implements Serializable {
    private static final long serialVersionUID = -5900628797965019737L;
    private Long id;
    private Long spuId;
    private Long price;
    private String title;
    /**
     * key:属性 enName
     * value: 属性值
     */
    private Map<String,String> props;
    /**
     * key:属性id
     * value:属性值id
     */
    private Map<Long,Long> ownSpec;

    public void setOwnSpec(String ownSpec)throws IOException {
        this.ownSpec = MapperUtils.json2mapLong(ownSpec);
    }
}
