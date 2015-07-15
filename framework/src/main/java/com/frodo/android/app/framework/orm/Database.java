package com.frodo.android.app.framework.orm;

import java.util.List;
import java.util.Map;

import com.frodo.android.app.framework.controller.ChildSystem;

/**
 * 数据库操作
 * Created by frodo on 2015/6/20.
 */
public interface Database extends ChildSystem {
    long insert(Entity entity);

    long insertOrReplace(Entity entity);

    void refresh(Entity entity);

    void update(Entity entity);

    void delete(Entity entity);

    void deleteAll(Class entityClass);

    Entity load(Class entityClass, String key);

    List<Entity> loadAll(Class entityClass);

    interface Entity {
        Map<String, String> map();
    }
}
