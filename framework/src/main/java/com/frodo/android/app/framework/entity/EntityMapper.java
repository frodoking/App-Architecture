package com.frodo.android.app.framework.entity;

import java.util.Collection;

/**
 * 为何和服务器的实体进行隔离，此处使用mapper做映射处理
 * Created by frodo on 2015/4/2.
 */
public interface EntityMapper<T, R> {
    R transform(T entity);

    Collection<R> transform(Collection<T> entityCollection);

    T transformJsonToEntity(String jsonString);

    Collection<T> transformJsonArrayToEntity(String jsonArrayString);

    String transformEntityToJsonString();
}
