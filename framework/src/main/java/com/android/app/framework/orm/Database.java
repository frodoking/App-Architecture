package com.android.app.framework.orm;

/**
 * 纯数据库的操作
 * Created by frodo on 2015/6/20.
 */
public interface Database {
    boolean insert(String sql, Object[] objects);

    boolean delete(String sql);

    boolean update(String sql, Object[] objects);

    Object[] query(String sql);
}
