package com.frodo.app.framework.orm;

import com.frodo.app.framework.controller.ChildSystem;
import com.frodo.app.framework.exception.DbException;
import com.frodo.app.framework.orm.sql.DbModelSelector;
import com.frodo.app.framework.orm.sql.Selector;
import com.frodo.app.framework.orm.sql.SqlInfo;
import com.frodo.app.framework.orm.sql.WhereBuilder;
import com.frodo.app.framework.orm.table.DbModel;

import java.util.List;

/**
 * 数据库操作
 * Created by frodo on 2015/6/20.
 */
public interface Database extends ChildSystem {

    DaoConfig getDaoConfig();

    void saveOrUpdate(Object entity) throws DbException;

    void saveOrUpdateAll(List<?> entities) throws DbException;

    void replace(Object entity) throws DbException;

    void replaceAll(List<?> entities) throws DbException;

    void save(Object entity) throws DbException;

    void saveAll(List<?> entities) throws DbException;

    boolean saveBindingId(Object entity) throws DbException;

    void saveBindingIdAll(List<?> entities) throws DbException;

    void deleteById(Class<?> entityType, Object idValue) throws DbException;

    void delete(Object entity) throws DbException;

    void delete(Class<?> entityType, WhereBuilder whereBuilder) throws DbException;

    void deleteAll(List<?> entities) throws DbException;

    void deleteAll(Class<?> entityType) throws DbException;

    void update(Object entity, String... updateColumnNames) throws DbException;

    void update(Object entity, WhereBuilder whereBuilder, String... updateColumnNames) throws DbException;

    void updateAll(List<?> entities, String... updateColumnNames) throws DbException;

    void updateAll(List<?> entities, WhereBuilder whereBuilder, String... updateColumnNames) throws DbException;

    <T> T findById(Class<T> entityType, Object idValue) throws DbException;

    <T> T findFirst(Selector selector) throws DbException;

    <T> T findFirst(Class<T> entityType) throws DbException;

    <T> List<T> findAll(Selector selector) throws DbException;

    <T> List<T> findAll(Class<T> entityType) throws DbException;

    DbModel findDbModelFirst(SqlInfo sqlInfo) throws DbException;

    DbModel findDbModelFirst(DbModelSelector selector) throws DbException;

    List<DbModel> findDbModelAll(SqlInfo sqlInfo) throws DbException;

    List<DbModel> findDbModelAll(DbModelSelector selector) throws DbException;

    long count(Selector selector) throws DbException;

    long count(Class<?> entityType) throws DbException;

}
