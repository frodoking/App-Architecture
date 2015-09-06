package com.frodo.android.app.framework.orm;

public interface DbUpgradeListener {
    void onUpgrade(Database db, int oldVersion, int newVersion);
}
