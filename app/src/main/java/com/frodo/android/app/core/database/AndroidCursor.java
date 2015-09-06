package com.frodo.android.app.core.database;

import com.frodo.android.app.framework.orm.Cursor;

/**
 * Created by frodo on 2015/9/6.
 */
public class AndroidCursor implements Cursor {
   private android.database.Cursor cursor;

    public AndroidCursor(android.database.Cursor cursor) {
        this.cursor = cursor;
    }

    public android.database.Cursor getCursor() {
        return cursor;
    }

    @Override
    public boolean isNull(int index) {
        return cursor.isNull(index);
    }

    @Override
    public int getInt(int index) {
        return cursor.getInt(index);
    }

    @Override
    public int getColumnIndex(String idColumnName) {
        return cursor.getColumnIndex(idColumnName);
    }

    @Override
    public int getColumnCount() {
        return cursor.getColumnCount();
    }

    @Override
    public String getColumnName(int i) {
        return cursor.getColumnName(i);
    }

    @Override
    public String getString(int i) {
        return cursor.getString(i);
    }

    @Override
    public byte[] getBlob(int index) {
        return cursor.getBlob(index);
    }

    @Override
    public long getLong(int index) {
        return cursor.getLong(index);
    }

    @Override
    public Double getDouble(int index) {
        return cursor.getDouble(index);
    }

    @Override
    public Float getFloat(int index) {
        return cursor.getFloat(index);
    }

    @Override
    public Short getShort(int index) {
        return cursor.getShort(index);
    }
}
