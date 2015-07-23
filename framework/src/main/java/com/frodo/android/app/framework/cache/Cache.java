package com.frodo.android.app.framework.cache;

/**
 * An interface representing a E Cache.
 * Created by frodo on 2015/6/20.
 */
public interface Cache<K, V> {

    /**
     * Get CacheSystem.
     *
     * @return CacheSystem
     */
    CacheSystem getCacheSystem();

    /**
     * Get cache Type
     *
     * @return Type
     */
    Type getType();

    /**
     * Get an V by K.
     *
     * @param key
     *
     * @return E
     */
    V get(K key);

    /**
     * Puts and element into the cache.
     *
     * @param value Element to insert in the cache by key.
     */
    void put(K key, V value);

    /**
     * Checks if an element (E) exists in the cache.
     *
     * @param key The id used to look for inside the cache.
     *
     * @return true if the element is cached, otherwise false.
     */
    boolean isCached(K key);

    /**
     * Checks if the cache is expired.
     *
     * @return true, the cache is expired, otherwise false.
     */
    boolean isExpired();

    /**
     * Evict all elements of the cache.
     */
    void evictAll();

    enum Type {
        INTERNAL, DATABASE, DISK,
    }
}
