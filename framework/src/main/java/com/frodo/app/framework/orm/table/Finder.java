package com.frodo.app.framework.orm.table;

import com.frodo.app.framework.exception.DbException;
import com.frodo.app.framework.orm.Cursor;
import com.frodo.app.framework.orm.sql.ColumnDbType;
import com.frodo.app.framework.orm.sql.FinderLazyLoader;

import java.lang.reflect.Field;
import java.util.List;

public class Finder extends com.frodo.app.framework.orm.table.Column {

    private final String valueColumnName;
    private final String targetColumnName;

    Finder(Class<?> entityType, Field field) throws DbException {
        super(entityType, field);

        com.frodo.app.framework.orm.annotation.Finder finder =
                field.getAnnotation(com.frodo.app.framework.orm.annotation.Finder.class);
        this.valueColumnName = finder.valueColumn();
        this.targetColumnName = finder.targetColumn();
    }

    public Class<?> getTargetEntityType() {
        return ColumnUtils.getFinderTargetEntityType(this);
    }

    public String getTargetColumnName() {
        return targetColumnName;
    }

    @Override
    public void setValue2Entity(Object entity, Cursor cursor, int index) throws DbException {
        Object value;
        Class<?> columnType = columnField.getType();
        Object finderValue = TableUtils.getColumnOrId(entity.getClass(), this.valueColumnName).getColumnValue(entity);
        if (columnType.equals(FinderLazyLoader.class)) {
            value = new FinderLazyLoader(this, finderValue);
        } else if (columnType.equals(List.class)) {
            value = new FinderLazyLoader(this, finderValue).getAllFromDb();
        } else {
            value = new FinderLazyLoader(this, finderValue).getFirstFromDb();
        }

        if (setMethod != null) {
            try {
                setMethod.invoke(entity, value);
            } catch (Throwable e) {
                throw new DbException(e);
            }
        } else {
            try {
                this.columnField.setAccessible(true);
                this.columnField.set(entity, value);
            } catch (Throwable e) {
                throw new DbException(e);
            }
        }
    }

    @Override
    public Object getColumnValue(Object entity) {
        return null;
    }

    @Override
    public Object getDefaultValue() {
        return null;
    }

    @Override
    public ColumnDbType getColumnDbType() {
        return ColumnDbType.TEXT;
    }
}
