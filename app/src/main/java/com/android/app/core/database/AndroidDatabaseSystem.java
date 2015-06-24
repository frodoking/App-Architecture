package com.android.app.core.database;

import com.android.app.framework.controller.AbstractChildSystem;
import com.android.app.framework.controller.IController;
import com.android.app.framework.orm.Database;

import android.content.Context;

/**
 * DB
 * Created by frodo on 2015/6/20.
 */
public class AndroidDatabaseSystem extends AbstractChildSystem implements Database {

    private Context context;

    public AndroidDatabaseSystem(IController controller) {
        super(controller);
        this.context = (Context) controller.getContext();
    }

    @Override
    public boolean insert(String sql, Object[] objects) {
        return false;
    }

    @Override
    public boolean delete(String sql) {
        return false;
    }

    @Override
    public boolean update(String sql, Object[] objects) {
        return false;
    }

    @Override
    public Object[] query(String sql) {
        return new Object[0];
    }
}
