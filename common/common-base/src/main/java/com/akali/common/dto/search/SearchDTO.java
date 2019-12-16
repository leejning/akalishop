package com.akali.common.dto.search;

import com.akali.common.dto.query.QueryPageBase;
import lombok.Data;

import java.util.Map;

/**
 * @ClassName SearchDTO
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/12/15 0015
 * @Version V1.0
 **/
@Data
public class SearchDTO extends QueryPageBase {

    String key;
    /**
     * 搜索属性过滤
     */
    Map<String,String> filter;
}
