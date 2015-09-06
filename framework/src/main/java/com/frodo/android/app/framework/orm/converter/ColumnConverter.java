package com.frodo.android.app.framework.orm.converter;


import com.frodo.android.app.framework.orm.Cursor;
import com.frodo.android.app.framework.orm.sql.ColumnDbType;

/**
 * Author: wyouflf
 * Date: 13-11-4
 * Time: 下午8:57
 */
public interface ColumnConverter<T> {

    T getFieldValue(final Cursor cursor, int index);

    T getFieldValue(String fieldStringValue);

    Object fieldValue2ColumnValue(T fieldValue);

    ColumnDbType getColumnDbType();
}
