package com.akali.common.model.response;

import com.akali.common.code.CommonCode;
import com.akali.common.code.ResultCode;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class QueryResponseResult<T> extends ResponseResult<T> implements Serializable {
    private static final long serialVersionUID = 139682029789447006L;
    QueryResult<T> queryResult;

    public QueryResponseResult(ResultCode resultCode, QueryResult<T> queryResult) {
        super(resultCode);
        this.queryResult = queryResult;
    }

    public static <T> QueryResponseResult<T> SUCCESS(QueryResult<T> queryResult) {
        return new QueryResponseResult<>(CommonCode.SUCCESS, queryResult);
    }

}
