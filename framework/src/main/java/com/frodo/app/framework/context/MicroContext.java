package com.frodo.app.framework.context;

/**
 * Take over app context
 * Created by frodo on 2015/6/20.
 */
public interface MicroContext {
    String applicationName();

    int versionCode();

    String versionName();

    String getRootDirName();

    String getFilesDirName();
}
