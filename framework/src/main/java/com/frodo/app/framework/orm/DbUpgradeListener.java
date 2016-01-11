package com.frodo.app.framework.orm;

public interface DbUpgradeListener {
    void onUpgrade(com.frodo.app.framework.orm.Database db, int oldVersion, int newVersion);
}
