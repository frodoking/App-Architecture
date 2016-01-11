package com.frodo.app.framework.orm.converter;

import com.frodo.app.framework.orm.Cursor;
import com.frodo.app.framework.orm.sql.ColumnDbType;
import com.frodo.app.framework.toolbox.TextUtils;

public class IntegerColumnConverter implements com.frodo.app.framework.orm.converter.ColumnConverter<Integer> {
    @Override
    public Integer getFieldValue(final Cursor cursor, int index) {
        return cursor.isNull(index) ? null : cursor.getInt(index);
    }

    @Override
    public Integer getFieldValue(String fieldStringValue) {
        if (TextUtils.isEmpty(fieldStringValue)) return null;
        return Integer.valueOf(fieldStringValue);
    }

    @Override
    public Object fieldValue2ColumnValue(Integer fieldValue) {
        return fieldValue;
    }

    @Override
    public ColumnDbType getColumnDbType() {
        return ColumnDbType.INTEGER;
    }
}
