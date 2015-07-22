package com.frodo.android.app.core.database;

import com.frodo.android.app.framework.controller.AbstractChildSystem;
import com.frodo.android.app.framework.controller.IController;
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

}
