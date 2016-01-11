package com.frodo.app.framework.orm.sql;

import com.frodo.app.framework.exception.DbException;
import com.frodo.app.framework.orm.Cursor;
import com.frodo.app.framework.orm.Database;
import com.frodo.app.framework.orm.table.Column;
import com.frodo.app.framework.orm.table.DbModel;
import com.frodo.app.framework.orm.table.Finder;
import com.frodo.app.framework.orm.table.Id;
import com.frodo.app.framework.orm.table.Table;

import java.util.concurrent.ConcurrentHashMap;

public class CursorUtils {

    @SuppressWarnings("unchecked")
    public static <T> T getEntity(final Database db, final Cursor cursor, Class<T> entityType, long findCacheSequence) throws DbException {
        if (db == null || cursor == null) return null;

        EntityTempCache.setSeq(findCacheSequence);
        try {
            Table table = Table.get(db, entityType);
            Id id = table.id;
            String idColumnName = id.getColumnName();
            int idIndex = id.getIndex();
            if (idIndex < 0) {
                idIndex = cursor.getColumnIndex(idColumnName);
            }
            Object idValue = id.getColumnConverter().getFieldValue(cursor, idIndex);
            T entity = EntityTempCache.get(entityType, idValue);
            if (entity == null) {
                entity = entityType.newInstance();
                id.setValue2Entity(entity, cursor, idIndex);
                EntityTempCache.put(entityType, idValue, entity);
            } else {
                return entity;
            }
            int columnCount = cursor.getColumnCount();
            for (int i = 0; i < columnCount; i++) {
                String columnName = cursor.getColumnName(i);
                Column column = table.columnMap.get(columnName);
                if (column != null) {
                    column.setValue2Entity(entity, cursor, i);
                }
            }

            // init finder
            for (Finder finder : table.finderMap.values()) {
                finder.setValue2Entity(entity, null, 0);
            }
            return entity;
        } catch (Throwable e) {
            throw new DbException(e);
        }
    }

    public static DbModel getDbModel(final Cursor cursor) {
        DbModel result = null;
        if (cursor != null) {
            result = new DbModel();
            int columnCount = cursor.getColumnCount();
            for (int i = 0; i < columnCount; i++) {
                result.add(cursor.getColumnName(i), cursor.getString(i));
            }
        }
        return result;
    }

    public static class FindCacheSequence {
        private static final String FOREIGN_LAZY_LOADER_CLASS_NAME = ForeignLazyLoader.class.getName();
        private static final String FINDER_LAZY_LOADER_CLASS_NAME = FinderLazyLoader.class.getName();
        private static long seq = 0;
        private FindCacheSequence() {
        }

        public static long getSeq() {
            String findMethodCaller = Thread.currentThread().getStackTrace()[4].getClassName();
            if (!findMethodCaller.equals(FOREIGN_LAZY_LOADER_CLASS_NAME) && !findMethodCaller.equals(FINDER_LAZY_LOADER_CLASS_NAME)) {
                ++seq;
            }
            return seq;
        }
    }

    private static class EntityTempCache {
        private static final ConcurrentHashMap<String, Object> cache = new ConcurrentHashMap<String, Object>();
        private static long seq = 0;

        private EntityTempCache() {
        }

        public static <T> void put(Class<T> entityType, Object idValue, Object entity) {
            cache.put(entityType.getName() + "#" + idValue, entity);
        }

        @SuppressWarnings("unchecked")
        public static <T> T get(Class<T> entityType, Object idValue) {
            return (T) cache.get(entityType.getName() + "#" + idValue);
        }

        public static void setSeq(long seq) {
            if (EntityTempCache.seq != seq) {
                cache.clear();
                EntityTempCache.seq = seq;
            }
        }
    }
}
