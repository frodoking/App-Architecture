package com.frodo.android.app.framework.orm.table;

import com.frodo.android.app.framework.exception.DbException;
import com.frodo.android.app.framework.orm.annotation.Table;
import com.frodo.android.app.framework.orm.converter.ColumnConverterFactory;
import com.frodo.android.app.framework.toolbox.TextUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class TableUtils {

    /**
     * key: entityType.name
     */
    private static ConcurrentHashMap<String, HashMap<String, Column>> entityColumnsMap = new ConcurrentHashMap<String, HashMap<String, Column>>();
    /**
     * key: entityType.name
     */
    private static ConcurrentHashMap<String, Id> entityIdMap = new ConcurrentHashMap<>();

    private TableUtils() {
    }

    public static String getTableName(Class<?> entityType) {
        Table table = entityType.getAnnotation(Table.class);
        if (table == null || TextUtils.isEmpty(table.name())) {
            return entityType.getName().replace('.', '_');
        }
        return table.name();
    }

    public static String getExecAfterTableCreated(Class<?> entityType) {
        Table table = entityType.getAnnotation(Table.class);
        if (table != null) {
            return table.execAfterTableCreated();
        }
        return null;
    }

    static synchronized HashMap<String, Column> getColumnMap(Class<?> entityType) throws DbException {

        if (entityColumnsMap.containsKey(entityType.getName())) {
            return entityColumnsMap.get(entityType.getName());
        }

        HashMap<String, Column> columnMap = new HashMap<>();
        String primaryKeyFieldName = getPrimaryKeyFieldName(entityType);
        addColumns2Map(entityType, primaryKeyFieldName, columnMap);
        entityColumnsMap.put(entityType.getName(), columnMap);

        return columnMap;
    }

    private static void addColumns2Map(Class<?> entityType, String primaryKeyFieldName, HashMap<String, Column> columnMap) throws DbException {
        if (Object.class.equals(entityType)) return;
        try {
            Field[] fields = entityType.getDeclaredFields();
            for (Field field : fields) {
                if (ColumnUtils.isTransient(field) || Modifier.isStatic(field.getModifiers())) {
                    continue;
                }
                if (ColumnConverterFactory.isSupportColumnConverter(field.getType())) {
                    if (!field.getName().equals(primaryKeyFieldName)) {
                        Column column = new Column(entityType, field);
                        if (!columnMap.containsKey(column.getColumnName())) {
                            columnMap.put(column.getColumnName(), column);
                        }
                    }
                } else if (ColumnUtils.isForeign(field)) {
                    Foreign column = new Foreign(entityType, field);
                    if (!columnMap.containsKey(column.getColumnName())) {
                        columnMap.put(column.getColumnName(), column);
                    }
                } else if (ColumnUtils.isFinder(field)) {
                    Finder column = new Finder(entityType, field);
                    if (!columnMap.containsKey(column.getColumnName())) {
                        columnMap.put(column.getColumnName(), column);
                    }
                }
            }

            if (!Object.class.equals(entityType.getSuperclass())) {
                addColumns2Map(entityType.getSuperclass(), primaryKeyFieldName, columnMap);
            }
        } catch (Throwable e) {
            throw new DbException(e);
        }
    }

    /* package */
    static Column getColumnOrId(Class<?> entityType, String columnName) throws DbException {
        if (getPrimaryKeyColumnName(entityType).equals(columnName)) {
            return getId(entityType);
        }
        return getColumnMap(entityType).get(columnName);
    }

    /* package */
    static synchronized Id getId(Class<?> entityType) throws DbException {
        if (Object.class.equals(entityType)) {
            throw new RuntimeException("field 'id' not found");
        }

        if (entityIdMap.containsKey(entityType.getName())) {
            return entityIdMap.get(entityType.getName());
        }

        Field primaryKeyField = null;
        Field[] fields = entityType.getDeclaredFields();
        if (fields != null) {

            for (Field field : fields) {
                if (field.getAnnotation(com.frodo.android.app.framework.orm.annotation.Id.class) != null) {
                    primaryKeyField = field;
                    break;
                }
            }

            if (primaryKeyField == null) {
                for (Field field : fields) {
                    if ("id".equals(field.getName()) || "_id".equals(field.getName())) {
                        primaryKeyField = field;
                        break;
                    }
                }
            }
        }

        if (primaryKeyField == null) {
            return getId(entityType.getSuperclass());
        }

        Id id = new Id(entityType, primaryKeyField);
        entityIdMap.put(entityType.getName(), id);
        return id;
    }

    private static String getPrimaryKeyFieldName(Class<?> entityType) throws DbException {
        Id id = getId(entityType);
        return id == null ? null : id.getColumnField().getName();
    }

    private static String getPrimaryKeyColumnName(Class<?> entityType) throws DbException {
        Id id = getId(entityType);
        return id == null ? null : id.getColumnName();
    }
}
