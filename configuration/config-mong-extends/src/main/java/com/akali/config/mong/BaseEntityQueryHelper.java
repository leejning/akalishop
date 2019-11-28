package com.akali.config.mong;

import com.google.common.collect.Lists;
import com.mongodb.BasicDBObject;
import com.mongodb.QueryBuilder;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Query;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName BaseEntityQueryHelper
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/28 0028
 * @Version V1.0
 **/
public interface BaseEntityQueryHelper {

    public static Query getWhere(BaseEntityQueryHelper queryHelper) {
        //定义条件匹配器
        QueryBuilder queryBuilder = new QueryBuilder();
        //设置条件值
        queryHelper.filter(queryHelper, queryBuilder);
        return new BasicQuery(queryBuilder.get().toString());
    }

    default void filter(BaseEntityQueryHelper queryHelper, QueryBuilder queryBuilder) {

    }

    default Query getWhere(BaseEntityQueryHelper queryHelper, BasicDBObject fields) {
        //定义条件匹配器
        QueryBuilder queryBuilder = new QueryBuilder();
        //设置条件值
        queryHelper.filter(queryHelper, queryBuilder);
        return new BasicQuery(queryBuilder.get().toString(), fields.toJson());
    }

    default BasicDBObject resultList(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        List<String> fieldNames = Lists.newArrayList(fields).stream()
                .map(f -> f.getName()).collect(Collectors.toList());
        BasicDBObject feild = new BasicDBObject();
        fieldNames.stream().forEach(n->feild.append(n,true));
        return feild;
    }

}
