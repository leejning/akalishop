package com.akali.config.jpa;

import lombok.Data;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName BaseEntityQueryHelper
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/15 0015
 * @Version V1.0
 **/
@Data
public class BaseEntityQueryHelper {
    private String inField;
    private boolean withIn;
    private Collection<?> inValues = Collections.EMPTY_LIST;
    private Class<?> resultClass;

    protected static String[] initResultFields(Class<?> resultClass){
        List<String> fieldNames = Arrays.asList(resultClass.getFields()).stream()
                .map(f -> f.getName()).collect(Collectors.toList());
        return fieldNames.toArray(new String[fieldNames.size()]);
    }

}
