package com.frodo.app.framework.orm.table;

import com.frodo.app.framework.exception.DbException;
import com.frodo.app.framework.orm.Cursor;
import com.frodo.app.framework.orm.converter.ColumnConverter;
import com.frodo.app.framework.orm.converter.ColumnConverterFactory;
import com.frodo.app.framework.orm.sql.ColumnDbType;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Column {

    protected final String columnName;
    protected final Method getMethod;
    protected final Method setMethod;
    protected final Field columnField;
    protected final ColumnConverter columnConverter;
    private final Object defaultValue;
    private com.frodo.app.framework.orm.table.Table table;
    private int index = -1;

    /* package */ Column(Class<?> entityType, Field field) throws DbException {
        this.columnField = field;
        this.columnConverter = ColumnConverterFactory.getColumnConverter(field.getType());
        this.columnName = ColumnUtils.getColumnNameByField(field);
        if (this.columnConverter != null) {
            this.defaultValue = this.columnConverter.getFieldValue(ColumnUtils.getColumnDefaultValue(field));
        } else {
            this.defaultValue = null;
        }
        this.getMethod = ColumnUtils.getColumnGetMethod(entityType, field);
        this.setMethod = ColumnUtils.getColumnSetMethod(entityType, field);
    }

    @SuppressWarnings("unchecked")
    public void setValue2Entity(Object entity, Cursor cursor, int index) throws DbException {
        this.index = index;
        Object value = columnConverter.getFieldValue(cursor, index);
        if (value == null && defaultValue == null) return;

        if (setMethod != null) {
            try {
                setMethod.invoke(entity, value == null ? defaultValue : value);
            } catch (Throwable e) {
                throw new DbException(e);
            }
        } else {
            try {
                this.columnField.setAccessible(true);
                this.columnField.set(entity, value == null ? defaultValue : value);
            } catch (Throwable e) {
                throw new DbException(e);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public Object getColumnValue(Object entity) throws DbException {
        Object fieldValue = getFieldValue(entity);
        return columnConverter.fieldValue2ColumnValue(fieldValue);
    }

    public Object getFieldValue(Object entity) throws DbException {
        Object fieldValue = null;
        if (entity != null) {
            if (getMethod != null) {
                try {
                    fieldValue = getMethod.invoke(entity);
                } catch (Throwable e) {
                    throw new DbException(e);
                }
            } else {
                try {
                    this.columnField.setAccessible(true);
                    fieldValue = this.columnField.get(entity);
                } catch (Throwable e) {
                    throw new DbException(e);
                }
            }
        }
        return fieldValue;
    }

    public com.frodo.app.framework.orm.table.Table getTable() {
        return table;
    }

    /* package */ void setTable(com.frodo.app.framework.orm.table.Table table) {
        this.table = table;
    }

    /**
     * The value set in setValue2Entity(...)
     *
     * @return -1 or the index of this column.
     */
    public int getIndex() {
        return index;
    }

    public String getColumnName() {
        return columnName;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }

    public Field getColumnField() {
        return columnField;
    }

    public ColumnConverter getColumnConverter() {
        return columnConverter;
    }

    public ColumnDbType getColumnDbType() {
        return columnConverter.getColumnDbType();
    }
}
