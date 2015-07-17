package com.frodo.android.app.core.database;

import java.util.List;

import com.frodo.android.app.framework.controller.AbstractChildSystem;
import com.frodo.android.app.framework.controller.IController;
import com.frodo.android.app.framework.entity.Entity;
import com.frodo.android.app.framework.orm.Database;

import android.content.Context;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * DB
 * Created by frodo on 2015/6/20.
 */
public class AndroidDatabaseSystem extends AbstractChildSystem implements Database {

    private Context context;
    private Realm realm;

    public AndroidDatabaseSystem(IController controller, String db) {
        super(controller);
        this.context = (Context) controller.getContext();

        RealmConfiguration realmConfig = new RealmConfiguration.Builder(this.context).build();
        Realm.deleteRealm(realmConfig);
        realm = Realm.getInstance(realmConfig);
    }

    @Override
    public <E extends Entity> E createObject(Class<E> clazz) {
        Class<? extends RealmEntity> object = (Class<? extends RealmEntity>) clazz;
        return (E) realm.createObject(object);
    }

    @Override
    public long insert(Entity entity) {
        return 1;
    }

    @Override
    public long insertOrReplace(Entity entity) {
        return 0;
    }

    @Override
    public void refresh(Entity entity) {

    }

    @Override
    public void update(Entity entity) {
    }

    @Override
    public void delete(Entity entity) {

    }

    @Override
    public void deleteAll(Class entityClass) {

    }

    @Override
    public Entity load(Class entityClass, String key) {
        return null;
    }

    @Override
    public List<Entity> loadAll(Class entityClass) {
        return null;
    }

    @Override
    public void close() {
        realm.close();
    }

    private String getTable(Class clazz) {
        return clazz.getName().toLowerCase();
    }

}
