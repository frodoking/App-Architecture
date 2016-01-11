package com.frodo.app.framework.orm;

public interface Cursor {
    boolean isNull(int index);

    int getInt(int index);

    int getColumnIndex(String idColumnName);

    int getColumnCount();

    String getColumnName(int i);

    String getString(int i);

    byte[] getBlob(int index);

    long getLong(int index);

    Double getDouble(int index);

    Float getFloat(int index);

    Short getShort(int index);
}
