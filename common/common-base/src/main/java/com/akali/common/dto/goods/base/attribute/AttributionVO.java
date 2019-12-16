package com.akali.common.dto.goods.base.attribute;

import com.google.common.collect.Lists;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @ClassName AttributionVO
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/12/5 0005
 * @Version V1.0
 **/
@Data
public class AttributionVO implements Serializable {
    private static final long serialVersionUID = 2647754131148415574L;
    private Long id;
    private Long cateId;
    /**
     * 参数所属分组
     */
    private Long groupId;
    /**
     * 参数名
     */
    private String name;
    /**
     * 是否是数值类型 true是，false不是
     */
    private Boolean numeric;
    /**
     * 数组类型的单位
     */
    private String unit;

    /**
     * 是否是通用属性，true是则属于spu，false不是则属于sku
     */
    private Boolean generic;
    /**
     * 是否参与搜索
     */
    private Boolean searching;
    /**
     * 是否是固定选项，true是，false不是
     */
    private Boolean hasOptions;
    /**
     * 属性英文名
     */
    private String enName;

    /**
     * 搜索分片
     */
    private List<String> segments;

    public void setSegments(String segments) {
        if (StringUtils.isBlank(segments)) {
            this.segments = Collections.emptyList();
        } else {
            this.segments = Arrays.asList(segments.split(","));
        }
    }

    private List<AttrOptionVO> options = Lists.newArrayList();


}
