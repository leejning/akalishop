package com.akali.common.model.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;


@Data
@ToString
@NoArgsConstructor
public class QueryResult<T> implements Serializable {
    private static final long serialVersionUID = -7766371810558727989L;
    /**
     * 数据集合
     */
    private List<T> list;
    /**
     * 数据总数
     */
    private Long total;
    /**
     * 页码
     */
    private Integer pageNo;
    /**
     * 页数
     */
    private Integer pageSize;
    private String descOrAsc;
    private String sortby;

    QueryResult(List<T> list, Long total) {
        this.list = list;
        this.total = total;
    }

    public static<T> QueryResult<T> create(List<T> list, Long total){
        return new QueryResult(list,total);
    }

}
