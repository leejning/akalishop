package com.akali.common.data_help;


import com.akali.common.dto.query.QueryPageBase;
import com.akali.common.model.response.QueryResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * @ClassName HIbernatePageUtils
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/9/30 0030
 * @Version V1.0
 **/
public class DataJpaPageUtils {
    static final Integer defaultPageNo = 1;
    static final Integer defaultPageSize = 20;


    public static PageAndSortObj initPageAndSort(QueryPageBase queryPageBase) {
        PageAndSortObj pageAndSortObj = new PageAndSortObj();
        Integer pageNo = queryPageBase.getPageNo();
        Integer pageSize = queryPageBase.getPageSize();
        if ( pageNo== null ||pageNo < 1) {
            queryPageBase.setPageNo(defaultPageNo);
            pageNo = defaultPageNo;
        }
        if (pageSize == null || pageSize < 1) {
            queryPageBase.setPageSize(defaultPageSize);
            pageSize = defaultPageSize;
        }
        String sortby = queryPageBase.getSortby();
        if ( sortby == null || sortby == "" || sortby.isEmpty()) {
            queryPageBase.setSortby("id");
            sortby = queryPageBase.getSortby();
        }
        if (!StringUtils.isEmpty(queryPageBase.getDescOrAsc()) && "asc".equals(queryPageBase.getDescOrAsc())) {
            pageAndSortObj.setSort(Sort.by(Sort.Direction.ASC, sortby)) ;
            queryPageBase.setDescOrAsc("asc");
        } else {
            pageAndSortObj.setSort(Sort.by(Sort.Direction.DESC, sortby));
            queryPageBase.setDescOrAsc("desc");
        }
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, pageAndSortObj.getSort());
        pageAndSortObj.setPageable(pageable);
        return pageAndSortObj;
    }

    public static<T> QueryResult<T> setQueryResult(Page<T> data, QueryPageBase queryPageBase) {
        QueryResult<T> queryResult = new QueryResult();
        BeanUtils.copyProperties(queryPageBase,queryResult);
        queryResult.setList(data.getContent());
        queryResult.setTotal(data.getTotalElements());
        return queryResult;
    }
    public static<T> QueryResult<T> setQueryResult(List<T> data, Long total, QueryPageBase queryPageBase) {
        QueryResult<T> queryResult = new QueryResult();
        BeanUtils.copyProperties(queryPageBase,queryResult);
        queryResult.setList(data);
        queryResult.setTotal(total);
        return queryResult;
    }
}
