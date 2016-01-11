package com.frodo.app.framework.orm.converter;

import com.frodo.app.framework.orm.sql.ColumnDbType;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

public class ColumnConverterFactory {

    private static final ConcurrentHashMap<String, com.frodo.app.framework.orm.converter.ColumnConverter> columnType_columnConverter_map;

    static {
        columnType_columnConverter_map = new ConcurrentHashMap<String, com.frodo.app.framework.orm.converter.ColumnConverter>();

        com.frodo.app.framework.orm.converter.BooleanColumnConverter booleanColumnConverter = new com.frodo.app.framework.orm.converter.BooleanColumnConverter();
        columnType_columnConverter_map.put(boolean.class.getName(), booleanColumnConverter);
        columnType_columnConverter_map.put(Boolean.class.getName(), booleanColumnConverter);

        ByteArrayColumnConverter byteArrayColumnConverter = new ByteArrayColumnConverter();
        columnType_columnConverter_map.put(byte[].class.getName(), byteArrayColumnConverter);

        com.frodo.app.framework.orm.converter.ByteColumnConverter byteColumnConverter = new ByteColumnConverter();
        columnType_columnConverter_map.put(byte.class.getName(), byteColumnConverter);
        columnType_columnConverter_map.put(Byte.class.getName(), byteColumnConverter);

        com.frodo.app.framework.orm.converter.CharColumnConverter charColumnConverter = new CharColumnConverter();
        columnType_columnConverter_map.put(char.class.getName(), charColumnConverter);
        columnType_columnConverter_map.put(Character.class.getName(), charColumnConverter);

        com.frodo.app.framework.orm.converter.DateColumnConverter dateColumnConverter = new DateColumnConverter();
        columnType_columnConverter_map.put(Date.class.getName(), dateColumnConverter);

        com.frodo.app.framework.orm.converter.DoubleColumnConverter doubleColumnConverter = new DoubleColumnConverter();
        columnType_columnConverter_map.put(double.class.getName(), doubleColumnConverter);
        columnType_columnConverter_map.put(Double.class.getName(), doubleColumnConverter);

        com.frodo.app.framework.orm.converter.FloatColumnConverter floatColumnConverter = new FloatColumnConverter();
        columnType_columnConverter_map.put(float.class.getName(), floatColumnConverter);
        columnType_columnConverter_map.put(Float.class.getName(), floatColumnConverter);

        IntegerColumnConverter integerColumnConverter = new IntegerColumnConverter();
        columnType_columnConverter_map.put(int.class.getName(), integerColumnConverter);
        columnType_columnConverter_map.put(Integer.class.getName(), integerColumnConverter);

        com.frodo.app.framework.orm.converter.LongColumnConverter longColumnConverter = new LongColumnConverter();
        columnType_columnConverter_map.put(long.class.getName(), longColumnConverter);
        columnType_columnConverter_map.put(Long.class.getName(), longColumnConverter);

        com.frodo.app.framework.orm.converter.ShortColumnConverter shortColumnConverter = new ShortColumnConverter();
        columnType_columnConverter_map.put(short.class.getName(), shortColumnConverter);
        columnType_columnConverter_map.put(Short.class.getName(), shortColumnConverter);

        SqlDateColumnConverter sqlDateColumnConverter = new SqlDateColumnConverter();
        columnType_columnConverter_map.put(java.sql.Date.class.getName(), sqlDateColumnConverter);

        com.frodo.app.framework.orm.converter.StringColumnConverter stringColumnConverter = new StringColumnConverter();
        columnType_columnConverter_map.put(String.class.getName(), stringColumnConverter);
    }

    private ColumnConverterFactory() {
    }

    public static com.frodo.app.framework.orm.converter.ColumnConverter getColumnConverter(Class columnType) {
        if (columnType_columnConverter_map.containsKey(columnType.getName())) {
            return columnType_columnConverter_map.get(columnType.getName());
        } else if (com.frodo.app.framework.orm.converter.ColumnConverter.class.isAssignableFrom(columnType)) {
            try {
                com.frodo.app.framework.orm.converter.ColumnConverter columnConverter = (com.frodo.app.framework.orm.converter.ColumnConverter) columnType.newInstance();
                if (columnConverter != null) {
                    columnType_columnConverter_map.put(columnType.getName(), columnConverter);
                }
                return columnConverter;
            } catch (Throwable e) {
            }
        }
        return null;
    }

    public static ColumnDbType getDbColumnType(Class columnType) {
        com.frodo.app.framework.orm.converter.ColumnConverter converter = getColumnConverter(columnType);
        if (converter != null) {
            return converter.getColumnDbType();
        }
        return ColumnDbType.TEXT;
    }

    public static void registerColumnConverter(Class columnType, com.frodo.app.framework.orm.converter.ColumnConverter columnConverter) {
        columnType_columnConverter_map.put(columnType.getName(), columnConverter);
    }

    public static boolean isSupportColumnConverter(Class columnType) {
        if (columnType_columnConverter_map.containsKey(columnType.getName())) {
            return true;
        } else if (com.frodo.app.framework.orm.converter.ColumnConverter.class.isAssignableFrom(columnType)) {
            try {
                com.frodo.app.framework.orm.converter.ColumnConverter columnConverter = (ColumnConverter) columnType.newInstance();
                if (columnConverter != null) {
                    columnType_columnConverter_map.put(columnType.getName(), columnConverter);
                }
                return columnConverter == null;
            } catch (Throwable e) {
            }
        }
        return false;
    }
}
