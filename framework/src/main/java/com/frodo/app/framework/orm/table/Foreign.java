package com.frodo.app.framework.orm.table;

import com.frodo.app.framework.exception.DbException;
import com.frodo.app.framework.orm.Cursor;
import com.frodo.app.framework.orm.converter.ColumnConverter;
import com.frodo.app.framework.orm.converter.ColumnConverterFactory;
import com.frodo.app.framework.orm.sql.ColumnDbType;
import com.frodo.app.framework.orm.sql.ForeignLazyLoader;

import java.lang.reflect.Field;
import java.util.List;

public class Foreign extends com.frodo.app.framework.orm.table.Column {

    private final String foreignColumnName;
    private final ColumnConverter foreignColumnConverter;

    public Foreign(Class<?> entityType, Field field) throws DbException {
        super(entityType, field);

        foreignColumnName = ColumnUtils.getForeignColumnNameByField(field);
        Class<?> foreignColumnType =
                TableUtils.getColumnOrId(getForeignEntityType(), foreignColumnName).columnField.getType();
        foreignColumnConverter = ColumnConverterFactory.getColumnConverter(foreignColumnType);
    }

    public String getForeignColumnName() {
        return foreignColumnName;
    }

    public Class<?> getForeignEntityType() {
        return ColumnUtils.getForeignEntityType(this);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setValue2Entity(Object entity, Cursor cursor, int index) throws DbException {
        Object fieldValue = foreignColumnConverter.getFieldValue(cursor, index);
        if (fieldValue == null) return;

        Object value = null;
        Class<?> columnType = columnField.getType();
        if (columnType.equals(ForeignLazyLoader.class)) {
            value = new ForeignLazyLoader(this, fieldValue);
        } else if (columnType.equals(List.class)) {
            value = new ForeignLazyLoader(this, fieldValue).getAllFromDb();
        } else {
            value = new ForeignLazyLoader(this, fieldValue).getFirstFromDb();
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

    @SuppressWarnings("unchecked")
    @Override
    public Object getColumnValue(Object entity) throws DbException {
        Object fieldValue = getFieldValue(entity);
        Object columnValue = null;

        if (fieldValue != null) {
            Class<?> columnType = columnField.getType();
            if (columnType.equals(ForeignLazyLoader.class)) {
                columnValue = ((ForeignLazyLoader) fieldValue).getColumnValue();
            } else if (columnType.equals(List.class)) {
                try {
                    List<?> foreignEntities = (List<?>) fieldValue;
                    if (!foreignEntities.isEmpty()) {

                        Class<?> foreignEntityType = ColumnUtils.getForeignEntityType(this);
                        com.frodo.app.framework.orm.table.Column column = TableUtils.getColumnOrId(foreignEntityType, foreignColumnName);
                        columnValue = column.getColumnValue(foreignEntities.get(0));

                        com.frodo.app.framework.orm.table.Table table = this.getTable();
                        if (table != null && column instanceof com.frodo.app.framework.orm.table.Id) {
                            for (Object foreignObj : foreignEntities) {
                                Object idValue = column.getColumnValue(foreignObj);
                                if (idValue == null) {
                                    table.db.saveOrUpdate(foreignObj);
                                }
                            }
                        }

                        columnValue = column.getColumnValue(foreignEntities.get(0));
                    }
                } catch (Throwable e) {
                    throw new DbException(e);
                }
            } else {
                try {
                    com.frodo.app.framework.orm.table.Column column = TableUtils.getColumnOrId(columnType, foreignColumnName);
                    columnValue = column.getColumnValue(fieldValue);

                    Table table = this.getTable();
                    if (table != null && columnValue == null && column instanceof Id) {
                        table.db.saveOrUpdate(fieldValue);
                    }

                    columnValue = column.getColumnValue(fieldValue);
                } catch (Throwable e) {
                    throw new DbException(e);
                }
            }
        }

        return columnValue;
    }

    @Override
    public ColumnDbType getColumnDbType() {
        return foreignColumnConverter.getColumnDbType();
    }

    /**
     * It always return null.
     *
     * @return null
     */
    @Override
    public Object getDefaultValue() {
        return null;
    }
}
