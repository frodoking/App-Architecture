/*
 * Copyright (c) 2013. wyouflf (wyouflf@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.frodo.android.app.framework.orm.table;


import com.frodo.android.app.framework.exception.DbException;
import com.frodo.android.app.framework.orm.Database;
import com.frodo.android.app.framework.toolbox.TextUtils;

import java.util.HashMap;
import java.util.Map;


public class Table {

    /**
     * key: dbName#className
     */
    private static final HashMap<String, Table> tableMap = new HashMap<String, Table>();
    public final Database db;
    public final String tableName;
    public final Id id;
    /**
     * key: columnName
     */
    public final HashMap<String, Column> columnMap;
    /**
     * key: columnName
     */
    public final HashMap<String, Finder> finderMap;
    private boolean checkedDatabase;

    private Table(Database db, Class<?> entityType) throws DbException {
        this.db = db;
        this.tableName = TableUtils.getTableName(entityType);
        this.id = TableUtils.getId(entityType);
        this.columnMap = TableUtils.getColumnMap(entityType);

        finderMap = new HashMap<>();
        for (Column column : columnMap.values()) {
            column.setTable(this);
            if (column instanceof Finder) {
                finderMap.put(column.getColumnName(), (Finder) column);
            }
        }
    }

    public static synchronized Table get(Database db, Class<?> entityType) throws DbException {
        String tableKey = db.getDaoConfig().getDbName() + "#" + entityType.getName();
        Table table = tableMap.get(tableKey);
        if (table == null) {
            table = new Table(db, entityType);
            tableMap.put(tableKey, table);
        }

        return table;
    }

    public static synchronized void remove(Database db, Class<?> entityType) {
        String tableKey = db.getDaoConfig().getDbName() + "#" + entityType.getName();
        tableMap.remove(tableKey);
    }

    public static synchronized void remove(Database db, String tableName) {
        if (tableMap.size() > 0) {
            String key = null;
            for (Map.Entry<String, Table> entry : tableMap.entrySet()) {
                Table table = entry.getValue();
                if (table != null && table.tableName.equals(tableName)) {
                    key = entry.getKey();
                    if (key.startsWith(db.getDaoConfig().getDbName() + "#")) {
                        break;
                    }
                }
            }
            if (TextUtils.isEmpty(key)) {
                tableMap.remove(key);
            }
        }
    }

    public boolean isCheckedDatabase() {
        return checkedDatabase;
    }

    public void setCheckedDatabase(boolean checkedDatabase) {
        this.checkedDatabase = checkedDatabase;
    }

}
