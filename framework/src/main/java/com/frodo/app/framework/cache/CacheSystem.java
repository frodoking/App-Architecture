package com.frodo.app.framework.cache;

import com.frodo.app.framework.controller.ChildSystem;

import java.lang.reflect.Type;

/**
 * Created by frodo on 2015/7/23.
 */
public interface CacheSystem extends ChildSystem {
    String getCacheDir();

    void setCacheDir(String dir);

    /**
     * By Type
     */
    boolean existCacheByType(String key, Cache.Type type);

    <T> T findCacheByType(String key, Type classType, Cache.Type type);

    <K, V> boolean put(K key, V value, Cache.Type type);

    void evict(String key);

    void evictAll();

    /**
     * from application Internal
     */
    boolean existCacheInInternal(String key);

    <T> T findCacheFromInternal(String key, Type classType);

    /**
     * from application database
     */
    boolean existCacheInDatabase(String sql);

    <T> T findCacheFromDatabase(String sql, Type classType);

    /**
     * from application disk
     */
    boolean existCacheInDisk(String fileName);

    <T> T findCacheFromDisk(String fileName, Type classType);
}
