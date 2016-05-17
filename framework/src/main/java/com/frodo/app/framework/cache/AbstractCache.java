package com.frodo.app.framework.cache;

/**
 * Abstract Cache
 * @param <K>
 * @param <V>
 *     Created by frodo on 2015/7/23.
 */
public abstract class AbstractCache<K, V> implements Cache<K, V> {
    private CacheSystem mCacheSystem;
    private Type mType;

    public AbstractCache(CacheSystem cacheSystem, Type type) {
        this.mCacheSystem = cacheSystem;
        this.mType = type;
    }

    @Override
    public final CacheSystem getCacheSystem() {
        return mCacheSystem;
    }

    public final Type getType() {
        return mType;
    }
}
