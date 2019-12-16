package com.akali.config.jpa;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import javax.persistence.criteria.ParameterExpression;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ExtendedSpecificationAdapter
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/12/9 0009
 * @Version V1.0
 **/
public abstract class ExtendedSpecificationAdapter<T> implements ExtendedSpecification<T> {
    Map<String, ParameterExpression<Collection<?>>> ipm;
    Map<String,Collection<?>> ipvm;
    BaseEntityQueryHelper queryHelper;

    public void setQueryHelper(BaseEntityQueryHelper qeryHelper){
        this.queryHelper = qeryHelper;
    }

    public BaseEntityQueryHelper getQueryHelper(){
        return queryHelper;
    }

    @Override
    public Map<String, ParameterExpression<Collection<?>>> getInParameterMap() {
        if(ipm==null){
            ipm = Maps.newHashMap();
        }
        return ipm;
    }
    @Override
    public Map<String, Collection<?>> getInParameterValueMap() {
        if(ipvm==null){
            ipvm = Maps.newHashMap();
        }
        return ipvm;
    }
    @Override
    public boolean hasInCondition() {
        return queryHelper.isWithIn();
    }

    @Override
    public List<String> excludeName() {
        return Lists.newArrayList("values");
    }

    @Override
    public Class getResultClass() {
        return queryHelper.getResultClass();
    }
}
