package com.frodo.app.framework.orm.sql;

import com.frodo.app.framework.orm.converter.ColumnConverterFactory;
import com.frodo.app.framework.orm.table.ColumnUtils;
import com.frodo.app.framework.toolbox.TextUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class WhereBuilder {

    private final List<String> whereItems;

    private WhereBuilder() {
        this.whereItems = new ArrayList<String>();
    }

    /**
     * create new instance
     *
     * @return WhereBuilder
     */
    public static WhereBuilder b() {
        return new WhereBuilder();
    }

    /**
     * create new instance
     *
     * @param columnName
     * @param op         operator: "=","<","LIKE","IN","BETWEEN"...
     * @param value
     * @return WhereBuilder
     */
    public static WhereBuilder b(String columnName, String op, Object value) {
        WhereBuilder result = new WhereBuilder();
        result.appendCondition(null, columnName, op, value);
        return result;
    }

    /**
     * add AND condition
     *
     * @param columnName
     * @param op         operator: "=","<","LIKE","IN","BETWEEN"...
     * @param value
     * @return WhereBuilder
     */
    public WhereBuilder and(String columnName, String op, Object value) {
        appendCondition(whereItems.isEmpty() ? null : "AND", columnName, op, value);
        return this;
    }

    /**
     * add OR condition
     *
     * @param columnName
     * @param op         operator: "=","<","LIKE","IN","BETWEEN"...
     * @param value
     * @return WhereBuilder
     */
    public WhereBuilder or(String columnName, String op, Object value) {
        appendCondition(whereItems.isEmpty() ? null : "OR", columnName, op, value);
        return this;
    }

    public WhereBuilder expr(String expr) {
        whereItems.add(" " + expr);
        return this;
    }

    public WhereBuilder expr(String columnName, String op, Object value) {
        appendCondition(null, columnName, op, value);
        return this;
    }

    public int getWhereItemSize() {
        return whereItems.size();
    }

    @Override
    public String toString() {
        if (whereItems.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (String item : whereItems) {
            sb.append(item);
        }
        return sb.toString();
    }

    private void appendCondition(String conj, String columnName, String op, Object value) {
        StringBuilder sqlSb = new StringBuilder();

        if (!whereItems.isEmpty()) {
            sqlSb.append(" ");
        }

        // append conj
        if (!TextUtils.isEmpty(conj)) {
            sqlSb.append(conj + " ");
        }

        // append columnName
        sqlSb.append(columnName);

        // convert op
        if ("!=".equals(op)) {
            op = "<>";
        } else if ("==".equals(op)) {
            op = "=";
        }

        // append op & value
        if (value == null) {
            if ("=".equals(op)) {
                sqlSb.append(" IS NULL");
            } else if ("<>".equals(op)) {
                sqlSb.append(" IS NOT NULL");
            } else {
                sqlSb.append(" " + op + " NULL");
            }
        } else {
            sqlSb.append(" " + op + " ");

            if ("IN".equalsIgnoreCase(op)) {
                Iterable<?> items = null;
                if (value instanceof Iterable) {
                    items = (Iterable<?>) value;
                } else if (value.getClass().isArray()) {
                    ArrayList<Object> arrayList = new ArrayList<Object>();
                    int len = Array.getLength(value);
                    for (int i = 0; i < len; i++) {
                        arrayList.add(Array.get(value, i));
                    }
                    items = arrayList;
                }
                if (items != null) {
                    StringBuffer stringBuffer = new StringBuffer("(");
                    for (Object item : items) {
                        Object itemColValue = ColumnUtils.convert2DbColumnValueIfNeeded(item);
                        if (com.frodo.app.framework.orm.sql.ColumnDbType.TEXT.equals(ColumnConverterFactory.getDbColumnType(itemColValue.getClass()))) {
                            String valueStr = itemColValue.toString();
                            if (valueStr.indexOf('\'') != -1) { // convert single quotations
                                valueStr = valueStr.replace("'", "''");
                            }
                            stringBuffer.append("'" + valueStr + "'");
                        } else {
                            stringBuffer.append(itemColValue);
                        }
                        stringBuffer.append(",");
                    }
                    stringBuffer.deleteCharAt(stringBuffer.length() - 1);
                    stringBuffer.append(")");
                    sqlSb.append(stringBuffer.toString());
                } else {
                    throw new IllegalArgumentException("value must be an Array or an Iterable.");
                }
            } else if ("BETWEEN".equalsIgnoreCase(op)) {
                Iterable<?> items = null;
                if (value instanceof Iterable) {
                    items = (Iterable<?>) value;
                } else if (value.getClass().isArray()) {
                    ArrayList<Object> arrayList = new ArrayList<Object>();
                    int len = Array.getLength(value);
                    for (int i = 0; i < len; i++) {
                        arrayList.add(Array.get(value, i));
                    }
                    items = arrayList;
                }
                if (items != null) {
                    Iterator<?> iterator = items.iterator();
                    if (!iterator.hasNext())
                        throw new IllegalArgumentException("value must have tow items.");
                    Object start = iterator.next();
                    if (!iterator.hasNext())
                        throw new IllegalArgumentException("value must have tow items.");
                    Object end = iterator.next();

                    Object startColValue = ColumnUtils.convert2DbColumnValueIfNeeded(start);
                    Object endColValue = ColumnUtils.convert2DbColumnValueIfNeeded(end);

                    if (com.frodo.app.framework.orm.sql.ColumnDbType.TEXT.equals(ColumnConverterFactory.getDbColumnType(startColValue.getClass()))) {
                        String startStr = startColValue.toString();
                        if (startStr.indexOf('\'') != -1) { // convert single quotations
                            startStr = startStr.replace("'", "''");
                        }
                        String endStr = endColValue.toString();
                        if (endStr.indexOf('\'') != -1) { // convert single quotations
                            endStr = endStr.replace("'", "''");
                        }
                        sqlSb.append("'" + startStr + "'");
                        sqlSb.append(" AND ");
                        sqlSb.append("'" + endStr + "'");
                    } else {
                        sqlSb.append(startColValue);
                        sqlSb.append(" AND ");
                        sqlSb.append(endColValue);
                    }
                } else {
                    throw new IllegalArgumentException("value must be an Array or an Iterable.");
                }
            } else {
                value = ColumnUtils.convert2DbColumnValueIfNeeded(value);
                if (com.frodo.app.framework.orm.sql.ColumnDbType.TEXT.equals(ColumnConverterFactory.getDbColumnType(value.getClass()))) {
                    String valueStr = value.toString();
                    if (valueStr.indexOf('\'') != -1) { // convert single quotations
                        valueStr = valueStr.replace("'", "''");
                    }
                    sqlSb.append("'" + valueStr + "'");
                } else {
                    sqlSb.append(value);
                }
            }
        }
        whereItems.add(sqlSb.toString());
    }
}
