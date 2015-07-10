package com.frodo.android.app.core.database;

import java.util.List;
import java.util.Map;

import com.frodo.android.app.framework.controller.AbstractChildSystem;
import com.frodo.android.app.framework.controller.IController;
import com.frodo.android.app.framework.orm.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * DB
 * Created by frodo on 2015/6/20.
 */
public class AndroidDatabaseSystem extends AbstractChildSystem implements Database {

    private Context context;
    private SQLiteOpenHelper sqLiteOpenHelper;

    public AndroidDatabaseSystem(IController controller, String db) {
        super(controller);
        this.context = (Context) controller.getContext();
        sqLiteOpenHelper = new DatabaseHelper(context, db);
    }

    @Override
    public long insert(Entity entity) {
        SQLiteDatabase sqliteDatabase = sqLiteOpenHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        for (Map.Entry<String, String> entry : entity.map().entrySet()) {
            values.put(entry.getKey(), entry.getValue());
        }
        return sqliteDatabase.insert(getTable(entity.getClass()), null, values);
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

    private String getTable(Class clazz) {
        return clazz.getName().toLowerCase();
    }

    private class DatabaseHelper extends SQLiteOpenHelper {

        private static final int VERSION = 1;

        public DatabaseHelper(Context context, String name) {
            this(context, name, VERSION);
        }

        public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        public DatabaseHelper(Context context, String name, int version) {
            this(context, name, null, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }
}
