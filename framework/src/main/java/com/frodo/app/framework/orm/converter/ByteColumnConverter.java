package com.frodo.app.framework.orm.converter;


import com.frodo.app.framework.orm.Cursor;
import com.frodo.app.framework.orm.sql.ColumnDbType;
import com.frodo.app.framework.toolbox.TextUtils;

/**
 * Author: wyouflf
 * Date: 13-11-4
 * Time: 下午10:51
 */
public class ByteColumnConverter implements com.frodo.app.framework.orm.converter.ColumnConverter<Byte> {
    @Override
    public Byte getFieldValue(final Cursor cursor, int index) {
        return cursor.isNull(index) ? null : (byte) cursor.getInt(index);
    }

    @Override
    public Byte getFieldValue(String fieldStringValue) {
        if (TextUtils.isEmpty(fieldStringValue)) return null;
        return Byte.valueOf(fieldStringValue);
    }

    @Override
    public Object fieldValue2ColumnValue(Byte fieldValue) {
        return fieldValue;
    }

    @Override
    public ColumnDbType getColumnDbType() {
        return ColumnDbType.INTEGER;
    }
}
