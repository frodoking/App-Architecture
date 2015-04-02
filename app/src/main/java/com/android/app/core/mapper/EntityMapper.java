package com.android.app.core.mapper;

/**
 * 为何和服务器的实体进行隔离，此处使用mapper做映射处理
 * Created by frodo on 2015/4/2.
 */
public abstract class EntityMapper<T, R> {
    public abstract R map(T entity);
}

