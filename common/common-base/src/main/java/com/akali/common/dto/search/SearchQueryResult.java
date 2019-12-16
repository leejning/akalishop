package com.akali.common.dto.search;

import com.akali.common.dto.goods.base.brand.BrandSimpleVO;
import com.akali.common.dto.goods.base.category.CategorySimpleVO;
import com.akali.common.model.response.QueryResult;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @ClassName SearchQueryResult
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/12/15 0015
 * @Version V1.0
 **/
@Data
public class SearchQueryResult implements Serializable {
    private static final long serialVersionUID = 2539630812810116984L;
    QueryResult<ProductVO> queryResult;
    /**
     * 搜索属性过滤
     */
    Map<String,String> filter;
    List<CategorySimpleVO> categories;
    List<BrandSimpleVO> brands;
    List<Map<String, List<String>>> attribute;


}
