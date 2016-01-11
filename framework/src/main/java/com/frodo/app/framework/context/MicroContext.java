package com.frodo.app.framework.context;

/**
 * 原始App上下文关系的接管器
 * Created by frodo on 2015/6/20.
 */
public interface MicroContext {
    String applicationName();

    int versionCode();

    String versionName();

    String getRootDirName();

    String getFilesDirName();
}
