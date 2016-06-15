package com.frodo.app.framework.context;

/**
 * Take over app context
 * Created by frodo on 2015/6/20.
 */
public interface MicroContext<Application> {

    Application getContext();
    /**
     * app name
     *
     * @return {@link String}
     */
    String applicationName();

    /**
     * version code
     *
     * @return int
     */
    int versionCode();

    /**
     * version name
     *
     * @return {@link String}
     */
    String versionName();

    /**
     * root directory
     *
     * @return {@link String}
     */
    String getRootDirName();

    /**
     * file directory
     *
     * @return {@link String}
     */
    String getFilesDirName();
}
