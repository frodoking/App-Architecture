package com.frodo.app.framework.orm.converter;

import com.frodo.app.framework.orm.Cursor;
import com.frodo.app.framework.orm.sql.ColumnDbType;
import com.frodo.app.framework.toolbox.TextUtils;

public class LongColumnConverter implements com.frodo.app.framework.orm.converter.ColumnConverter<Long> {
    @Override
    public Long getFieldValue(final Cursor cursor, int index) {
        return cursor.isNull(index) ? null : cursor.getLong(index);
    }

    @Override
    public Long getFieldValue(String fieldStringValue) {
        if (TextUtils.isEmpty(fieldStringValue)) return null;
        return Long.valueOf(fieldStringValue);
    }

    @Override
    public Object fieldValue2ColumnValue(Long fieldValue) {
        return fieldValue;
    }

    @Override
    public ColumnDbType getColumnDbType() {
        return ColumnDbType.INTEGER;
    }
}
