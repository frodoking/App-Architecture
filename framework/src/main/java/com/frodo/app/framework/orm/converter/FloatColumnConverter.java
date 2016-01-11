package com.frodo.app.framework.orm.converter;

import com.frodo.app.framework.orm.Cursor;
import com.frodo.app.framework.orm.sql.ColumnDbType;
import com.frodo.app.framework.toolbox.TextUtils;

public class FloatColumnConverter implements com.frodo.app.framework.orm.converter.ColumnConverter<Float> {
    @Override
    public Float getFieldValue(final Cursor cursor, int index) {
        return cursor.isNull(index) ? null : cursor.getFloat(index);
    }

    @Override
    public Float getFieldValue(String fieldStringValue) {
        if (TextUtils.isEmpty(fieldStringValue)) return null;
        return Float.valueOf(fieldStringValue);
    }

    @Override
    public Object fieldValue2ColumnValue(Float fieldValue) {
        return fieldValue;
    }

    @Override
    public ColumnDbType getColumnDbType() {
        return ColumnDbType.REAL;
    }
}
