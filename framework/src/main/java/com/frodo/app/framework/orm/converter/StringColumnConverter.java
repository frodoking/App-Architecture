package com.frodo.app.framework.orm.converter;

import com.frodo.app.framework.orm.Cursor;
import com.frodo.app.framework.orm.sql.ColumnDbType;

public class StringColumnConverter implements com.frodo.app.framework.orm.converter.ColumnConverter<String> {
    @Override
    public String getFieldValue(final Cursor cursor, int index) {
        return cursor.isNull(index) ? null : cursor.getString(index);
    }

    @Override
    public String getFieldValue(String fieldStringValue) {
        return fieldStringValue;
    }

    @Override
    public Object fieldValue2ColumnValue(String fieldValue) {
        return fieldValue;
    }

    @Override
    public ColumnDbType getColumnDbType() {
        return ColumnDbType.TEXT;
    }
}
