package com.android.app.framework.cache;

import com.android.app.framework.controller.ChildSystem;

/**
 * 本地缓存(调配缓存策略)
 * Created by frodo on 2015/6/20.
 */
public interface Cache extends ChildSystem {
    String getCacheDir();

    void setCacheDir(String dir);

    /**
     * 来至于 数据库
     */
    boolean existCacheInDatabase(String sql);

    <T> T findCacheFromDatabase(String sql);

    /**
     * 来至于 应用内部本身
     */
    boolean existCacheInApplication(String key);

    <T> T findCacheFromApplication(String key);

    /**
     * 来至于 磁盘
     */
    boolean existCacheInLocal(String fileName);

    <F> F findCacheFromLocal(String fileName);
}
