package com.frodo.app.framework.cache;

import com.frodo.app.framework.controller.ChildSystem;

import java.lang.reflect.Type;

/**
 * a cache system to manager {@link Cache}
 * Created by frodo on 2015/7/23.
 */
public interface CacheSystem extends ChildSystem {
    /**
     * get cache directory
     *
     * @return {@link String}
     */
    String getCacheDir();

    /**
     * set cache directory
     *
     * @param dir directory path
     */
    void setCacheDir(String dir);

    /**
     * is exist cache by {@link Cache.Type}
     *
     * @param key  {@link String}
     * @param type {@link Cache.Type}
     * @return boolean
     */
    boolean existCacheByType(String key, Cache.Type type);

    /**
     * find cache by {@link Type}
     *
     * @param key       {@link String}
     * @param classType {@link Type}
     * @param type      {@link Cache.Type}
     * @param <T>       {@link T}
     * @return {@link T}
     */
    <T> T findCacheByType(String key, Type classType, Cache.Type type);

    /**
     * is success put
     *
     * @param key   {@link K}
     * @param value {@link V}
     * @param type  {@link Cache.Type}
     * @param <K>   {@link K}
     * @param <V>   {@link V}
     * @return boolean
     */
    <K, V> boolean put(K key, V value, Cache.Type type);

    /**
     * evict
     *
     * @param key {@link String}
     * @param type {@link Cache.Type}
     */
    void evict(String key, Cache.Type type);

    /**
     * evict all by key
     * @param key {@link String}
     */
    void evictAll(String key);

    /**
     * evict all
     */
    void evictAll();

    /**
     * is exist cache in application internal
     *
     * @param key {@link String}
     * @return boolean
     */
    boolean existCacheInInternal(String key);

    /**
     * get cache from internal
     *
     * @param key       {@link String}
     * @param classType {@link Type}
     * @param <T>       {@link T}
     * @return {@link T}
     */
    <T> T findCacheFromInternal(String key, Type classType);

    /**
     * is exist cache in database
     *
     * @param sql sql string
     * @return boolean
     */
    boolean existCacheInDatabase(String sql);

    /**
     * get cache from database
     *
     * @param sql       sql string
     * @param classType {@link Type}
     * @param <T>       {@link T}
     * @return {@link T}
     */
    <T> T findCacheFromDatabase(String sql, Type classType);

    /**
     * is exist cache in external
     *
     * @param fileName file name
     * @return boolean
     */
    boolean existCacheInDisk(String fileName);

    /**
     * find cache from external
     *
     * @param fileName file name
     * @param clazz    any {@link Object}
     * @param <T>      {@link T}
     * @return {@link T}
     */
    <T> T findCacheFromDisk(String fileName, Object clazz);
}
